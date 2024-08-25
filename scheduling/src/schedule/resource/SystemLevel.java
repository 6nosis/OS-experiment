package schedule.resource;

import java.util.ArrayList;
import java.util.List;

class SystemLevel extends T0status {
    private int[][] allocation;
    private int[][] need;
    private int[] available;
    private int[] work;
    private int currReady;
    private List<Pcb> process;

    SystemLevel(String s) {
        this.allocation = super.allocation;
        this.need = super.need;
        this.available = super.available;
        this.process = super.process;
    }

    void show() {
        System.out.println("---------------------初始资源分配情况--------------------------");
        System.out.println("          Max        Allocation      Need        Available");
        System.out.println("       A   B   C     A   B   C     A   B   C     A   B   C");
        int f = 0;
        for (int i = 0; i < process.size(); i++) {
            System.out.print("P[" + i + "]   ");
            for (int j = 0; j < 3; j++) {
                System.out.print(process.get(i).getNeedMax()[j] + "   ");
            }
            System.out.print("  ");
            for (int j = 0; j < 3; j++) {
                System.out.print(process.get(i).getOccupy()[j] + "   ");
            }
            System.out.print("  ");
            for (int j = 0; j < 3; j++) {
                System.out.print(need[i][j] + "   ");
            }
            System.out.print("  ");
            if (f == 0) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(available[j] + "   ");
                }
                f++;
            }
            System.out.println();
        }
    }

    private int name2index(String s) {
        String str = s.substring(s.length() - 1);
        return Integer.parseInt(str);
    }

    void opwPcb(String name, char c) {//更改状态，哪个进程，状态改为什么
        this.process.get(name2index(name)).setStatus(c);
    }

    void opwPcb(int n, String s, int[] a) {
        switch (n) {
            case 1:
                this.process.get(name2index(s)).setApply(a);
                break;
            case 2:
                this.process.get(name2index(s)).setNeedMax(a);
                break;
            case 3:
                this.process.get(name2index(s)).setOccupy(a);
                break;
            default:
                break;
        }
    }

    boolean check(int[] a) {
        if (a.length != this.available.length) {
            return false;
        }
        for (int i = 0; i < this.available.length; i++) {
            if (a[i] > this.available[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean can(Pcb p) {
        for (int i = 0; i < this.work.length; i++) {
            if (p.getApply()[i] > this.work[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSecurity() {
        List<Pcb> tmp = new ArrayList<>();
        // 初始化工作向量
        this.work = this.available;
        for (Pcb pcb : this.process) {
            if (pcb.getStatus() == 'r') {
                // 找到要申请资源的进程将其加入列表
                tmp.add(pcb);
            }
        }
        boolean[] finish = new boolean[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            finish[i] = false;
        }
        for (int i = 0; i < tmp.size(); i++) {
            // 对于可加入安全序列的进程
            if (!finish[i] && can(tmp.get(i))) {
                for (int j = 0; j < work.length; j++) {
                    // 假设它很快完成任务，把资源归还
                    this.work[j] += tmp.get(i).getApply()[j];
                }
                // 让它可完成
                finish[i] = true;
            }
        }
        for (boolean b : finish) {
            // 只要有不能完成的，即不满足安全序列
            if (!b) {
                return false;
            }
        }
        //能执行到这说明都可完成
        return true;
    }

    private void apply() {
        for (int i = 0; i < this.available.length; i++) {
            // 系统现存减申请
            this.available[i] -= this.process.get(this.currReady).getApply()[i];
            // 系统占用加申请
            this.allocation[currReady][i] += this.process.get(this.currReady).getApply()[i];
        }
        // 将申请清零
        this.orderApply();
    }

    private void giveback() {
        // 将进程占据资源全部归还
        for (int i = 0; i < this.process.get(this.currReady).getOccupy().length; i++) {
            this.available[i] += this.process.get(this.currReady).getOccupy()[i];
        }
        // 将该进程系统占用清零
        for (int i = 0; i < this.process.get(this.currReady).getOccupy().length; i++) {
            this.allocation[this.currReady][i] = 0;
        }
        // 更新占据资源信息
        this.process.get(this.currReady).release();
        // 更改状态为“完成”
        this.process.get(this.currReady).setStatus('e');
    }

    boolean bankerAlgorithm() {
        // 检查就绪进程
        while (this.checkReady()) {
            // 现存可满足申请并进行安全性检查
            if (this.check(this.process.get(this.currReady).getApply()) && checkSecurity()) {
                this.apply();
                // 没有得到全部所需资源
                if (this.checkFulfil() && (process.get(currReady).getApply()[0] != 0
                        && process.get(currReady).getApply()[1] != 0
                        && process.get(currReady).getApply()[2] != 0)) {
                    continue;
                } else {//得到全部所需资源
                    this.giveback();
                }
                for (int i = 0; i < this.process.size(); i++) {
                    // 检查等待中的进程
                    if (this.process.get(i).getStatus() == 'w') {
                        // 如果系统现存能满足之前的申请
                        if (check(this.need[i])) {
                            // 设置为“就绪”状态
                            this.process.get(i).setStatus('r');
                        }
                    }
                }
            } else {
                // 不能满足申请置为“等待”状态
                this.process.get(this.currReady).setStatus('w');
                // 向系统登记申请
                this.need[this.currReady] = this.process.get(this.currReady).getApply();
                continue;
            }
        }
        // 检查是否有进程未处于完成态
        for (Pcb pcb : this.process) {
            if (pcb.getStatus() != 'e') {
                return false;
            }
        }
        //执行到这说明全部完成
        return true;
    }

    /**
     * 允许申请
     */
    private void orderApply() {
        int[] a = new int[this.process.get(this.currReady).getApply().length];
        for (int i = 0; i < this.process.get(this.currReady).getApply().length; i++) {
            // 将进程申请全部清零
            a[i] = 0;
        }
        // 完成清零
        this.process.get(this.currReady).setApply(a);
    }

    /**
     * 检查当前进程列表中是否有状态为“就绪”的进程
     * @return 检查结果
     */
    private boolean checkReady() {
        for (int i = 0; i < this.process.size(); i++) {
            if (this.process.get(i).getStatus() == 'r') {
                // 如果有就把它的序号交给currReady
                this.currReady = i;
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否满足全部需求
     * @return 检查结果
     */
    private boolean checkFulfil() {
        boolean result = true;
        for (int i = 0; i < process.get(currReady).getNeedMax().length; i++) {
            if (process.get(currReady).getNeedMax()[i] != process.get(currReady).getOccupy()[i]) {
                result = false;
            }
        }
        return !result;
    }

    boolean randomAlgorithm() {
        // 检查就绪进程
        while (this.checkReady()) {
            // 现存可满足申请
            if (this.check(this.process.get(this.currReady).getApply())) {
                this.apply();
                // 没有得到全部所需资源
                if (this.checkFulfil() && (process.get(currReady).getApply()[0] != 0
                        && process.get(currReady).getApply()[1] != 0
                        && process.get(currReady).getApply()[2] != 0)) {
                    continue;
                } else {//得到全部所需资源
                    this.giveback();
                }
                // 检查等待中的进程
                for (int i = 0; i < this.process.size(); i++) {
                    if (this.process.get(i).getStatus() == 'w') {
                        // 如果系统现存能满足之前的申请
                        if (check(this.need[i])) {
                            // 设置为“就绪”状态
                            this.process.get(i).setStatus('r');
                        }
                    }
                }
            } else {
                // 不能满足申请置为“等待”状态
                this.process.get(this.currReady).setStatus('w');
                // 向系统登记申请
                this.need[this.currReady] = this.process.get(this.currReady).getApply();
                continue;
            }
        }
        // 检查是否有进程未处于完成态
        for (Pcb pcb : this.process) {
            if (pcb.getStatus() != 'e') {
                return false;
            }
        }
        //执行到这说明全部完成
        return true;
    }
}

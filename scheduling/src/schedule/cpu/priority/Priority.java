package schedule.cpu.priority;

import schedule.cpu.method.AbstractMethod;
import schedule.cpu.pcb.PriorityPcb;

import java.util.*;

/**
 * @author kaoso
 */
public class Priority extends AbstractMethod {
    /**
     * 优先队列容器：用来存储所有带优先级的PCB，永远是降序排列的
     */
    private Queue<PriorityPcb> pc = new PriorityQueue<>();
    /**
     * 临时保存已结束进程
     */
    private List<PriorityPcb> l = new ArrayList<>();
    /**
     * 临时保存当前执行进程
     */
    private List<PriorityPcb> ll = new ArrayList<>();
    /**
     * 当前进程
     */
    private PriorityPcb current;

    /**
     * 提供向优先队列容器添加进程的接口
     * @param p 进程
     */
    public void setPc(PriorityPcb p) {
        this.pc.add(p);
    }

    /**
     * 展示当前所有进程信息
     */
    @Override
    public void show() {

        if (ll.size() != 0) {
            System.out.println("当前执行进程：");
            System.out.println("进程名   状态  需运行   已运行   优先级");
            current.show();
        }

        if (pc.size() != 0) {
            System.out.println("当前就绪进程：");
            System.out.println("进程名   状态  需运行   已运行   优先级");
            // 利用迭代器依次输出储存的所有对象
            for (PriorityPcb o : pc) {
                o.show();
            }
        }

        if (l.size() != 0) {
            System.out.println("当前已结束进程：");
            System.out.println("进程名   状态  需运行   已运行   优先级");
            // 利用迭代器依次输出储存的所有对象
            for (PriorityPcb o : l) {
                o.show();
            }
        }
        this.goon();
    }

    /**
     * 最高优先级优先算法
     */
    public void priorityAlgorithm() {
        System.out.println("----------初始状态----------");
        show();
        // 当优先队列不为空时
        while (pc.size() != 0) {
            // 将队首进程取出
            current = pc.poll();
            // 状态置为“运行”
            current.setStatus('r');
            // 加入当前执行进程
            ll.add(current);
            // 已运行加1
            current.changeAtime();
            // 若优先级不为0则减1
            if (current.getPriority() != 0) {
                current.changePriority();
            }
            // 展示当前所有进程状态
            show();
            // 如果已运行等于需运行
            if (current.getRequestTime() == current.getRunningTime()) {
                // 状态置为“结束”
                current.setStatus('e');
                // 加入当前已结束进程
                l.add(current);
            } else {
                // 状态置为“就绪”
                current.setStatus('w');
                // 重新送入优先队列
                pc.add(current);
            }
            // 清除当前执行进程
            ll.clear();
        }
        // 最终状态
        show();
    }
}

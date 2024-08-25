package schedule.cpu.simpleroundrobin;

import schedule.cpu.method.AbstractMethod;
import schedule.cpu.pcb.Pcb;

import java.util.ArrayList;
import java.util.List;

public class SimpleRoundRobin extends AbstractMethod {
    /**
     * 数组列表容器：用来存储所有的PCB，先来的占小序号
     */
    private List<Pcb> pc = new ArrayList<>();
    /**
     * 临时保存已结束进程
     */
    private List<Pcb> l = new ArrayList<>();
    /**
     * 临时保存当前执行进程
     */
    private List<Pcb> ll = new ArrayList<>();
    /**
     * 当前进程
     */
    private Pcb current;

    /**
     * 提供向数组列表容器添加进程的接口
     * @param p 进程
     */
    public void setPc(Pcb p) {
        this.pc.add(p);
    }

    /**
     * 展示当前所有进程信息
     */
    @Override
    public void show() {
        if (!ll.isEmpty()) {
            System.out.println("当前执行进程：");
            System.out.println("进程名   状态  需运行   已运行");
            current.show();
            System.out.println();
        }

        if (!pc.isEmpty()) {
            System.out.println("当前就绪进程：");
            System.out.println("进程名   状态  需运行   已运行");
            // 利用迭代器依次输出储存的所有对象
            for (Pcb o : pc) {
                o.show();
                System.out.println();

            }
        }

        if (!l.isEmpty()) {
            System.out.println("当前已结束进程：");
            System.out.println("进程名  状态  需运行   已运行");
            // 利用迭代器依次输出储存的所有对象
            for (Pcb o : l) {
                o.show();
                System.out.println();
            }
        }
        this.goon();
    }

    /**
     * 简单轮转法算法
     */
    public void simpleRoundRobinAlgorithm() {
        System.out.println("----------初始状态----------");
        show();

        //如果列表不为空
        while (!pc.isEmpty()) {
            //取第一个进程
            current = pc.get(0);
            //从列表中移除第一个进程
            pc.remove(0);
            //置取出进程的状态为“运行”
            current.setStatus('r');
            //加入当前执行进程
            ll.add(current);
            //已运行加1
            current.changeAtime();
            //展示当前所有进程状态
            show();
            // 清除当前执行进程
            ll.clear();
            //如果已运行等于需运行
            if (current.getRequestTime() == current.getRunningTime()) {
                //状态置为“结束”
                current.setStatus('e');
                //加入当前已结束进程
                l.add(current);
                continue;//直接处理下一个进程
            }
            //如果不是则置取出进程的状态为“等待”
            current.setStatus('w');
            //将取出进程加到列表尾部
            pc.add(pc.size(), current);
        }
        show();//最终状态
    }
}


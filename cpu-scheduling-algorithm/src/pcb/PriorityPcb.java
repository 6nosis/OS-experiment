package pcb;

import java.util.Scanner;

/**
 * @author kaoso
 * 继承PCB属性，实现可比较接口
 */
public class PriorityPcb extends Pcb implements Comparable<PriorityPcb> {
    /**
     * 优先级
     */
    private int priority;

    /**
     * 初始化优先级
     * @param n 进程号
     */
    public PriorityPcb(String n) {
        super(n);
        System.out.println("请输入优先级：");
        Scanner in = new Scanner(System.in);
        this.priority = in.nextInt();
    }

    /**
     * 获取优先级
     * @return 当前优先级
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * 优先级减1
     */
    public void changePriority() {
        this.priority--;
    }

    /**
     * 重写比较规则，使得大优先级在队列顶端
     * compareTo说明：正常情况：小的在前大的在后，返回正数代表是大的，负数是小的。但是如果颠倒返回
     * 的正负，就会是大的在前小的在后了。如下代码，加个负号即可。
     * Comparator方法和实现Comparable接口，二选一即可，都可以实现排序并为优先队列所用，
     * 位置不一样而已。
     * @param o 比较对象
     * @return 比较代号
     */
    @Override
    public int compareTo(PriorityPcb o) {
        if (this.priority > o.getPriority()) {
            // 原本应是正数
            return -(this.priority - o.getPriority());
        }
        if (this.priority < o.getPriority()){
            //原本应是负数
            return -(this.priority - o.getPriority());
        }
        return 0;
    }

    @Override
    public void show() {//展示带优先级的PCB信息
        super.show();
        System.out.println(this.priority);
    }
}

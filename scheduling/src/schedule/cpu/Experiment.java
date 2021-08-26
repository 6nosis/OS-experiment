package schedule.cpu;/*
 * @author Kaoso
 * @since 2018.06.01
 */

import schedule.cpu.pcb.PriorityPcb;
import schedule.cpu.priority.Priority;
import schedule.cpu.simpleroundrobin.SimpleRoundRobin;
import schedule.cpu.pcb.Pcb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author kaoso
 */
public class Experiment {

    /**
     *  使用算法标记
     */
    private int algorithm;
    /**
     * 创建最高优先级优先算法
     */
    private Priority pa = new Priority();
    /**
     * 创建简单轮转法算法
     */
    private SimpleRoundRobin sa = new SimpleRoundRobin();

    public static void main(String[] args) {
        System.out.println("--------------------------");
        System.out.println("------实验一：进程调度------");
        System.out.println("--------------------------\n");
        // 创建实验
        Experiment exp = new Experiment();
        exp.choice();//选择算法
        exp.setProcess();//输入进程信息
        exp.exec();//执行算法
        System.out.println("实验结束");
    }

    public void choice() {
        /*
         * 选择算法
         */
        // 安全读取输入
        try {
            System.out.println("请输入要使用的算法：（输入“1”或“2”或其他任意字符）");
            System.out.println("1：最高优先级优先调度算法");
            System.out.println("2：简单轮转法调度算法");
            System.out.println("其他：退出");

            /*
              这里隐含地实现了一个“请按任意键继续”的方法，即只有default时（可以更简洁），按任意键
              可以进行其他操作。
             */
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String str = in.readLine();
            // 如果限定输入整数
            int chioce = Integer.parseInt(str);
            switch (chioce) {
                case 1:
                    this.algorithm = 1;
                    System.out.println("正在使用最高优先级优先调度算法");
                    return;
                case 2:
                    this.algorithm = 2;
                    System.out.println("正在使用简单轮转法调度算法");
                    return;
                default:
                    System.exit(0);
            }
        } catch (NumberFormatException | IOException e) {
            System.out.println("异常退出");
            System.exit(0);
        }
    }

    private void setProcess() {
        /*
         *  初始化进程
         */
        System.out.println("请输入要创建的进程数：");
        Scanner in = new Scanner(System.in);
        // 记录进程数量
        final int quantity = in.nextInt();
        // 分别对应两种算法输入进程信息，进程直接命名为输入序号
        if (this.algorithm == 1) {
            for (int i = 0; i < quantity; i++) {
                System.out.println("当前进程：" + (i + 1));
                pa.setPc(new PriorityPcb(String.valueOf(i + 1)));
            }
        } else {
            for (int i = 0; i < quantity; i++) {
                System.out.println("当前进程：" + (i + 1));
                sa.setPc(new Pcb(String.valueOf(i + 1)));
            }
        }
    }

    private void exec() {
        if (this.algorithm == 1) {
            pa.priorityAlgorithm();
        } else {
            sa.simpleRoundRobinAlgorithm();
        }
    }
}

package pcb;

import java.util.Scanner;

/**
 * @author kaoso
 */
public class Pcb {
    /**
     * 进程名
     */
    private String name;
    /**
     * 要求运行时间
     */
    private int requestTime;
    /**
     * 已运行时间
     */
    private int runningTime = 0;
    /**
     * 状态
     */
    private char status = 'w';

    /**
     * 初始化进程信息
     * @param n 进程名
     */
    public Pcb(String n) {
        this.name = n;
        Scanner in = new Scanner(System.in);
        System.out.println("请输入要求运行时间：");
        this.requestTime = in.nextInt();
    }

    /**
     * 获取进程名
     * @return 进程名
     */
    private String getName() {
        return this.name;
    }

    /**
     * 获取要求运行时间
     * @return 要求运行时间
     */
    public int getRequestTime() {
        return this.requestTime;
    }

    /**
     * 获取已运行时间
     * @return 已运行时间
     */
    public int getRunningTime() {
        return this.runningTime;
    }

    /**
     * 获取状态
     * @return 状态
     */
    private char getStatus() {
        return this.status;
    }

    /**
     * 设置状态
     * @param stat 状态
     */
    public void setStatus(char stat) {
        this.status = stat;
    }

    /**
     * 已运行时间加1
     */
    public void changeAtime() {
        this.runningTime++;
    }

    public void show() {//展示PCB信息
        System.out.print(this.getName() + "\t\t");
        System.out.print(this.getStatus() + "\t\t");
        System.out.print(this.getRequestTime() + "\t\t");
        System.out.print(this.getRunningTime() + "\t\t");
    }
}
package schedule.cpu.method;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 展示当前容器中的进程信息，响应输入
 */
public abstract class AbstractMethod {
    /**
     * 输出说明文字
     */
    public abstract void show();

    protected void goon() {
        System.out.println("请按回车键继续执行...");
        // 读取回车键
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String str = in.readLine();
        } catch (NumberFormatException | IOException e) {
            System.out.println("异常退出");
            System.exit(0);
        }
    }
}

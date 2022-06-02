package io.github.jiashunx.toy.prevent_screen_lock;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main implements Runnable {

    public static void main(String[] args) {
        // 30s移动一次.
        POOL.scheduleWithFixedDelay(new Main(), 1L, 30L, TimeUnit.SECONDS);
    }

    private static final ScheduledThreadPoolExecutor POOL = new ScheduledThreadPoolExecutor(1);

    @Override
    public void run() {
        // 获取鼠标位置
        Point point = MouseInfo.getPointerInfo().getLocation();
        // x坐标(屏幕左上角开始向右为x轴正)
        int originX = Double.valueOf(point.getX()).intValue();
        // y坐标(屏幕左上角开始向下为y轴正)
        int originY = Double.valueOf(point.getY()).intValue();
        try {
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            if (originX < 0 || originX > dimension.width) {
                System.err.println("[" + getCurrentTime() + "] originX=" + originX + ", 置为1");
                originX = 1;
            }
            if (originY < 0 || originY > dimension.height) {
                System.err.println("[" + getCurrentTime() + "] originY=" + originY + ", 置为1");
                originY = 1;
            }
            Robot robot = new Robot();
            robot.mouseMove(originX - 1, originY);
            System.out.println("[" + getCurrentTime() + "] move mouse: x=" + (originX - 1) + ", y=" + originY);
            robot.mouseMove(originX, originY);
            System.out.println("[" + getCurrentTime() + "] move mouse[origin]: x=" + originX + ", y=" + originY);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return format.format(new Date());
    }

}

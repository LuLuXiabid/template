package thread.xiao.threadsafe;

import thread.xiao.DemoTest01;

public class ThreadSafe01 {
    static class Thread01 implements Runnable {
        private static volatile int count = 0;

        @Override
        public void run() {
            while (count < 20)
                synchronized (this) {
                    System.out.println(Thread.currentThread().getName() + "   I=" + count++);
                    notify();
                    try {
                        wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    public static void main(String[] args) {
        Thread01 demoTest01 = new Thread01();
        new Thread(demoTest01,"偶数线程").start();
        new Thread(demoTest01,"奇数线程").start();
    }
}

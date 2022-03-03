package thread.xiao.test;

public class ThreadDemo10 extends Thread {
    static final Object obj = new Object();

    @Override
    public void run() {
        synchronized (obj) {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "   i=" + i);
            }
        }
    }
    public static void main(String[] args) {
        ThreadDemo10 thread_1 = new ThreadDemo10();
        thread_1.start();
        ThreadDemo10 thread_2 = new ThreadDemo10();
        thread_2.start();
    }
}

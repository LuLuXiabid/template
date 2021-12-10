package thread.xiao.test;

public class ThreadDemo09 implements Runnable {

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
        ThreadDemo09 threadDemo09 = new ThreadDemo09();
        new Thread(threadDemo09).start();
        new Thread(threadDemo09).start();
    }
}

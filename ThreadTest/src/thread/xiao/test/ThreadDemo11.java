package thread.xiao.test;

public class ThreadDemo11 implements Runnable {

    static final Object obj = new Object();

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (obj) {
                System.out.println(Thread.currentThread().getName() + "   i=" + i);
            }
        }
    }

    public static void main(String[] args) {
        ThreadDemo11 threadDemo11 = new ThreadDemo11();
        new Thread(threadDemo11).start();
        new Thread(threadDemo11).start();
    }
}

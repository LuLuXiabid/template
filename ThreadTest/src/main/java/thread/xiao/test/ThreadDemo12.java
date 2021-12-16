package thread.xiao.test;


public class ThreadDemo12 extends Thread {
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
        ThreadDemo12 thread_1 = new ThreadDemo12();
        thread_1.start();
        ThreadDemo12 thread_2 = new ThreadDemo12();
        thread_2.start();
    }
}


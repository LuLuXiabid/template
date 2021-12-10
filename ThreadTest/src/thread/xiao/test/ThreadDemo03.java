package thread.xiao.test;


//ThreadDemo01基础上加上同步块，锁this
public class ThreadDemo03 implements Runnable {

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "    i=" + i);
            }
        }
    }

    public static void main(String[] args) {
        ThreadDemo03 threadDemo03 = new ThreadDemo03();
        new Thread(threadDemo03).start();
        new Thread(threadDemo03).start();
    }
}

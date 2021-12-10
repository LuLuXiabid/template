package thread.xiao.test;

//ThreadDemo01基础上加上同步块，锁this,synchronized放for循环里
public class ThreadDemo05 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (this){
                System.out.println(Thread.currentThread().getName()+"   i=" + i);
            }
        }
    }
    public static void main(String[] args) {
        ThreadDemo05 threadDemo05 = new ThreadDemo05();
        new Thread(threadDemo05).start();
        new Thread(threadDemo05).start();
    }
}

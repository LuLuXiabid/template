package thread.xiao.test;

//ThreadDemo02基础上加上同步块，锁this,synchronized放for循环里
public class ThreadDemo06 extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (this){
                System.out.println(currentThread().getName()+"   i=" + i);
            }
        }
    }

    public static void main(String[] args) {
        ThreadDemo06 thread_1 = new ThreadDemo06();
        ThreadDemo06 thread_2 = new ThreadDemo06();
        thread_1.start();
        thread_2.start();
    }
}
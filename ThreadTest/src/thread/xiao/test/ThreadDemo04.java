package thread.xiao.test;

//ThreadDemo02基础上加上同步块，锁this
public class ThreadDemo04 extends Thread {
    @Override
    public void run() {
        synchronized (this){
            for (int i = 0; i < 10; i++) {
                System.out.println(currentThread().getName()+"   i=" + i);
            }
        }

    }
    public static void main(String[] args) {
        ThreadDemo04 thread_1 = new ThreadDemo04();
        thread_1.start();
        ThreadDemo04 thread_2 = new ThreadDemo04();
        thread_2.start();
    }
}
    


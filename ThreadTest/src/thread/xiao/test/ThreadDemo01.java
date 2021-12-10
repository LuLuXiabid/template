package thread.xiao.test;


//Runnable创建线程
public class ThreadDemo01 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "    i=" + i);
        }
    }

    public static void main(String[] args) {
        ThreadDemo01 threadDemo01 = new ThreadDemo01();
        new Thread(threadDemo01).start();
        new Thread(threadDemo01).start();
    }
}


package thread.xiao.test;

public class ThreadDemo08 extends Thread {
    @Override
    public void run() {
        sout();
    }
    synchronized void sout(){
        for (int i = 0; i < 10; i++) {
            System.out.println(currentThread().getName()+"   i=" + i);
        }
    }
    public static void main(String[] args) {
        ThreadDemo08 thread_1 = new ThreadDemo08();
        thread_1.start();
        ThreadDemo08 thread_2 = new ThreadDemo08();
        thread_2.start();
    }
}

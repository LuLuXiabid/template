package thread.xiao.test;


//Runnable使用同步方法
public class ThreadDemo07 implements Runnable {
    @Override
    public void run() {
        sout();
    }
    synchronized void sout(){
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"   i=" + i);
        }
    }
    public static void main(String[] args) {
        ThreadDemo07 threadDemo07 = new ThreadDemo07();
        new Thread(threadDemo07).start();
        new Thread(threadDemo07).start();
    }
}

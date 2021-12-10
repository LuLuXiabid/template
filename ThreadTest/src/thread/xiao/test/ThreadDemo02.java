package thread.xiao.test;


//Thread创建线程
public class ThreadDemo02 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(currentThread().getName() + "   i=" + i);
        }
    }

    public static void main(String[] args) {
        ThreadDemo02 thread_1 = new ThreadDemo02();
        thread_1.start();
//        //Thread不支持一个对象，多次start()
//        thread_1.start();
        ThreadDemo02 thread_2 = new ThreadDemo02();
        thread_2.start();
    }
}


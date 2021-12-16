package thread.xiao;

public class test01 {

    static class T1 implements Runnable {
        private int a = 0;

        @Override
        public void run() {
            while (a < 10)
                synchronized (this) {
                    System.out.println(Thread.currentThread().getName() + "    I=" + a++);
                }
        }
    }

    static class T1_1 extends Thread {
        private int a = 0;

        @Override
        public void run() {
            while (a < 10)
                synchronized (this) {
                    System.out.println(Thread.currentThread().getName() + "    I=" + a++);
                }
        }
    }


    static class T2 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (this) {
                    System.out.println(Thread.currentThread().getName() + "    I=" + i);
                }
            }
        }
    }


    public static void main(String[] args) {
        //三个线程合力从零加到10（有线程安全问题）
        T1 t1 = new T1();
        new Thread(t1).start();
        new Thread(t1).start();
        new Thread(t1).start();


        //三个线程每个线程任务是从0加到10
//        T1_1 t1 = new T1_1();
//        t1.start();
//        T1_1 t2 = new T1_1();
//        t2.start();
//        T1_1 t3 = new T1_1();
//        t3.start();


//        //每个线程各自循环10次，从0加到10
//        T2 t2 = new T2();
//        new Thread(t2).start();
//        new Thread(t2).start();
//        new Thread(t2).start();
    }
}

package thread.xiao.producerconsumer;

public class Test01 {

    static final Object obj = new Object();
    static volatile int a = 0;


    static class Pud implements Runnable {
        @Override
        public void run() {
            synchronized (obj) {
                obj.notifyAll();
                if (a >= 10) {
                    System.out.println("仓库已满！" + Thread.currentThread().getName() + "休息！");
                    try {
                        obj.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    a++;
                    System.out.println(Thread.currentThread().getName() + "  生产一个，现在数量：" + a);
                }


            }
        }
    }

    static class Cus implements Runnable {
        @Override
        public void run() {
            synchronized (obj) {
                obj.notifyAll();
                if (a <= 0) {
                    System.out.println("仓库以空！请生产者生产！");
                    try {
                        obj.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    a--;
                    System.out.println(Thread.currentThread().getName() + "  消费一个，现在数量：" + a);
                }
            }
        }
    }

    public static void main(String[] args) {
        Pud pud = new Pud();
//            for (int i = 0; i < 4; i++) {
//                new Thread(pud, "生产者" + i).start();
//            }
//            Cus cus = new Cus();
//            for (int i = 0; i < 40; i++) {
//                new Thread(cus, "消费者" + i).start();
//            }

        for (int i = 0; i < 40; i++) {
            new Thread(pud, "生产者" + i).start();
        }
        Cus cus = new Cus();
        for (int i = 0; i < 4; i++) {
            new Thread(cus, "消费者" + i).start();
        }

    }
}


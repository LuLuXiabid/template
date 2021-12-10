package thread.xiao.producerconsumer;

public class Producer01 {

    static final Object obj = new Object();
    static final Object objp = new Object();
    static final Object objc = new Object();

    static volatile boolean flag = false;

    static volatile int a = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                synchronized (obj){
                    synchronized (objp) {
                        obj.notify();
                        objc.notify();
                        if (a < 10) {
                            a++;
                            System.out.println(Thread.currentThread().getName() + "  生产一个，现在数量：" + a);
                        } else {
                            System.out.println("仓库已满！" + Thread.currentThread().getName() + "休息！");
                            try {
                                obj.wait();
                                objp.wait();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }

            }, "生产者").start();


            new Thread(() -> {
                synchronized (obj){
                    synchronized (objc) {
                        obj.notify();
                        objp.notify();
                        if (a > 0) {
                            a--;
                            System.out.println(Thread.currentThread().getName() + "  消费一个，现在数量：" + a);
                        } else {
                            System.out.println("仓库以空！请生产者生产！");
                            try{
                                objc.wait();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }, "消费者").start();

        }



    }
}

package thread.xiao;
//
/*
* 多线程打印奇偶数的方式有很多:
* 1、信号量
* 2、wait（），notify（）
* 3、原子类
*
**/
public class DemoTest01  implements Runnable {

    private static volatile Boolean FALG = true;

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            synchronized (this){
                while (FALG){
                    System.out.println(Thread.currentThread().getName() + "    I =" + i);
                    FALG = Boolean.FALSE;
                    try {
                        wait();
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                while (!FALG){
                    System.out.println(Thread.currentThread().getName() + "    I =" + i);
                    FALG = Boolean.TRUE;
                    notify();
                }
            }

        }
    }


    public static void main(String[] args) {
        DemoTest01 demoTest01 = new DemoTest01();
        new Thread(demoTest01).start();
        new Thread(demoTest01).start();
    }
}

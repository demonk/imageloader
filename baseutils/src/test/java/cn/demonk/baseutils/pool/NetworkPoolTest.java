package cn.demonk.baseutils.pool;


import org.junit.Test;

/**
 * Created by ligs on 6/5/16.
 */
public class NetworkPoolTest {

    @Test
    public void testExec() throws Exception {

        NetworkPool.instance().exec(new Runnable() {

            @Override
            public void run() {
                System.out.println("start");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("finish");
            }
        });

        Thread.sleep(5000);
    }

    @Test
    public void testStop() throws Exception {
        NetworkPool.instance().exec(new Runnable() {

            @Override
            public void run() {
                System.out.println("start");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("finish");
            }
        });
        NetworkPool.instance().stop(true);
        Thread.sleep(5000);
    }
}
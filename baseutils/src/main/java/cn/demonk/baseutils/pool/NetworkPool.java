package cn.demonk.baseutils.pool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.demonk.baseutils.Singleton;

/**
 * Created by ligs on 6/5/16.
 */
public class NetworkPool {

    private static final int DEFAULT_THREAD_NUM = 5;

    private static NetworkPool instance = null;

    private ExecutorService mThreadPool = null;

    public static NetworkPool instance() {
        if (instance == null)
            synchronized (NetworkPool.class) {
                if (instance == null)
                    instance = new NetworkPool();
            }
        return instance;
    }

    private NetworkPool() {
        mThreadPool = Executors.newFixedThreadPool(DEFAULT_THREAD_NUM);
    }

    public void exec(Runnable task) {
        mThreadPool.execute(task);
    }

    public void stop(boolean force) {
        if (!mThreadPool.isTerminated()) {
            if (force)
                mThreadPool.shutdownNow();
            else
                mThreadPool.shutdown();
        }
    }
}

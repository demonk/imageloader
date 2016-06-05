package cn.demonk.baseutils.handler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ligs on 6/5/16.
 */
public class AsyncHandler {

    private static final int TAG_ADD = 1;
    private static final int TAG_REMOVE = 2;

    private HandlerThread mHandlerThread = null;

    private static AsyncHandler instance = null;

    private Looper mLoop = null;
    private Handler mSubHandler = null;

    public static final AsyncHandler instance() {
        if (instance == null)
            synchronized (AsyncHandler.class) {
                if (instance == null) {
                    synchronized (AsyncHandler.class) {
                        instance = new AsyncHandler();
                    }
                }
            }

        return instance;
    }

    private AsyncHandler() {
        mHandlerThread = new HandlerThread("AsyncHandler");
        this.mHandlerThread.start();

        mLoop = mHandlerThread.getLooper();
        mSubHandler = new Handler(mLoop) {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case TAG_ADD:
                        if (msg.obj instanceof HandleTask) {
                            HandleTask task = (HandleTask) msg.obj;
                            if (task != null) {
                                task.run();
                            }
                        }
                        break;
                    case TAG_REMOVE:
                        break;

                    default:
                        break;
                }

            }
        };
    }

    public void append(HandleTask task) {
        Message msg = Message.obtain();
        msg.obj = task;
        msg.what = TAG_ADD;
        mSubHandler.sendMessage(msg);
    }


    /**
     * 停止所有任务
     */
    public void stop() {
        mHandlerThread.quit();
    }
}

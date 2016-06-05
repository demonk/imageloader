package cn.demonk.baseutils.handler;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by ligs on 6/5/16.
 */
public class UIHandler {

    public static final Handler sMainHandler = new Handler(Looper.getMainLooper());

    public static final void post(Runnable task) {
        if (task != null) {
            if (Looper.myLooper() == Looper.getMainLooper())
                task.run();
            else
                sMainHandler.post(task);
        }
    }
}

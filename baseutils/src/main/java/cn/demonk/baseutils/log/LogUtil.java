package cn.demonk.baseutils.log;

import android.util.Log;

import cn.demonk.baseutils.str.StringUtil;

/**
 * Created by ligs on 5/29/16.
 */
public class LogUtil {

    private static final String TAG = "demonk";

    public static final boolean TESTING = true;

    public static final void e(String msg) {
        if (StringUtil.isEmpty(msg))
            return;

        print(msg);
        Log.e(TAG, msg);
    }

    public static final void w(String msg) {
        if (StringUtil.isEmpty(msg))
            return;
        print(msg);
        Log.w(TAG, msg);
    }

    public static final void i(String msg) {
        if (StringUtil.isEmpty(msg))
            return;
        print(msg);
        Log.i(TAG, msg);
    }

    public static final void d(String msg) {
        if (StringUtil.isEmpty(msg))
            return;
        print(msg);
        Log.d(TAG, msg);
    }

    private static final void print(String msg) {
        if (TESTING)
            System.out.println(msg);
    }
}

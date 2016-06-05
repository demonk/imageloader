package cn.demonk.imageloader.task.getimage;

import android.content.Context;
import android.graphics.drawable.Drawable;

import cn.demonk.baseutils.handler.HandleCallback;
import cn.demonk.baseutils.pool.NetworkPool;

/**
 * 用于真正获取和缓存图片的
 * Created by ligs on 6/5/16.
 */
public class ImageProvider {

    private static final String SCHEME_HTTP = "http";
    private static final String SCHEME_HTTPS = "https";
    private static final String SCHEME_FILE = "file";

    private String mUrl = "";
    private Context mCtx = null;

    public ImageProvider(Context ctx, String url) {
        this.mUrl = url;
        this.mCtx = ctx;
    }

    public Drawable getImageDrawable(HandleCallback callback) {
        String urlLowcase = mUrl.toLowerCase();
        Drawable drawable = null;
        if (urlLowcase.startsWith(SCHEME_HTTP) || urlLowcase.startsWith((SCHEME_HTTPS))) {
            drawable = getImageFromNetwork(callback);
        } else {
            if (!urlLowcase.startsWith(SCHEME_FILE))
                urlLowcase = "file://" + urlLowcase;

            drawable = getImageFromLocal(callback);
        }

        return null;
    }


    private Drawable getImageFromNetwork(HandleCallback callback) {
        Runnable task = new GetImageFromNetworkTask(mCtx, this.mUrl, callback);
        NetworkPool.instance().exec(task);
        return null;
    }

    private Drawable getImageFromLocal(HandleCallback callback) {
        Runnable task = new GetImageFromLocalTask(mCtx, this.mUrl, callback);


        return null;
    }
}

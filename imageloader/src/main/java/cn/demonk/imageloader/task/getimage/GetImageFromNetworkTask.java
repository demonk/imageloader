package cn.demonk.imageloader.task.getimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.IOException;

import cn.demonk.baseutils.graph.BitmapUtil;
import cn.demonk.baseutils.handler.HandleCallback;
import cn.demonk.baseutils.handler.HandleResponse;
import cn.demonk.baseutils.handler.HandleTask;
import cn.demonk.imageloader.net.Get;

/**
 * Created by ligs on 6/5/16.
 */
public class GetImageFromNetworkTask extends HandleTask<Drawable> {

    private String mUrl;
    private Context mCtx;

    public GetImageFromNetworkTask(Context ctx, String url, HandleCallback callback) {
        super(callback);
        this.mUrl = url;
        this.mCtx = ctx;
    }

    @Override
    public void run() {
        Drawable drawable = null;
        try {
            Bitmap bm = Get.getBitmap(mUrl);
            drawable = BitmapUtil.bitmap2Drawable(this.mCtx.getResources(), bm);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HandleResponse resp = packResponse(drawable);
        mCallback.onDone(resp);
    }

    @Override
    protected HandleResponse<Drawable> packResponse(Drawable obj) {
        HandleResponse<Drawable> response = new HandleResponse<>();
        if (obj != null) {
            response.code = HandleResponse.CODE_SUCC;
            response.resObj = obj;
        } else {
            response.code = HandleResponse.CODE_FAIL;
        }
        return response;
    }
}

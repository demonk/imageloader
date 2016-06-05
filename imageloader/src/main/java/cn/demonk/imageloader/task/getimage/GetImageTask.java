package cn.demonk.imageloader.task.getimage;

import android.content.Context;
import android.graphics.drawable.Drawable;

import cn.demonk.baseutils.handler.Callback;
import cn.demonk.baseutils.handler.HandleCallback;
import cn.demonk.baseutils.handler.HandleResponse;
import cn.demonk.baseutils.handler.HandleTask;

/**
 * Created by ligs on 6/5/16.
 */
public class GetImageTask extends HandleTask<Drawable> {

    private ImageProvider mProvider = null;

    /**
     * 可以接受远程 url,本地
     *
     * @param url
     * @param callback
     */
    public GetImageTask(Context ctx, String url, HandleCallback callback) {
        super(callback);

        mProvider = new ImageProvider(ctx,url);
    }

    @Override
    public void run() {
        mProvider.getImageDrawable(new HandleCallback() {
            @Override
            public void onDone(HandleResponse response) {
                mCallback.onDone(response);
            }
        });
//        HandleResponse response = packResponse(result);
    }

    @Override
    protected HandleResponse packResponse(Drawable obj) {
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

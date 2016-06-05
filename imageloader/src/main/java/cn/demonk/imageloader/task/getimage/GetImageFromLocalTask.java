package cn.demonk.imageloader.task.getimage;

import android.content.Context;
import android.graphics.drawable.Drawable;

import cn.demonk.baseutils.handler.HandleCallback;
import cn.demonk.baseutils.handler.HandleResponse;
import cn.demonk.baseutils.handler.HandleTask;

/**
 * Created by ligs on 6/5/16.
 */
public class GetImageFromLocalTask extends HandleTask<Drawable> {

    public GetImageFromLocalTask(Context ctx, String path, HandleCallback callback) {
        super(callback);
    }

    @Override
    public void run() {

    }

    @Override
    protected HandleResponse<Drawable> packResponse(Drawable obj) {
        return null;
    }
}

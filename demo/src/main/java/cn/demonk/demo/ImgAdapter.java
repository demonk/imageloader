package cn.demonk.demo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import cn.demonk.baseutils.handler.HandleCallback;
import cn.demonk.baseutils.handler.HandleResponse;
import cn.demonk.baseutils.handler.UIHandler;
import cn.demonk.imageloader.task.getimage.GetImageTask;

/**
 * Created by ligs on 6/5/16.
 */
public class ImgAdapter extends ArrayAdapter<String> {

    public ImgAdapter(Context context, int resource, String[] items) {
        super(context, resource, items);
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            convertView = inflater.inflate(R.layout.image, viewGroup, false);
        }

        ImageView imv = (ImageView) convertView.findViewById(R.id.im_ct);
        imv.setImageResource(R.drawable.pictures_no);

        //将真正的pic设置到imv中
        getImage(position, imv);

        return convertView;
    }

    private void getImage(final int position, final ImageView imv) {
        String url = this.getItem(position);
        GetImageTask task = new GetImageTask(this.getContext(), url, new HandleCallback() {

            @Override
            public void onDone(HandleResponse response) {
                if (response.code == HandleResponse.CODE_SUCC) {
                    Object resObj = response.resObj;
                    if (resObj instanceof Drawable) {
                        Drawable drawable = (Drawable) resObj;

                        setImage(imv, drawable);
                    }
                }
            }
        });

        task.run();
    }

    private void setImage(final ImageView imv, final Drawable drawable) {
        UIHandler.post(new Runnable() {

            @Override
            public void run() {
                imv.setImageDrawable(drawable);
            }
        });
    }
}

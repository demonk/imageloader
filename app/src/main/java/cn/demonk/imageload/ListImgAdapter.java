package cn.demonk.imageload;

import android.content.Context;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.zip.Inflater;

/**
 * Created by ligs on 5/22/16.
 */
public class ListImgAdapter extends ArrayAdapter<String> {

    private ImageLoader mLoader = null;

    public ListImgAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);

        mLoader = ImageLoader.instance();
    }

    public View getView(int position, View convertView, ViewGroup viewgroup) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            convertView = inflater.inflate(R.layout.image, viewgroup, false);
        }

        ImageView view = (ImageView) convertView.findViewById(R.id.id_img);
        view.setImageResource(R.drawable.pictures_no);

        mLoader.loadImage(getItem(position), view, true);
        Log.e("demonk", "pos=" + position);
        return convertView;

    }
}

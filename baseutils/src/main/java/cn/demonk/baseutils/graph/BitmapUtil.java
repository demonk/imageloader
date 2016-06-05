package cn.demonk.baseutils.graph;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by ligs on 6/5/16.
 */
public class BitmapUtil {

    public static Drawable bitmap2Drawable(Resources res, Bitmap bm) {
        BitmapDrawable bd = null;
        if (bm != null) {
            bd = new BitmapDrawable(res, bm);
        }

        return bd;
    }


    public static Bitmap drawable2Bitmap(Drawable drawable) {
        return null;
    }

}

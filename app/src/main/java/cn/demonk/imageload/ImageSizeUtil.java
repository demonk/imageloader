package cn.demonk.imageload;

import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.view.ViewGroup.LayoutParams;

import java.lang.reflect.Field;

/**
 * Created by ligs on 5/29/16.
 */
public class ImageSizeUtil {
    public static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeight);

            inSampleSize = Math.max(widthRadio, heightRadio);
        }
        return inSampleSize;
    }

    public static ImageSize getImageViewSize(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();

        LayoutParams params = imageView.getLayoutParams();

        int width = imageView.getWidth();
        width = width <= 0 ? params.width : width;
        width = width < 0 ? getImageViewFieldValue(imageView, "mMaxWidth") : width;
        width = width <= 0 ? displayMetrics.widthPixels : width;

        int height = imageView.getHeight();
        height = height <= 0 ? params.height : height;
        height = height <= 0 ? getImageViewFieldValue(imageView, "mMaxHeight") : height;
        height = height <= 0 ? displayMetrics.heightPixels:height;

        imageSize.width = width;
        imageSize.height = height;

        return imageSize;
    }

    private static int getImageViewFieldValue(Object obj, String fieldName) {
        int value = 0;

        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = field.getInt(obj);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}

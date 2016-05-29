package cn.demonk.imageload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ligs on 5/29/16.
 */
public class DownloadImgUtils {
    public static boolean downloadImgByUrl(String urlStr, File file) {
        FileOutputStream fos = null;
        InputStream is = null;

        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
            fos = new FileOutputStream(file);

            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(is);
            StreamUtil.close(fos);
        }
        return false;
    }

    public static Bitmap downloadImgByUrl(String urlStr, ImageView imageView) {
        InputStream is = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(conn.getInputStream());
            is.mark(is.available());

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            ImageSize imageSize = ImageSizeUtil.getImageViewSize(imageView);
            options.inSampleSize = ImageSizeUtil.caculateInSampleSize(options, imageSize.width, imageSize.height);

            options.inJustDecodeBounds = false;

            is.reset();
            Bitmap bm = BitmapFactory.decodeStream(is, null, options);

            conn.disconnect();
            return bm;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(is);
        }
        return null;
    }
}

package cn.demonk.imageloader.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

import cn.demonk.baseutils.log.LogUtil;
import cn.demonk.baseutils.str.StringUtil;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ligs on 5/29/16.
 */
public class Get {

    public static void getHeader(String url) throws Exception {
        OkHttpClient client = NetWorker.instance().getClient();

        if (StringUtil.isEmpty(url))
            return;

        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();

        if (!response.isSuccessful())
            throw new Exception("Unexpected code " + response);

        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            LogUtil.i("header --> " + responseHeaders.name(i) + " : " + responseHeaders.value(i));
        }
        LogUtil.d(response.body().toString());
    }

    /**
     * 从远程获取一个图像
     * @param url
     * @return
     * @throws IOException
     */
    public static Bitmap getBitmap(String url) throws IOException {
        OkHttpClient client = NetWorker.instance().getClient();
        Bitmap bm = null;

        if (!StringUtil.isEmpty(url)) {
            Request req = new Request.Builder().url(url).build();
            Response resp = client.newCall(req).execute();

            InputStream input = resp.body().byteStream();
            bm = BitmapFactory.decodeStream(input);
        }

        return bm;
    }
}

package cn.demonk.imageloader.net;

import java.io.IOException;

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
}

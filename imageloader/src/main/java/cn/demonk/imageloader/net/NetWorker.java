package cn.demonk.imageloader.net;

import okhttp3.OkHttpClient;

/**
 * Created by ligs on 5/29/16.
 */
public class NetWorker {
    private static final OkHttpClient sHttpClient = new OkHttpClient();

    private static final NetWorker INSTANCE = new NetWorker();

    public static final NetWorker instance() {
        return INSTANCE;
    }


    public OkHttpClient getClient() {
        return sHttpClient;
    }
    //基础服务
}

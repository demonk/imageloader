package cn.demonk.baseutils.handler;

/**
 * 异步响应回调
 * Created by ligs on 6/5/16.
 */
public class HandleResponse<T> {
    public static final int CODE_SUCC=1;
    public static final int CODE_FAIL=-1;

    public int code;
    public T resObj;

}

package cn.demonk.baseutils.handler;

/**
 * Created by ligs on 6/5/16.
 */
public abstract class HandleTask<T> implements Runnable {

    public HandleCallback mCallback = null;

    public HandleTask(HandleCallback callback) {
        this.mCallback = callback;
    }

    protected abstract HandleResponse<T> packResponse(T obj);

}

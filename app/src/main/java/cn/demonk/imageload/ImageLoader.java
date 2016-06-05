package cn.demonk.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by ligs on 5/29/16.
 */
public class ImageLoader {

    private boolean isDiskCacheEnable = true;

    public enum Type {
        FIFO, LIFO
    }

    /**
     * 线程池
     */
    private ExecutorService mThreadPool;

    private Thread mPoolThread;
    private Handler mPoolThreadHandler;

    private LinkedList<Runnable> mTaskQueue;

    private int mThreadCount;
    private Type mType;

    private Semaphore mSemaphoreThreadPool;

    private LruCache<String, Bitmap> mLruCache;

    private static final int DEFAULT_THREAD_COUNT = 3;

    private Handler mUIHandler;

    private static ImageLoader sInstance = new ImageLoader(DEFAULT_THREAD_COUNT, Type.LIFO);

    public static final ImageLoader instance() {
        return sInstance;
    }

    private ImageLoader(int threadCount, Type type) {
        this.mThreadCount = threadCount;
        this.mType = type;

        initBackThread();

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 8;

        mLruCache = new LruCache<String, Bitmap>(cacheMemory) {
            protected int sizeOf(String key, Bitmap value) {
                //返回一个bitmap的大小
                return value.getRowBytes() * value.getHeight();
            }
        };

        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<Runnable>();
        mType = type;
        mSemaphoreThreadPool = new Semaphore(threadCount);//定义N个信号量，每个线程互斥
    }

    public void loadImage(final String path, final ImageView imageView, final boolean isFromNet) {
        imageView.setTag(path);
        if (mUIHandler == null) {
            mUIHandler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
                    Bitmap bm = holder.bitmap;
                    ImageView imageView = holder.imageView;
                    String path = holder.path;

                    if (imageView.getTag().toString().equals(path)) {
                        imageView.setImageBitmap(bm);
                    }
                }
            };
        }

        Bitmap bm = null;//getBitmapFromLruCache(path);
        if (bm != null) {
            refreshBitmap(path, imageView, bm);
        } else {
            addTask(buildTask(path, imageView, isFromNet));
        }
    }

    private Runnable buildTask(final String path, final ImageView imageView, final boolean isFromNet) {
        Runnable run = new Runnable() {
            public void run() {
                Bitmap bm = null;
                if (isFromNet) {
                    bm = loadImageFromNet(path, imageView);
                } else {
                    bm = loadImageFromLocal(path, imageView);
                }

                addBitmapToLruCache(path, bm);
                refreshBitmap(path, imageView, bm);
                mSemaphoreThreadPool.release();
            }
        };
        return run;
    }

    private void addBitmapToLruCache(String path, Bitmap bm) {
        if (getBitmapFromLruCache(path) == null) {
            if (bm != null) {
                mLruCache.put(path, bm);
            }
        }
    }

    private Bitmap loadImageFromNet(String path, ImageView imageView) {
        File file = getDiskCacheDir(imageView.getContext(), HashUtil.md5(path));
        Bitmap bm = null;
        if (file.exists()) {
            bm = loadImageFromLocal(file.getAbsolutePath(), imageView);
        } else {
            if (isDiskCacheEnable) {
                bm = DownloadImgUtils.downloadImgByUrl(path, file) ? loadImageFromLocal(file.getAbsolutePath(), imageView) : null;
            } else {
                bm = DownloadImgUtils.downloadImgByUrl(path, imageView);
            }
        }
        return bm;
    }

    private Bitmap loadImageFromLocal(String path, ImageView imageView) {
        Bitmap bm;

        ImageSize size = ImageSizeUtil.getImageViewSize(imageView);
        bm = decodeSampleBitmapFromPath(path, size.width, size.height);
        return bm;
    }

    private Bitmap decodeSampleBitmapFromPath(String path, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = ImageSizeUtil.caculateInSampleSize(options, width, height);
        //再次解释
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }


    private File getDiskCacheDir(Context ctx, String name) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cachePath = ctx.getExternalCacheDir().getPath();
        } else {
            cachePath = ctx.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + name);

    }

    private synchronized void addTask(Runnable runnable) {
        mTaskQueue.add(runnable);

        try {
            if (mPoolThreadHandler == null) {
                mSemaphoreThreadPool.acquire();
            }
        } catch (InterruptedException e) {
        }

        mPoolThreadHandler.sendEmptyMessage(0x110);
    }

    private void refreshBitmap(String path, ImageView imageView, Bitmap bm) {
        Message message = Message.obtain();
        ImgBeanHolder holder = new ImgBeanHolder();
        holder.bitmap = bm;
        holder.path = path;
        holder.imageView = imageView;
        message.obj = holder;
        mUIHandler.sendMessage(message);
    }

    private Bitmap getBitmapFromLruCache(String key) {
        return mLruCache.get(key);
    }

    private void initBackThread() {
        mPoolThread = new Thread() {

            public void run() {
                Looper.prepare();

                mPoolThreadHandler = new Handler() {
                    public void handleMessage(Message msg) {
                        mThreadPool.execute(getTask());

                        try {
                            mSemaphoreThreadPool.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                mSemaphoreThreadPool.release();
                Looper.loop();
            }
        };
        mPoolThread.start();
    }

    private Runnable getTask() {
        if (mType == Type.FIFO) {
            return mTaskQueue.removeFirst();
        } else if (mType == Type.LIFO) {
            return mTaskQueue.removeLast();
        }
        return null;
    }
}

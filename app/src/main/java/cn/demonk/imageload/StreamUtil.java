package cn.demonk.imageload;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by ligs on 5/29/16.
 */
public class StreamUtil {
    public static void close(Closeable obj)
    {
        if(obj!=null)
        {
            try {
                obj.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

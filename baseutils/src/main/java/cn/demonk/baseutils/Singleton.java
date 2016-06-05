package cn.demonk.baseutils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ligs on 6/5/16.
 */
@Deprecated
public class Singleton {

    private static Set<Class<?>> sMutexSet = new HashSet<Class<?>>();

    public static <T> T getInstance(Class<?> clazz, Object... params) {
        synchronized (sMutexSet) {
            if (sMutexSet.contains(clazz))
                return null;
            else
                sMutexSet.add(clazz);
        }

        return newInstance(clazz, params);
    }

    private static <T> T newInstance(Class<?> clazz, Object... params) {
        T instance = null;
        if (clazz != null) {
            synchronized (clazz) {
                Class[] paramsType = getParamsType(params);
                try {
                    Constructor constructor = clazz.getConstructor(paramsType);
                    instance = (T) constructor.newInstance(params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return instance;
    }

    private static Class[] getParamsType(Object... params) {
        Class<?>[] clazz = new Class<?>[params.length];
        int i = 0;
        for (Object param : params) {
            clazz[i++] = param.getClass();
        }
        return clazz;
    }
}

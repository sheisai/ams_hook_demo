package com.antonio.ams_hook_demo;

import android.os.Build;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ams hook帮助类
 *
 * @author antonio
 * @version 1.0
 */
public class AmsHookHelper {
    private final static String TAG = "AmsHookHelper";

    /**
     * hook掉ActivityManager
     */
    public static void hookActivityManager() {
        try {
            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityTaskManager");
            // 获取 gDefault 这个字段, 想办法替换它
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("IActivityTaskManagerSingleton");
            gDefaultField.setAccessible(true);

            Object IActivityManagerSingleton = gDefaultField.get(null);
            //2.获取mInstance
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            if (Build.VERSION.SDK_INT >= 29) {
                //Q上需要动态执行create方法
                Method getMethod = singletonClass.getMethod("get");
                getMethod.setAccessible(true);
                getMethod.invoke(IActivityManagerSingleton);

            }
            Object mInstance = mInstanceField.get(IActivityManagerSingleton);
            //3.动态代理设置自己的mInstance
            Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    mInstance.getClass().getInterfaces(),
                    new AmsHookHandler(mInstance));
            mInstanceField.set(IActivityManagerSingleton, proxyInstance);
        } catch (Exception e) {
            Log.e(TAG, "Hook Failed", e);
        }

    }

}

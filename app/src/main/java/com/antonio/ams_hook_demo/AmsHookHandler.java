package com.antonio.ams_hook_demo;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * AMS动态代理类
 *
 * @author antonio
 * @version 1.0
 */
public class AmsHookHandler implements InvocationHandler {
    private static final String TAG = "AmsHookHandler";

    private Object mBase;

    public AmsHookHandler(Object base) {
        mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d(TAG, "method:" + method.getName() + " invoke with args:" + Arrays.toString(args));
        return method.invoke(mBase, args);
    }
}

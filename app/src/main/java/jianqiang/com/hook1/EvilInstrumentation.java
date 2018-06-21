package jianqiang.com.hook1;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Method;

public class EvilInstrumentation extends Instrumentation {

    private static final String TAG = "EvilInstrumentation";

    // ActivityThread中原始的对象, 保存起来
    Instrumentation mBase;

    public EvilInstrumentation(Instrumentation base) {
        mBase = base;
    }

    public Activity newActivity(ClassLoader cl, String className,
                                Intent intent)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {

        Log.d(TAG, "包建强到此一游!");

        return mBase.newActivity(cl, className, intent);
    }

    public void callActivityOnCreate(Activity activity, Bundle bundle) {

        Log.d(TAG, "到此一游!");

        // 开始调用原始的方法, 调不调用随你,但是不调用的话, 所有的startActivity都失效了.
        // 由于这个方法是隐藏的,因此需要使用反射调用;首先找到这个方法
        Class[] p1 = {Activity.class, Bundle.class};
        Object[] v1 = {activity, bundle};
        RefInvoke.invokeInstanceMethod(
                mBase, "callActivityOnCreate", p1, v1);
    }
}


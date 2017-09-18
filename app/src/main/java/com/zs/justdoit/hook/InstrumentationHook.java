package com.zs.justdoit.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by shao on 2017/7/29.
 */

public class InstrumentationHook extends Instrumentation {

    Instrumentation mInstrumentation;
    public static void hook(){
        try {
            Class<?> clazz=Class.forName("android.app.ActivityThread");
            Method method=clazz.getDeclaredMethod("currentActivityThread");
            method.setAccessible(true);
            Object activityThread=method.invoke(null);

            Field field=clazz.getDeclaredField("mInstrumentation");
            field.setAccessible(true);
            Instrumentation instrumentation=(Instrumentation)field.get(activityThread);
            InstrumentationHook hook=new  InstrumentationHook(instrumentation);
            field.set(activityThread,hook);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    private InstrumentationHook(Instrumentation instrumentation){
        mInstrumentation=instrumentation;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        System.out.print("\n执行了startActivity, 参数如下: \n" + "who = [" + who + "], " +
                "\ncontextThread = [" + contextThread + "], \ntoken = [" + token + "], " +
                "\ntarget = [" + target + "], \nintent = [" + intent +
                "], \nrequestCode = [" + requestCode + "], \noptions = [" + options + "]");
        try {
            Method method = mInstrumentation.getClass().getMethod("execStartActivity",new Class[]{Context.class,
                    IBinder.class,IBinder.class,Activity.class,Intent.class,int.class,Bundle.class});
            method.setAccessible(true);
            return (ActivityResult)method.invoke(mInstrumentation, who, contextThread, token,
                    target, intent, requestCode, options);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}

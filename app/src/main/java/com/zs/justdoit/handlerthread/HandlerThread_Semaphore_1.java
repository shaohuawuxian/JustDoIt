package com.zs.justdoit.handlerthread;

import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Semaphore;

/**
 * Created by zhangshao on 2016/11/9.
 * use semaphore reload HandlerThread
 */

public class HandlerThread_Semaphore_1 extends Thread{

    Looper mLooper;
    Semaphore mSemaphore;
    public HandlerThread_Semaphore_1(String name){
        super(name);
        mSemaphore=new Semaphore(1);

    }

    @Override
    public void run() {
        Log.d(getName(),"run start");
        if(isInterrupted()){
            return;
        }
        Looper.prepare();
        if(mLooper==null){
            mLooper=Looper.myLooper();
        }
        Log.d(getName(),"mLooper init over");
        onLooperPrepared();
        Looper.loop();
    }

    public Looper getLooper(){
        if(!isAlive()){
            return null;
        }
        while (isAlive()&&mLooper==null){//Semaphore为1时使用，但要注释掉run方法的release
            try {
                mSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                mSemaphore.release();
            }
        }

        return mLooper;
    }

    /**
     * Call back method that can be explicitly overridden if needed to execute some
     * setup before Looper loops.
     */
    protected void onLooperPrepared() {
    }
    public boolean quit() {
        Looper looper = getLooper();
        if (looper != null) {
            looper.quit();
            return true;
        }
        return false;
    }
}

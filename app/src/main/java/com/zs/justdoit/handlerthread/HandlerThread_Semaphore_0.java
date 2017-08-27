package com.zs.justdoit.handlerthread;

import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Semaphore;

/**
 * Created by zhangshao on 2016/11/9.
 * use semaphore reload HandlerThread
 */

public class HandlerThread_Semaphore_0 extends Thread{

    Looper mLooper;
    Semaphore mSemaphore;
    public HandlerThread_Semaphore_0(String name){
        super(name);
        mSemaphore=new Semaphore(0);

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
        mSemaphore.release();//semaphore为0时使用。
        onLooperPrepared();
        Looper.loop();
    }

    public Looper getLooper(){
        if(!isAlive()){
            return null;
        }
        if(isAlive()&&mLooper==null){//  Semaphore为0，run方法中release后+1；
            try {
                Log.d(getName(),"mSemaphore acquire");
                mSemaphore.acquire();
                Log.d(getName(),"mSemaphore acquire ok");
            } catch (InterruptedException e) {
                e.printStackTrace();
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

package com.zs.justdoit.handlerthread;

import android.content.Intent;
import android.util.Log;

import com.zs.justdoit.ipc.RemoteConnection;

/**
 * Created by zhangshao on 2016/11/10.
 */

public class TestNewIntentService extends NewIntentService{
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public TestNewIntentService() {
        super("testNewIntentService");
    }


    //@Override
    protected void onHandleIntent(Intent intent) {
        RemoteConnection.getInstance().getList();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //Toast.makeText(this,"onHandleIntent 执行完毕",Toast.LENGTH_SHORT).show();
        Log.w("service","onHandleIntent 执行完毕  intent="+intent);
    }
}

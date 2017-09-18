package com.zs.justdoit.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;

import java.util.List;

/**
 * Created by zhangshao on 2017/8/25.
 */

public class RemoteConnection implements ServiceConnection{

    static RemoteConnection remoteConnection=new RemoteConnection();
    UndercoverInterface undercoverInterface;
    Context mContext;
    private RemoteConnection(){}
    public static RemoteConnection getInstance(){
        return remoteConnection;
    }
    public void bindService(Context context){
        mContext=context;
        Intent intent=new Intent(context,RemoteService.class);
        context.bindService(intent,this,Context.BIND_AUTO_CREATE);
    }
    UndercoverAddLinstener.Stub linstener=new UndercoverAddLinstener.Stub() {
        @Override
        public void hasAdd() throws RemoteException {
            getList();
        }
    };
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        System.out.println("绑定成功");
        undercoverInterface=UndercoverInterface.Stub.asInterface(service);
        try {
            undercoverInterface.setUndercoverAddListener(linstener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            service.linkToDeath(deathRecipient, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    DeathRecipient deathRecipient=new DeathRecipient(){

        @Override
        public void binderDied() {
            if(mContext!=null){
                bindService(mContext);
            }
        }
    };

    @Override
    public void onServiceDisconnected(ComponentName name) {
        System.out.println("断开连接");
        undercoverInterface=null;
    }

    public void getList(){
        if(undercoverInterface!=null){
            List<String> list= null;
            try {
                //获取数据在binder线程池执行，会挂起主线程，会造成主线程卡顿或ANR
                list = undercoverInterface.getUndercoverNames();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            System.out.println(list);
        }
    }

    public void add(String name){
        if(undercoverInterface!=null){
            try {
                undercoverInterface.addUndercover(name);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void unBindService(){
        if(undercoverInterface!=null){
            try {
                undercoverInterface.unregistUndercoverAddListener(linstener);
                undercoverInterface.asBinder().unlinkToDeath(deathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mContext.unbindService(this);
            mContext=null;
        }
    }
}

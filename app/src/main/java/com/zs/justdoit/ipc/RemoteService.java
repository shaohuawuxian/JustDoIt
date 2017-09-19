package com.zs.justdoit.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshao on 2017/8/25.
 */

public class RemoteService extends Service {

    static List<String> mList=new ArrayList<>();
    static {
        mList.add("yongren chen");
        mList.add("black");
    }

    RemoteCallbackList<UndercoverAddLinstener> mRemoteCallbackList=new RemoteCallbackList();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("远程服务收到绑定信息");
        return undercoverInterface;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("远程服务解除绑定");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("远程Service onDestroy");
        System.exit(0);//关闭当前进程
    }

    private UndercoverInterface.Stub undercoverInterface=new UndercoverInterface.Stub(){
        @Override
        public List<String> getUndercoverNames() throws RemoteException {
            try {
                Thread.sleep(10000);//查询比较慢，等待3秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mList;
        }
        @Override
        public void addUndercover(String name) throws RemoteException {
            mList.add(name);
            int n=mRemoteCallbackList.beginBroadcast();
            while(n>0){
                UndercoverAddLinstener undercoverAddLinstener=mRemoteCallbackList.getBroadcastItem(n);
                undercoverAddLinstener.hasAdd();
                n--;
            }
            mRemoteCallbackList.finishBroadcast();
        }
        @Override
        public void removeUndercover(String name) throws RemoteException {
            boolean removed=mList.remove(name);
        }

        @Override
        public void setUndercoverAddListener(UndercoverAddLinstener listener) throws RemoteException {
            mRemoteCallbackList.register(listener);
        }

        @Override
        public void unregistUndercoverAddListener(UndercoverAddLinstener listener) throws RemoteException {
            mRemoteCallbackList.unregister(listener);
        }
    };
}

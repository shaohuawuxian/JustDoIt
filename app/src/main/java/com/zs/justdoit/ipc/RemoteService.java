package com.zs.justdoit.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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

    UndercoverAddLinstener mUndercoverAddLinstener;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("收到绑定信息");
        return undercoverInterface;
    }

    private UndercoverInterface.Stub undercoverInterface=new UndercoverInterface.Stub(){
        @Override
        public List<String> getUndercoverNames() throws RemoteException {
            try {
                Thread.sleep(2000);//查询比较慢，等待3秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mList;
        }
        @Override
        public void addUndercover(String name) throws RemoteException {
            mList.add(name);
            if(mUndercoverAddLinstener!=null){
                mUndercoverAddLinstener.hasAdd();
            }
        }
        @Override
        public void removeUndercover(String name) throws RemoteException {
            boolean removed=mList.remove(name);
        }

        @Override
        public void setUndercoverAddListener(UndercoverAddLinstener listener) throws RemoteException {
            mUndercoverAddLinstener=listener;
        }

        @Override
        public void unregistUndercoverAddListener(UndercoverAddLinstener listener) throws RemoteException {
            mUndercoverAddLinstener=null;
        }
    };
}

// UndercoverInterface.aidl
package com.zs.justdoit.ipc;

// Declare any non-default types here with import statements
import com.zs.justdoit.ipc.UndercoverAddLinstener;
interface UndercoverInterface {

    List<String> getUndercoverNames();

    void addUndercover(String name);

    void removeUndercover(String name);

    void setUndercoverAddListener(UndercoverAddLinstener listener);

    void unregistUndercoverAddListener(UndercoverAddLinstener listener);
}

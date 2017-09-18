package com.zs.justdoit;

import android.app.Application;

import com.zs.justdoit.hook.InstrumentationHook;

/**
 * Created by shao on 2017/9/18.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        InstrumentationHook.hook();
    }
}

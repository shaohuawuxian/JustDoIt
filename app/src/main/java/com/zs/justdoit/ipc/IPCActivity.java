package com.zs.justdoit.ipc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.zs.justdoit.R;

/**
 * Created by shao on 2017/9/4.
 */

public class IPCActivity extends AppCompatActivity{

    int id=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);
        findViewById(R.id.link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteConnection.getInstance().bindService(getApplicationContext());
            }
        });

        findViewById(R.id.getlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteConnection.getInstance().getList();
            }
        });

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteConnection.getInstance().add("张学友"+id++);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RemoteConnection.getInstance().unBindService();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==4){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

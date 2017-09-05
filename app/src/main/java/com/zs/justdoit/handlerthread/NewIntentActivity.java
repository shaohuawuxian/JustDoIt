package com.zs.justdoit.handlerthread;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.zs.justdoit.R;
import com.zs.justdoit.ipc.RemoteConnection;

/**
 * Created by shao on 2017/9/4.
 */

public class NewIntentActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intentservice);
        findViewById(R.id.textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewIntentActivity.this,TestNewIntentService.class);
                startService(intent);
                ((TextView)findViewById(R.id.textview)).setText("service已启动");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        RemoteConnection.getInstance().bindService(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RemoteConnection.getInstance().unBindService();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==4){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

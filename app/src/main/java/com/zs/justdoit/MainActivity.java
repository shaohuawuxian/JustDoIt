package com.zs.justdoit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zs.justdoit.handlerthread.TestNewIntentService;
import com.zs.justdoit.ipc.RemoteConnection;

public class MainActivity extends AppCompatActivity {

    int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TestNewIntentService.class);
                startService(intent);
                ((TextView)findViewById(R.id.textview)).setText("service已启动");
            }
        });

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
}

package com.zs.justdoit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zs.justdoit.handlerthread.NewIntentActivity;
import com.zs.justdoit.handlerthread.TestNewIntentService;
import com.zs.justdoit.hook.InstrumentationHook;
import com.zs.justdoit.hook.InstrumentationHookActivity;
import com.zs.justdoit.ipc.IPCActivity;
import com.zs.justdoit.ipc.RemoteConnection;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int id=0;

    private static List<Class> activityList=new ArrayList<>();
    static {
        activityList.clear();
        activityList.add(NewIntentActivity.class);
        activityList.add(IPCActivity.class);
        activityList.add(InstrumentationHookActivity.class);
}

    RecyclerView mRecyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView=(RecyclerView)findViewById(R.id.list);
        linearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new ActivityAdapter(this,activityList));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    private static class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder>{

        List<Class> activityList=new ArrayList();
        Context mContext;
        public ActivityAdapter(Context context,List<Class> list){
            mContext=context;
            activityList.addAll(list);
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(mContext).inflate(R.layout.item_main_list,null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.nameView.setText(activityList.get(position).getSimpleName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,activityList.get(position));
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return activityList.size();
        }

        protected static class ViewHolder extends RecyclerView.ViewHolder{
            TextView nameView=null;
            public ViewHolder(View itemView) {
                super(itemView);
                nameView=(TextView) itemView.findViewById(R.id.activityname);
            }
        }
    }
}

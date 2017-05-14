package com.unicellular.simpletask.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unicellular.simpletask.R;
import com.unicellular.simpletask.adapter.AllTaskRecycleAdapter;
import com.unicellular.simpletask.app.SimpleTaskApplication;
import com.unicellular.simpletask.bean.Task;
import com.unicellular.simpletask.utils.SpacesItemDecoration;

import java.util.List;

/**
 * Created by szc on 2017/3/23.
 *
 */

public class HistoryFragment extends Fragment{
    public static final int HISTORY_FRAGMENT=0x04;
    public static final String UPDATE_VIEW="HistoryFragment UpdateView";

    private View view;
    private RecyclerView recyclerView;
    private AllTaskRecycleAdapter adapter;
    private ProgressDialog dialog;

    private List<Task> tasks;

    private UpdateViewBroadcast broadcast;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            adapter=new AllTaskRecycleAdapter(getContext(),tasks);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_history,container,false);

        broadcast=new UpdateViewBroadcast();
        getActivity().registerReceiver(broadcast,new IntentFilter(UPDATE_VIEW));

        initData();
        initView();
        return view;
    }

    void initData(){
        dialog=new ProgressDialog(getContext());
        dialog.setMessage("正在加载");
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                tasks= SimpleTaskApplication.taskDB.readHistoryData();
                if (tasks!=null){
                    handler.sendEmptyMessage(1);
                }
            }
        }).start();
    }

    void initView(){
        recyclerView= (RecyclerView) view.findViewById(R.id.list_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.addItemDecoration(new DividerItemDecoration(
         //       getActivity(), DividerItemDecoration.VERTICAL));
        //recyclerView.addItemDecoration(new SpacesItemDecoration(18));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(broadcast!=null){
            getActivity().unregisterReceiver(broadcast);
        }
    }

    class UpdateViewBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case UPDATE_VIEW:
                    initData();
                    initView();
                    break;
            }
        }
    }

}

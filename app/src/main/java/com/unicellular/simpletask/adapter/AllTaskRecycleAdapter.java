package com.unicellular.simpletask.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unicellular.simpletask.R;
import com.unicellular.simpletask.bean.Task;
import com.unicellular.simpletask.utils.DialogUtils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by szc on 2017/3/23.
 *
 */

public class AllTaskRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Task> tasks;
    private SimpleDateFormat sdf;
    public AllTaskRecycleAdapter(Context context,List<Task> tasks){
        this.context=context;
        this.tasks=tasks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder=new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_all_task_recycler,parent,false));
        sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder= (ViewHolder) holder;
        final Task task=tasks.get(position);
        viewHolder.tvTaskName.setText(task.getName());
        viewHolder.tvTaskTime.setText(sdf.format(task.getTime()));
        viewHolder.tvContext.setText(task.getContext());
        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogUtils.reviewDialog(context,"删除","确定删除这条任务",task.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (tasks!=null){
            return tasks.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTaskName;
        TextView tvTaskTime;
        TextView tvContext;
        ImageView ivDelete;


        public ViewHolder(View itemView) {
            super(itemView);
            tvTaskName= (TextView) itemView.findViewById(R.id.tv_task_name);
            tvTaskTime= (TextView) itemView.findViewById(R.id.tv_task_time);
            tvContext= (TextView) itemView.findViewById(R.id.tv_context);
            ivDelete= (ImageView) itemView.findViewById(R.id.iv_delete);
        }
    }
}

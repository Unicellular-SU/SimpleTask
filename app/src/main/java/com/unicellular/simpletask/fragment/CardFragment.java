package com.unicellular.simpletask.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.unicellular.simpletask.R;
import com.unicellular.simpletask.bean.Task;
import com.unicellular.simpletask.utils.DialogUtils;

import java.text.SimpleDateFormat;

/**
 * Created by szc on 2017/3/20.
 *
 */

public class CardFragment extends Fragment{

    private int color[]={R.color.card_color_1,R.color.card_color_2
            ,R.color.card_color_3,R.color.card_color_4
            ,R.color.card_color_5,R.color.card_color_6
            ,R.color.card_color_7,R.color.card_color_8};

    private TextView tvTaskName;
    private TextView tvTaskTime;
    private TextView tvTaskContext;
    private ImageView ivDelete;
    public CardView cardView;

    private Task task;
    private SimpleDateFormat sdf;

    public CardFragment() {
    }

    public static CardFragment newInstance(Task task){

        CardFragment cardFragment=new CardFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("task",task);
        cardFragment.setArguments(bundle);
        return cardFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.item_card_fragment,container,false);
        sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        cardView= (CardView) view.findViewById(R.id.card_view);
        int temp= (int) (Math.random()*8);
        cardView.setCardBackgroundColor(getResources().getColor(color[temp]));

        tvTaskName= (TextView) view.findViewById(R.id.tv_task_name);
        tvTaskTime= (TextView) view.findViewById(R.id.tv_task_time);
        tvTaskContext= (TextView) view.findViewById(R.id.tv_context);
        ivDelete= (ImageView) view.findViewById(R.id.iv_delete);
        tvTaskContext.setMovementMethod(ScrollingMovementMethod.getInstance());

        task= (Task) getArguments().getSerializable("task");
        if (task!=null){
            tvTaskName.setText(task.getName());
            tvTaskTime.setText(sdf.format(task.getTime()));
            tvTaskContext.setText(task.getContext());

/*            if (task.getStatus()==1){
                cardView.setCardBackgroundColor(getResources().getColor(color[1]));
            }else {
                cardView.setCardBackgroundColor(getResources().getColor(color[7]));
            }*/
        }

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.reviewDialog(getActivity(),"删除","确定删除这项任务吗",task.getId());
            }
        });

        return view;
    }
}

package com.unicellular.simpletask.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by szc on 2017/5/9.
 *
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration{
    private int space;
    public SpacesItemDecoration(int space) {
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=space;
        outRect.right=space;
        outRect.top=space;
        if (parent.getChildPosition(view) == 0)
            outRect.top = space;

    }
}

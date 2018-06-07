package com.pans.xplan.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pans.xplan.util.MethodUtil;

/**
 * 自定义Item间隔
 */
public class ItemSpaceDecoration extends RecyclerView.ItemDecoration {

    private int startAndEnd, top, bottom,extraTop;

    public ItemSpaceDecoration(int startAndEnd, int top, int bottom,int extraTop) {
        this.startAndEnd = MethodUtil.dp2px(startAndEnd);
        this.top = MethodUtil.dp2px(top);
        this.bottom = MethodUtil.dp2px(bottom);
        this.extraTop = MethodUtil.dp2px(extraTop);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        View first = parent.getChildAt(0);
        outRect.top = first != null && first.equals(view)?top + extraTop:top;
        outRect.bottom = bottom;
        outRect.left = startAndEnd;
        outRect.right = startAndEnd;
    }

}

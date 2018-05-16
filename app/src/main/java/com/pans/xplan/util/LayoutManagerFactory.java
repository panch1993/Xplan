package com.pans.xplan.util;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author android01
 * @date 2018/5/16.
 * @time 下午2:45.
 */
public class LayoutManagerFactory {
    private LayoutManagerFactory() {
    }

    public static RecyclerView.LayoutManager createLinearLayoutManager(Context context,boolean isVertical) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(isVertical?LinearLayoutManager.VERTICAL:LinearLayoutManager.HORIZONTAL);
        return layoutManager;
    }

    public static RecyclerView.LayoutManager createGridLayoutManager(Context context,boolean isVertical,int spanCount) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, spanCount);
        layoutManager.setOrientation(isVertical?GridLayoutManager.VERTICAL:GridLayoutManager.HORIZONTAL);
        return layoutManager;
    }

}

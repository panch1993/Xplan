package com.pans.xplan.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pans.xplan.R;
import com.pans.xplan.util.MethodUtil;
import com.pans.xplan.util.ResourceUtil;

/**
 * @author android01
 * @date 2018/5/20.
 * @time 下午7:12.
 */
public class MenuPopupwindow extends PopupWindow {
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public MenuPopupwindow(Context context, final String[] strings) {
        super(context);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ResourceUtil.getDrawable(R.drawable.divider_primary_gray));
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < strings.length; i++) {
            if (TextUtils.isEmpty(strings[i]))continue;
            TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_pop_menu,null,false);
            tv.setText(strings[i]);
            tv.setLayoutParams(new LinearLayout.LayoutParams(MethodUtil.dp2px(100),MethodUtil.dp2px(30)));
            tv.setTag(i);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        int index = (int) v.getTag();
                        mOnItemClickListener.onItemClick(v, index,strings[index]);

                    }
                }
            });
            linearLayout.addView(tv);
        }
        setContentView(linearLayout);

        setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(MethodUtil.dp2px(6F));
        } else {
            setBackgroundDrawable(ResourceUtil.getDrawable(R.drawable.bg_alpha));
        }
        setOutsideTouchable(true);
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String str);
    }

}

package com.pans.xplan.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by panchenhuan on 17/1/3..
 */
class NoScrollViewpager : ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onTouchEvent(arg0: MotionEvent): Boolean = false

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean = false
}
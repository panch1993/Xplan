package com.pans.xplan.util

import android.annotation.SuppressLint
import android.widget.Toast
import com.pans.xplan.basic.AppContext

/**
 * @author android01
 * @date 2018/5/14.
 * @time 下午3:37.
 */
class ToastUtil {
    companion object {
        var toast: Toast? = null
        @SuppressLint("ShowToast")
        fun showToast(text: String) {
            if (toast == null) {
                toast = Toast.makeText(AppContext.instance, text, Toast.LENGTH_SHORT)
            } else {
                toast?.setText(text)
            }
            toast?.show()
        }
        @SuppressLint("ShowToast")
        fun showToast(text: Int) {
            if (toast == null) {
                toast = Toast.makeText(AppContext.instance, text, Toast.LENGTH_SHORT)
            } else {
                toast?.setText(text)
            }
            toast?.show()
        }
    }
}
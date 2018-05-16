package com.pans.xplan.basic

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.pans.xplan.R
import com.pans.xplan.interfaces.GetTitleImpl
import com.pans.xplan.util.ActManager
import com.umeng.analytics.MobclickAgent

/**
 * @author android01
 * @date 2018/5/14.
 * @time 上午10:59.
 */
 abstract class BaseActivity : AppCompatActivity(),GetTitleImpl {

    protected lateinit var context: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        preSetConentView(savedInstanceState)
        setContentView(getLayoutResId())
        ActManager.get().addActivity(context)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        if (showActionBar()) {
            val toolbar = findViewById<Toolbar>(R.id.toolbar) ?: throw NullPointerException("can't find toolbar by R.id.toolbar")
            setSupportActionBar(toolbar)
            if (showBackup()) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
            supportActionBar?.setDisplayShowTitleEnabled(false)
            findViewById<TextView>(R.id.tv_title).text = getTitleString()
        }
        initData()
        initView()
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActManager.get().removeActivity(context)
    }

    open fun preSetConentView(savedInstanceState: Bundle?) {}

    /**
     * 显示返回键
     */
    open fun showBackup():Boolean = true

    /**
     * 显示actionbar
     */
    open fun showActionBar():Boolean = false

    /**
     * 获取actionbar标题
     */
    override fun getTitleString():String = ""

    /**
     * 获取layoutResource
     */
    abstract fun getLayoutResId(): Int

    abstract fun initData()

    abstract fun initView()
}
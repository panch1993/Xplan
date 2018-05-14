package com.pans.xplan.basic

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pans.xplan.util.ActManager
import com.umeng.analytics.MobclickAgent

/**
 * @author android01
 * @date 2018/5/14.
 * @time 上午10:59.
 */
 abstract class BaseActivity : AppCompatActivity() {

    protected var context: BaseActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preSetConentView(savedInstanceState)
        setContentView(getLayoutResId())
        context = this
        ActManager.get().addActivity(context)
        if (showActionBar()) {
            if (showBackup()) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        } else {
            supportActionBar?.hide()
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
    open fun showActionBar():Boolean = true


    /**
     * 获取layoutResource
     */
    abstract fun getLayoutResId(): Int

    abstract fun initData()

    abstract fun initView()
}
package com.pans.xplan.basic

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.pans.xplan.R
import com.pans.xplan.interfaces.GetTitleImpl
import com.pans.xplan.util.ActManager
import com.umeng.analytics.MobclickAgent
import io.realm.Realm
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * @author android01
 * @date 2018/5/14.
 * @time 上午10:59.
 */
 abstract class BaseActivity : AppCompatActivity(),GetTitleImpl {

    protected lateinit var context: BaseActivity
    var realm: Realm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        if (getRealmInstance()) realm = Realm.getDefaultInstance()
        beforeSetContentView(savedInstanceState)
        setContentView(getLayoutResId())
        ActManager.get().addActivity(context)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        if (showActionBar()) {
            val toolbar = findViewById<Toolbar>(R.id.toolbar) ?: throw NullPointerException("can't find toolbar by R.id.toolbar")
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            if (showBackup()) supportActionBar?.setDisplayHomeAsUpEnabled(true)
            tv_title.text = getTitleString()
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
        realm?.close()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (showBackup() && item?.itemId == android.R.id.home) {
            context.finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    
    open fun beforeSetContentView(savedInstanceState: Bundle?) {}

    open fun getRealmInstance(): Boolean = false
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
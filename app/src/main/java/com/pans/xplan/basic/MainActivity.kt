package com.pans.xplan.basic

import android.os.Bundle
import com.pans.xplan.R
import com.pans.xplan.util.ActManager
import com.pans.xplan.util.KLog
import com.pans.xplan.util.MethodUtil

import com.pans.xplan.util.ToastUtil
import com.umeng.analytics.MobclickAgent

class MainActivity : BaseActivity() {

    private var clickBackTimeStamp: Long = 0L

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun showActionBar(): Boolean  = false

    override fun preSetConentView(savedInstanceState: Bundle?) {
        MobclickAgent.openActivityDurationTrack(false)
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL)
    }
    override fun initData() {
        KLog.d(MethodUtil.getDeviceInfo(this))
    }

    override fun initView() {

    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - clickBackTimeStamp > 2000) {//2秒内双击执行退出
            clickBackTimeStamp = System.currentTimeMillis()
            ToastUtil.showToast(R.string.exit_tip)
            return
        }
        ActManager.get().finishAll()
        System.exit(0)
    }
}

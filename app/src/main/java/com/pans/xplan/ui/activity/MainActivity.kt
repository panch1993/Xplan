package com.pans.xplan.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.WindowManager
import com.pans.xplan.R
import com.pans.xplan.basic.BaseActivity
import com.pans.xplan.data.Const
import com.pans.xplan.data.realm.entity.PLAN
import com.pans.xplan.ui.adapter.MainPagerAdapter
import com.pans.xplan.ui.fragment.PlanListFragment
import com.pans.xplan.util.ActManager
import com.pans.xplan.util.ResourceUtil
import com.pans.xplan.util.ToastUtil
import com.umeng.analytics.MobclickAgent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var clickBackTimeStamp: Long = 0L

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun showActionBar(): Boolean = false

    override fun preSetConentView(savedInstanceState: Bundle?) {
        MobclickAgent.openActivityDurationTrack(false)
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    override fun initData() {

    }

    override fun initView() {
        setSupportActionBar(toolbar)

        fab.setOnClickListener { startActivity(Intent(context, CreatePlanActivity::class.java)) }

        tb_main.setupWithViewPager(vp_main)


        vp_main.adapter = MainPagerAdapter(supportFragmentManager, initFragmentList())

    }

    fun initFragmentList(): List<Fragment> {
        val day = PlanListFragment()
        val dayBundle = Bundle()
        dayBundle.putString(Const.FRAGMENT_TITLE, ResourceUtil.getString(R.string.day))
        dayBundle.putString(Const.FIELD_PLAN_TYPE, PLAN.PLAN_DAY)
        day.arguments = dayBundle

        val week = PlanListFragment()
        val weekBundle = Bundle()
        weekBundle.putString(Const.FRAGMENT_TITLE, ResourceUtil.getString(R.string.week))
        weekBundle.putString(Const.FIELD_PLAN_TYPE, PLAN.PLAN_WEEK)
        week.arguments = weekBundle

        val month = PlanListFragment()
        val monthBundle = Bundle()
        monthBundle.putString(Const.FRAGMENT_TITLE, ResourceUtil.getString(R.string.month))
        monthBundle.putString(Const.FIELD_PLAN_TYPE, PLAN.PLAN_MONTH)
        month.arguments = monthBundle

        val year = PlanListFragment()
        val yearBundle = Bundle()
        yearBundle.putString(Const.FRAGMENT_TITLE, ResourceUtil.getString(R.string.year))
        yearBundle.putString(Const.FIELD_PLAN_TYPE, PLAN.PLAN_YEAR)
        year.arguments = yearBundle

        return listOf(day, week, month, year)
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

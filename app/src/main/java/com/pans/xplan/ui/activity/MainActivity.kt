package com.pans.xplan.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.pans.xplan.R
import com.pans.xplan.basic.BaseActivity
import com.pans.xplan.data.Const
import com.pans.xplan.ui.adapter.MainPagerAdapter
import com.pans.xplan.ui.fragment.PlanListFragment
import com.pans.xplan.util.ActManager
import com.pans.xplan.util.ResourceUtil
import com.pans.xplan.util.SpUtil
import com.pans.xplan.util.ToastUtil
import com.pans.xplan.util.bus.EventMessage
import com.pans.xplan.util.bus.RxBus
import com.pans.xplan.util.bus.RxSubscriptions
import com.umeng.analytics.MobclickAgent
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_all_plan.*
import kotlinx.android.synthetic.main.tab_setting.*

class MainActivity : BaseActivity() {
    private var disposable: Disposable? = null
    private var clickBackTimeStamp: Long = 0L

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun showActionBar(): Boolean = false

    override fun beforeSetContentView(savedInstanceState: Bundle?) {
        MobclickAgent.openActivityDurationTrack(false)
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL)
    }

    override fun initData() {
        RxSubscriptions.remove(disposable)
        disposable = RxBus.get().change(EventMessage::class.java)
                .subscribe {
                    when (it.type) {
                        EventMessage.TYPE.SHOW_DAY -> {
                            tv_card_title.text = getString(R.string.today_plan)
                        }
                        EventMessage.TYPE.SHOW_WEEK -> {
                            tv_card_title.text = getString(R.string.week_plan)
                        }
                        EventMessage.TYPE.SHOW_MONTH -> {
                            tv_card_title.text = getString(R.string.month_plan)
                        }
                        EventMessage.TYPE.SHOW_YEAR -> {
                            tv_card_title.text = getString(R.string.year_plan)
                        }
                    }
                }
        RxSubscriptions.add(disposable)
    }

    override fun initView() {
        //init plan tab
        setSupportActionBar(toolbar)

        fab.setOnClickListener { startActivity(Intent(context, CreatePlanActivity::class.java)) }

        tb_main.setupWithViewPager(vp_main)


        vp_main.adapter = MainPagerAdapter(supportFragmentManager, initFragmentList())

        aiv_plan.setOnClickListener {
            aiv_plan.isSelected = true
            aiv_setting.isSelected = false
            tab_setting.visibility = View.GONE
            tab_plan.visibility = View.VISIBLE
        }
        aiv_setting.setOnClickListener {
            aiv_plan.isSelected = false
            aiv_setting.isSelected = true
            tab_plan.visibility = View.GONE
            tab_setting.visibility = View.VISIBLE
        }
        aiv_plan.isSelected = true
        //init setting
        sc_finger.isChecked = SpUtil.get(Const.FINGER_ENABLE, false)

        sc_finger.setOnCheckedChangeListener { buttonView, isChecked -> SpUtil.put(Const.FINGER_ENABLE, isChecked) }

    }

    private fun initFragmentList(): List<Fragment> {
        val day = PlanListFragment()
        val dayBundle = Bundle()
        dayBundle.putString(Const.FRAGMENT_TITLE, ResourceUtil.getString(R.string.day))
        dayBundle.putString(Const.FIELD_PLAN_TYPE, Const.TYPE_PLAN_DAY)
        day.arguments = dayBundle

        val week = PlanListFragment()
        val weekBundle = Bundle()
        weekBundle.putString(Const.FRAGMENT_TITLE, ResourceUtil.getString(R.string.week))
        weekBundle.putString(Const.FIELD_PLAN_TYPE, Const.TYPE_PLAN_WEEK)
        week.arguments = weekBundle

        val month = PlanListFragment()
        val monthBundle = Bundle()
        monthBundle.putString(Const.FRAGMENT_TITLE, ResourceUtil.getString(R.string.month))
        monthBundle.putString(Const.FIELD_PLAN_TYPE, Const.TYPE_PLAN_MONTH)
        month.arguments = monthBundle

        val year = PlanListFragment()
        val yearBundle = Bundle()
        yearBundle.putString(Const.FRAGMENT_TITLE, ResourceUtil.getString(R.string.year))
        yearBundle.putString(Const.FIELD_PLAN_TYPE, Const.TYPE_PLAN_YEAR)
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

    override fun onDestroy() {
        super.onDestroy()
        RxSubscriptions.clear()
    }
}

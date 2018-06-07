package com.pans.xplan.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import com.pans.xplan.BuildConfig
import com.pans.xplan.R
import com.pans.xplan.basic.BaseActivity
import com.pans.xplan.data.Const
import com.pans.xplan.data.realm.entity.PLAN
import com.pans.xplan.ui.adapter.CardPlanListAdapter
import com.pans.xplan.ui.adapter.MainPagerAdapter
import com.pans.xplan.ui.fragment.PlanListFragment
import com.pans.xplan.util.*
import com.pans.xplan.util.bus.EventMessage
import com.pans.xplan.util.bus.RxBus
import com.pans.xplan.util.bus.RxSubscriptions
import com.pans.xplan.widget.ItemSpaceDecoration
import com.umeng.analytics.MobclickAgent
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_all_plan.*
import kotlinx.android.synthetic.main.tab_setting.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : BaseActivity() {
    private var disposable: Disposable? = null
    private var clickBackTimeStamp: Long = 0L
    private lateinit var cardPlanListAdapter: CardPlanListAdapter
    private val dateFormat: SimpleDateFormat = SimpleDateFormat(Const.Y_M_D_POINT_FORMAT, Locale.getDefault())

    private lateinit var currentPage: EventMessage.TYPE

    private var cardTitleDay: String? = null
    private var cardTitleWeek: String? = null
    private var cardTitleMonth: String? = null
    private var cardTitleYear: String? = null

    private var startTimeOfDay: Long = 0
    private var startTimeOfWeek: Long = 0
    private var startTimeOfMonth: Long = 0
    private var startTimeOfYear: Long = 0

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun showActionBar(): Boolean = false

    override fun getRealmInstance(): Boolean = true

    override fun beforeSetContentView(savedInstanceState: Bundle?) {
        MobclickAgent.openActivityDurationTrack(false)
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL)
    }

    override fun initData() {
        RxSubscriptions.remove(disposable)
        initCardTitle()
        disposable = RxBus.get().change(EventMessage::class.java)
                .subscribe {
                    currentPage = it.type
                    when (it.type) {
                        EventMessage.TYPE.SHOW_DAY -> {
                            tv_card_title.text = StringUtil.getSpannable(cardTitleDay, "   ",
                                    DateUtil.getDayOfWeek(startTimeOfDay,false),ResourceUtil.getDimens(R.dimen.text_big_title),ResourceUtil.getDimens(R.dimen.text_small),Color.WHITE,Color.WHITE)
                            updatePlanCard(Const.TYPE_PLAN_DAY, startTimeOfDay)
                        }
                        EventMessage.TYPE.SHOW_WEEK -> {
                            tv_card_title.text = StringUtil.getSpannable(cardTitleWeek!!.split("-")[0], " ~ ",
                                    cardTitleWeek!!.split("-")[1],ResourceUtil.getDimens(R.dimen.text_big_title),ResourceUtil.getDimens(R.dimen.text_small),Color.WHITE,Color.WHITE)
                            updatePlanCard(Const.TYPE_PLAN_WEEK, startTimeOfWeek)
                        }
                        EventMessage.TYPE.SHOW_MONTH -> {
                            tv_card_title.text = cardTitleMonth
                            updatePlanCard(Const.TYPE_PLAN_MONTH, startTimeOfMonth)
                        }
                        EventMessage.TYPE.SHOW_YEAR -> {
                            tv_card_title.text = cardTitleYear
                            updatePlanCard(Const.TYPE_PLAN_YEAR, startTimeOfYear)
                        }
                    }
                }
        RxSubscriptions.add(disposable)
    }

    private fun updatePlanCard(planType: String, startTime: Long) {
        val list = realmController!!.getEnablePlanList(realm!!, planType)
        realm!!.beginTransaction()
        for (plaN_LIST in list) {
            val exist = realmController!!.checkPlanExist(realm!!, plaN_LIST, startTime)
            if (!exist) {
                if (plaN_LIST.repeat_type == Const.TYPE_REPEAT_SPECIAL) {
                    //自定义日期过滤
                    val enableFlag: Byte = 1
                    val open: Boolean = when (DateUtil.getDayOfWeekIndex(startTime)) {
                        Calendar.MONDAY -> plaN_LIST.repeat_days[0] == enableFlag
                        Calendar.TUESDAY -> plaN_LIST.repeat_days[1] == enableFlag
                        Calendar.WEDNESDAY -> plaN_LIST.repeat_days[2] == enableFlag
                        Calendar.THURSDAY -> plaN_LIST.repeat_days[3] == enableFlag
                        Calendar.FRIDAY -> plaN_LIST.repeat_days[4] == enableFlag
                        Calendar.SATURDAY -> plaN_LIST.repeat_days[5] == enableFlag
                        Calendar.SUNDAY -> plaN_LIST.repeat_days[6] == enableFlag
                        else -> false
                    }
                    if (!open) continue
                } else if (plaN_LIST.repeat_type == Const.TYPE_REPEAT_ONCE){
                    //仅一次 过滤
                    if (startTime != plaN_LIST.target_time) continue
                }
                realm!!.insert(PLAN(plaN_LIST.plan_type, plaN_LIST.primary_key, 0, startTime, plaN_LIST.plan_title, plaN_LIST.plan_describe, plaN_LIST.expected_completion_number))
            }
        }
        realm!!.commitTransaction()
        val results = realmController!!.getAllPlanByTime(realm!!, planType, startTime)
        cardPlanListAdapter.setList(results)
    }

    /**
     * 初始化卡片大标题
     */
    private fun initCardTitle() {
        startTimeOfDay = DateUtil.getCurrentDayStartTime()
        startTimeOfWeek = DateUtil.getCurrentWeekStartTime()
        startTimeOfMonth = DateUtil.getCurrentMonthStartTime()
        startTimeOfYear = DateUtil.getCurrentYearStartTime()

        cardTitleDay = dateFormat.format(Date(startTimeOfDay))
        cardTitleWeek = String.format(Locale.getDefault(), "%s-%s", dateFormat.format(Date(startTimeOfWeek)), dateFormat.format(Date(startTimeOfWeek + 6 * DateUtil.DAY_MILLI_SECOND)))
        cardTitleMonth = cardTitleDay!!.substring(0, 7)
        cardTitleYear = cardTitleDay!!.substring(0, 4)

    }

    override fun initView() {
        //init plan tab
        rv_plan.layoutManager = LayoutManagerFactory.createLinearLayoutManager(context, true)
        rv_plan.addItemDecoration(ItemSpaceDecoration(0, 3, 2, 5))
        cardPlanListAdapter = CardPlanListAdapter(context, realm!!)
        rv_plan.adapter = cardPlanListAdapter
        rv_plan.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                v.parent.requestDisallowInterceptTouchEvent(true)
            }
            return@setOnTouchListener false
        }
        aiv_plan.isSelected = true
        setSupportActionBar(toolbar)
        tb_main.setupWithViewPager(vp_main)
        val mainPagerAdapter = MainPagerAdapter(supportFragmentManager, initFragmentList())
        vp_main.adapter = mainPagerAdapter
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
        //新建
        fab.setOnClickListener { startActivity(Intent(context, CreatePlanActivity::class.java).putExtra(Const.TAB_TITLE, mainPagerAdapter.getPageTitle(vp_main.currentItem))) }
        //日期选择
        aiv_date.setOnClickListener {
            val calendar = DateUtil.getCalendar()
            calendar.timeInMillis = startTimeOfDay
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                //设置时间
                startTimeOfDay = DateUtil.getTargetDayStartTime(year, month, dayOfMonth)
                startTimeOfWeek = DateUtil.getTargetWeekStartTime(year, month, dayOfMonth)
                startTimeOfMonth = DateUtil.getTargetMonthStartTime(year, month)
                startTimeOfYear = DateUtil.getTargetYearStartTime(year)

                cardTitleDay = dateFormat.format(Date(startTimeOfDay))
                cardTitleWeek = String.format(Locale.getDefault(), "%s-%s", dateFormat.format(Date(startTimeOfWeek)), dateFormat.format(Date(startTimeOfWeek + 6 * DateUtil.DAY_MILLI_SECOND)))
                cardTitleMonth = cardTitleDay!!.substring(0, 7)
                cardTitleYear = cardTitleDay!!.substring(0, 4)

                RxBus.get().post(EventMessage(currentPage))
            }, year, month, day).show()
        }
        //init setting
        sc_finger.isChecked = SpUtil.get(Const.FINGER_ENABLE, false)
        //指纹开关
        sc_finger.setOnCheckedChangeListener { buttonView, isChecked -> SpUtil.put(Const.FINGER_ENABLE, isChecked) }
        //每周开始
        tv_start_day.text = DateUtil.getDayOfWeekStr(SpUtil.get(Const.WEEK_START, Calendar.MONDAY), false)
        ll_choose_week_start_day.setOnClickListener {
            val popView = View.inflate(context, R.layout.popup_week_start_day, null)
            val popupWindow = PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
            val listener = View.OnClickListener {
                tv_start_day.text = (it as TextView).text
                val allWeekPlan = realmController!!.getAllPlanByType(realm!!, Const.TYPE_PLAN_WEEK)
                var dayOfWeekIndex = -1
                if (allWeekPlan.isNotEmpty()) {
                    val plan = allWeekPlan[0]
                    dayOfWeekIndex = DateUtil.getDayOfWeekIndex(plan!!.create_time)
                }
                when (it.id) {
                    R.id.tv_monday -> {
                        SpUtil.put(Const.WEEK_START, Calendar.MONDAY)
                        if (dayOfWeekIndex == Calendar.SUNDAY) {
                            realm!!.executeTransaction {
                                for (plan in allWeekPlan) {
                                    plan.create_time = plan.create_time + DateUtil.DAY_MILLI_SECOND;
                                }
                            }
                        } else if (dayOfWeekIndex == Calendar.SATURDAY) {
                            realm!!.executeTransaction {
                                for (plan in allWeekPlan) {
                                    plan.create_time = plan.create_time + 2 * DateUtil.DAY_MILLI_SECOND;
                                }
                            }
                        }
                    }
                    R.id.tv_saturday -> {
                        SpUtil.put(Const.WEEK_START, Calendar.SATURDAY)
                        if (dayOfWeekIndex == Calendar.SUNDAY) {
                            realm!!.executeTransaction {
                                for (plan in allWeekPlan) {
                                    plan.create_time = plan.create_time - DateUtil.DAY_MILLI_SECOND;
                                }
                            }
                        } else if (dayOfWeekIndex == Calendar.MONDAY) {
                            realm!!.executeTransaction {
                                for (plan in allWeekPlan) {
                                    plan.create_time = plan.create_time - 2 * DateUtil.DAY_MILLI_SECOND;
                                }
                            }
                        }
                    }
                    R.id.tv_sunday -> {
                        SpUtil.put(Const.WEEK_START, Calendar.SUNDAY)
                        if (dayOfWeekIndex == Calendar.MONDAY) {
                            realm!!.executeTransaction {
                                for (plan in allWeekPlan) {
                                    plan.create_time = plan.create_time - DateUtil.DAY_MILLI_SECOND;
                                }
                            }
                        } else if (dayOfWeekIndex == Calendar.SATURDAY) {
                            realm!!.executeTransaction {
                                for (plan in allWeekPlan) {
                                    plan.create_time = plan.create_time + DateUtil.DAY_MILLI_SECOND;
                                }
                            }
                        }
                    }
                }
                popupWindow.dismiss()
            }
            popView.findViewById<View>(R.id.tv_monday).setOnClickListener(listener)
            popView.findViewById<View>(R.id.tv_saturday).setOnClickListener(listener)
            popView.findViewById<View>(R.id.tv_sunday).setOnClickListener(listener)
            popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = MethodUtil.dp2px(6F).toFloat()
            } else {
                popView.setBackgroundResource(R.drawable.bg_alpha)
            }
            popupWindow.isOutsideTouchable = true //点击外部关闭。
            popupWindow.showAsDropDown(it, 0, -it.height)
        }
        //帮助
        tv_help.setOnClickListener {
            AlertDialog.Builder(context)
                    .setTitle(getString(R.string.help))
                    .setMessage(getString(R.string.help_message))
                    .setPositiveButton(getString(R.string.ok),{ dialog, which -> dialog.dismiss() })
                    .setCancelable(true).create().show()
        }

        //关于
        tv_about.setOnClickListener {
            AlertDialog.Builder(context)
                    .setTitle(getString(R.string.about))
                    .setMessage(BuildConfig.APP_NAME +" "+BuildConfig.VERSION_NAME +" ("+BuildConfig.VERSION_CODE+")" )
                    .setPositiveButton(getString(R.string.ok),{dialog, which -> dialog.dismiss() })
                    .setCancelable(true).create().show()
        }

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

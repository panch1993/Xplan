package com.pans.xplan.ui.activity

import android.app.DatePickerDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import com.pans.xplan.R
import com.pans.xplan.basic.BaseActivity
import com.pans.xplan.data.Const
import com.pans.xplan.data.realm.entity.PLAN
import com.pans.xplan.data.realm.entity.PLAN_LIST
import com.pans.xplan.util.DateUtil
import com.pans.xplan.util.MethodUtil
import com.pans.xplan.util.ResourceUtil
import com.pans.xplan.util.ToastUtil
import com.pans.xplan.widget.MenuPopupwindow
import kotlinx.android.synthetic.main.activity_create_plan.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author android01
 * @date 2018/5/15.
 * @time 下午5:19.
 */
class CreatePlanActivity : BaseActivity() {

    private lateinit var repeatType: String

    private lateinit var planType: String

    private var startTime: Long = 0L

    private var editPlan: PLAN_LIST? = null

    private val dateFormat = SimpleDateFormat(Const.Y_M_D_FORMAT, Locale.getDefault())

    private val onCheckedChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        if (!isChecked) {
            if (!cb1.isChecked
                    && !cb2.isChecked
                    && !cb3.isChecked
                    && !cb4.isChecked
                    && !cb5.isChecked
                    && !cb6.isChecked
                    && !cb7.isChecked) {
                ToastUtil.showToast("至少选择一个计划日期")
                buttonView.isChecked = true
            }
        }
    }

    override fun getLayoutResId(): Int = R.layout.activity_create_plan

    override fun showActionBar(): Boolean = true

    override fun showBackup(): Boolean = true

    override fun getRealmInstance(): Boolean = true

    override fun getTitleString(): String = ResourceUtil.getString(if (intent.getBooleanExtra(Const.IS_EDIT, false)) R.string.edit_plan else R.string.create_new_plan)

    override fun initData() {
        val planTypeArray = ResourceUtil.getStringArray(R.array.planTypeArray)
        val planKey = intent.getStringExtra(Const.PLAN_KEY)
        if (planKey != null) {
            editPlan = realm!!.where(PLAN_LIST::class.java)
                    .equalTo(Const.FIELD_PRIMARY_KEY, planKey)
                    .findFirst()
            if (editPlan == null) {
                ToastUtil.showToast("err CreatePlanActivity 74")
                context.finish()
                return
            }
            tv_plan_type.isEnabled = false
            planType = editPlan!!.plan_type
            repeatType = editPlan!!.repeat_type
            et_title.setText(editPlan!!.plan_title)
            et_describe.setText(editPlan!!.plan_describe)
            sc_plan_enable.isChecked = editPlan!!.isPlan_enable
            tv_num.text = editPlan!!.expected_completion_number.toString()
            tv_plan_type.text = when (planType) {
                Const.TYPE_PLAN_DAY -> planTypeArray[0]
                Const.TYPE_PLAN_WEEK -> planTypeArray[1]
                Const.TYPE_PLAN_MONTH -> planTypeArray[2]
                Const.TYPE_PLAN_YEAR -> planTypeArray[3]
                else -> "err -0"
            }
            when (repeatType) {
                Const.TYPE_REPEAT_EACH -> {
                    tv_repeat_type.text = when (planType) {
                        Const.TYPE_PLAN_DAY ->getString(R.string.repeat_day)
                        Const.TYPE_PLAN_WEEK ->getString(R.string.repeat_week)
                        Const.TYPE_PLAN_MONTH ->getString(R.string.repeat_month)
                        else->getString(R.string.repeat_year)
                    }
                }
                Const.TYPE_REPEAT_ONCE -> {
                    tv_repeat_type.text = "仅一次"
                    tv_special_date.visibility = View.VISIBLE
                    sc_plan_enable.visibility = View.GONE
                    startTime = editPlan!!.target_time
                    when (planType) {
                        Const.TYPE_PLAN_DAY -> {
                            tv_special_date.text = dateFormat.format(Date(startTime))
                        }
                        Const.TYPE_PLAN_WEEK -> {
                            tv_special_date.text = String.format(Locale.getDefault(), "%s  -  %s", dateFormat.format(Date(startTime)), dateFormat.format(Date(startTime + 6 * DateUtil.DAY_MILLI_SECOND)))
                        }
                        Const.TYPE_PLAN_MONTH -> {
                            tv_special_date.text = dateFormat.format(Date(startTime)).substring(0, 7)
                        }
                        Const.TYPE_PLAN_YEAR -> {
                            tv_special_date.text = dateFormat.format(Date(startTime)).substring(0, 4)
                        }
                    }
                }
                Const.TYPE_REPEAT_SPECIAL -> {
                    tv_repeat_type.text = "自定义"
                    ll_special.visibility = View.VISIBLE
                    for (i in editPlan!!.repeat_days.indices) {
                        (ll_special.getChildAt(i) as CheckBox).isChecked = editPlan!!.repeat_days[i] == 1.toByte()
                    }
                }
            }

        } else {

            //默认
            repeatType = Const.TYPE_REPEAT_EACH
            when (intent.getStringExtra(Const.TAB_TITLE)) {
                ResourceUtil.getString(R.string.day) -> {
                    tv_plan_type.text = planTypeArray[0]
                    planType = Const.TYPE_PLAN_DAY
                    tv_repeat_type.setText(R.string.repeat_day)
                }
                ResourceUtil.getString(R.string.week) -> {
                    tv_plan_type.text = planTypeArray[1]
                    planType = Const.TYPE_PLAN_WEEK
                    tv_repeat_type.setText(R.string.repeat_week)
                }
                ResourceUtil.getString(R.string.month) -> {
                    tv_plan_type.text = planTypeArray[2]
                    planType = Const.TYPE_PLAN_MONTH
                    tv_repeat_type.setText(R.string.repeat_month)
                }
                ResourceUtil.getString(R.string.year) -> {
                    tv_plan_type.text = planTypeArray[3]
                    planType = Const.TYPE_PLAN_YEAR
                    tv_repeat_type.setText(R.string.repeat_year)
                }
            }
        }

    }

    override fun initView() {

        //选择计划类型
        tv_plan_type.setOnClickListener {
            val planTypeArray = ResourceUtil.getStringArray(R.array.planTypeArray)
            val popupWindow = MenuPopupwindow(context, planTypeArray)
            popupWindow.setOnItemClickListener { v, position, str ->
                tv_plan_type.text = str

                if (repeatType != Const.TYPE_REPEAT_EACH) {
                    repeatType = Const.TYPE_REPEAT_EACH
                    tv_special_date.visibility = View.GONE
                    ll_special.visibility = View.GONE
                    sc_plan_enable.visibility = View.VISIBLE
                }
                when (str) {
                    getString(R.string.today_plan) -> {
                        planType = Const.TYPE_PLAN_DAY
                        tv_repeat_type.setText(R.string.repeat_day)
                    }
                    getString(R.string.week_plan) -> {
                        planType = Const.TYPE_PLAN_WEEK
                        tv_repeat_type.setText(R.string.repeat_week)
                    }
                    getString(R.string.month_plan) -> {
                        planType = Const.TYPE_PLAN_MONTH
                        tv_repeat_type.setText(R.string.repeat_month)
                    }
                    getString(R.string.year_plan) -> {
                        planType = Const.TYPE_PLAN_YEAR
                        tv_repeat_type.setText(R.string.repeat_year)
                    }
                }
                popupWindow.dismiss()
            }
            popupWindow.showAsDropDown(it, -MethodUtil.dp2px(100F), -it.height, Gravity.END)
        }
        //选择指定日期
        tv_special_date.setOnClickListener { showDatePicker() }
        //重复类型
        tv_repeat_type.setOnClickListener {
            var stringArray:Array<String>
               val eachStr = when (planType) {
                    Const.TYPE_PLAN_DAY ->getString(R.string.repeat_day)
                    Const.TYPE_PLAN_WEEK ->getString(R.string.repeat_week)
                    Const.TYPE_PLAN_MONTH ->getString(R.string.repeat_month)
                    else->getString(R.string.repeat_year)
                }
            stringArray = if (planType != Const.TYPE_PLAN_DAY) {
                arrayOf(eachStr, getString(R.string.repeat_once))
            } else {
                arrayOf(eachStr, getString(R.string.repeat_once),getString(R.string.repeat_special))
            }
            val popupWindow = MenuPopupwindow(context, stringArray)
            popupWindow.setOnItemClickListener { v, position, str ->
                when (str) {
                    getString(R.string.repeat_once) -> {
                        showDatePicker()
                    }
                    getString(R.string.repeat_special) -> {
                        tv_repeat_type.text = str
                        sc_plan_enable.visibility = View.VISIBLE
                        repeatType = Const.TYPE_REPEAT_SPECIAL
                        tv_special_date.visibility = View.GONE
                        ll_special.visibility = View.VISIBLE
                    }
                    else -> {
                        tv_repeat_type.text = str
                        sc_plan_enable.visibility = View.VISIBLE
                        repeatType = Const.TYPE_REPEAT_EACH
                        tv_special_date.visibility = View.GONE
                        ll_special.visibility = View.GONE
                    }
                }
                popupWindow.dismiss()
            }
            popupWindow.showAsDropDown(it, -MethodUtil.dp2px(100F), -it.height, Gravity.END)
        }
        //完成
        bt_complete.setOnClickListener { tryCreatePlanList() }
        //减少次数
        bt_subtract.setOnClickListener {
            val int = Integer.parseInt(tv_num.text.toString())
            if (int > 1) {
                tv_num.text = (int - 1).toString()
            }
        }
        //增加次数
        bt_add.setOnClickListener { tv_num.text = (Integer.parseInt(tv_num.text.toString()) + 1).toString() }

        et_title.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrBlank()) {
                    til_title.isErrorEnabled = true
                    til_title.error = "请输入计划标题"
                } else {
                    til_title.isErrorEnabled = false
                }
            }
        })
        //自定义日期
        cb1.setOnCheckedChangeListener(onCheckedChangeListener)
        cb2.setOnCheckedChangeListener(onCheckedChangeListener)
        cb3.setOnCheckedChangeListener(onCheckedChangeListener)
        cb4.setOnCheckedChangeListener(onCheckedChangeListener)
        cb5.setOnCheckedChangeListener(onCheckedChangeListener)
        cb6.setOnCheckedChangeListener(onCheckedChangeListener)
        cb7.setOnCheckedChangeListener(onCheckedChangeListener)
    }

    private fun showDatePicker() {
        val calendar = DateUtil.getCalendar()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            when (planType) {
                Const.TYPE_PLAN_DAY -> {
                    startTime = DateUtil.getTargetDayStartTime(year, month, dayOfMonth)
                    tv_special_date.text = dateFormat.format(Date(startTime))
                }
                Const.TYPE_PLAN_WEEK -> {
                    startTime = DateUtil.getTargetWeekStartTime(year, month, dayOfMonth)
                    tv_special_date.text = String.format(Locale.getDefault(), "%s  -  %s", dateFormat.format(Date(startTime)), dateFormat.format(Date(startTime + 6 * DateUtil.DAY_MILLI_SECOND)))

                }
                Const.TYPE_PLAN_MONTH -> {
                    startTime = DateUtil.getTargetMonthStartTime(year, month)
                    tv_special_date.text = dateFormat.format(Date(startTime)).substring(0, 7)
                }
                Const.TYPE_PLAN_YEAR -> {
                    startTime = DateUtil.getTargetYearStartTime(year)
                    tv_special_date.text = dateFormat.format(Date(startTime)).substring(0, 4)
                }
            }
            tv_repeat_type.setText(R.string.repeat_once)
            repeatType = Const.TYPE_REPEAT_ONCE
            ll_special.visibility = View.GONE
            sc_plan_enable.visibility = View.GONE
            tv_special_date.visibility = View.VISIBLE
        }, year, month, day).show()
    }

    private fun tryCreatePlanList() {
        if (et_title.text.isNullOrBlank()) {
            til_title.isErrorEnabled = true
            til_title.error = getString(R.string.tip_empty_plan_title)
            et_title.requestFocus()
            MethodUtil.showInputMethod(context, et_title)
            return
        }
        realm!!.executeTransaction {
            val same = realm!!.where(PLAN_LIST::class.java)
                    .equalTo(Const.FIELD_PLAN_TYPE, planType)
                    .equalTo(Const.FIELD_PLAN_TITLE, et_title.text.toString())
                    .findFirst()
            if (same != null && (editPlan == null || editPlan!!.primary_key != same.primary_key)) {
                til_title.isErrorEnabled = true
                til_title.error = getString(R.string.tip_plan_exist)
                et_title.requestFocus()
                MethodUtil.showInputMethod(context, et_title)
                return@executeTransaction
            }
            var days: ByteArray? = null
            if (repeatType == Const.TYPE_REPEAT_SPECIAL) {
                days = byteArrayOf(if (cb1.isChecked) 1 else 0
                        , if (cb2.isChecked) 1 else 0
                        , if (cb3.isChecked) 1 else 0
                        , if (cb4.isChecked) 1 else 0
                        , if (cb5.isChecked) 1 else 0
                        , if (cb6.isChecked) 1 else 0
                        , if (cb7.isChecked) 1 else 0)

            }
            if (editPlan == null) {
                realm!!.insert(PLAN_LIST(planType, et_title.text.toString(), et_describe.text.toString(), Date(),
                        planType != Const.TYPE_PLAN_YEAR, Integer.parseInt(tv_num.text.toString()), repeatType, days, if (repeatType == Const.TYPE_REPEAT_ONCE) true else sc_plan_enable.isChecked, Date(), if (repeatType == Const.TYPE_REPEAT_ONCE) startTime else 0))
                ToastUtil.showToast(getString(R.string.create_success))
            } else {
                editPlan!!.plan_title = et_title.text.toString()
                editPlan!!.plan_describe = et_describe.text.toString()
                editPlan!!.plan_type = planType
                editPlan!!.expected_completion_number = Integer.parseInt(tv_num.text.toString())
                editPlan!!.repeat_type = repeatType
                editPlan!!.repeat_days = days
                editPlan!!.isPlan_enable = if (repeatType == Const.TYPE_REPEAT_ONCE) true else sc_plan_enable.isChecked
                editPlan!!.target_time = if (repeatType == Const.TYPE_REPEAT_ONCE) startTime else 0
                editPlan!!.modifier_date = Date()

                val findAll = realm!!.where(PLAN::class.java)
                        .equalTo(Const.FIELD_PRIMARY_KEY, editPlan!!.primary_key)
                        .findAll()
                for (plan in findAll) {
                    plan.plan_title = et_title.text.toString()
                    plan.expected_completion_number = Integer.parseInt(tv_num.text.toString())
                    plan.plan_describe = et_describe.text.toString()
                }
                ToastUtil.showToast(getString(R.string.edit_success))
            }
            context.finish()
        }
    }
}
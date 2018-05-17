package com.pans.xplan.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import com.pans.xplan.R
import com.pans.xplan.basic.BaseActivity
import com.pans.xplan.data.Const
import com.pans.xplan.data.realm.entity.PLAN_LIST
import com.pans.xplan.util.MethodUtil
import com.pans.xplan.util.ResourceUtil
import com.pans.xplan.util.ToastUtil
import kotlinx.android.synthetic.main.activity_create_plan.*
import java.util.*

/**
 * @author android01
 * @date 2018/5/15.
 * @time 下午5:19.
 */
class CreatePlanActivity : BaseActivity() {

    lateinit var repeatType: String

    lateinit var planType: String

    private val rgListener: (RadioGroup, Int) -> Unit = { group, checkedId ->
        when (checkedId) {
            R.id.rb_day -> {
                rb_each.text = getString(R.string.repeat_each, getString(R.string.day))
                planType = Const.TYPE_PLAN_DAY
                rb_special.visibility = View.VISIBLE
            }
            R.id.rb_week -> {
                rb_each.text = getString(R.string.repeat_each, getString(R.string.week))
                planType = Const.TYPE_PLAN_WEEK
                rb_special.visibility = View.INVISIBLE
                ll_special.visibility = View.GONE
                if (rb_special.isChecked) rg_repeat_type.check(R.id.rb_once)
            }
            R.id.rb_month -> {
                rb_each.text = getString(R.string.repeat_each, getString(R.string.month))
                planType = Const.TYPE_PLAN_MONTH
                rb_special.visibility = View.INVISIBLE
                ll_special.visibility = View.GONE
                if (rb_special.isChecked) rg_repeat_type.check(R.id.rb_once)
            }
            R.id.rb_year -> {
                rb_each.text = getString(R.string.repeat_each, getString(R.string.year))
                planType = Const.TYPE_PLAN_YEAR
                rb_special.visibility = View.INVISIBLE
                ll_special.visibility = View.GONE
                if (rb_special.isChecked) rg_repeat_type.check(R.id.rb_once)
            }
            R.id.rb_once -> {
                repeatType = Const.TYPE_REPEAT_ONCE
                ll_special.visibility = View.GONE
            }
            R.id.rb_each -> {
                repeatType = Const.TYPE_REPEAT_EACH
                ll_special.visibility = View.GONE
            }
            R.id.rb_special -> {
                repeatType = Const.TYPE_REPEAT_SPECIAL
                ll_special.visibility = View.VISIBLE
            }
        }
    }

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

    override fun getTitleString(): String = ResourceUtil.getString(R.string.create_new_plan)

    override fun initData() {

    }

    override fun initView() {

        rg_plan_type.setOnCheckedChangeListener(rgListener)
        rg_repeat_type.setOnCheckedChangeListener(rgListener)
        //默认
        rg_plan_type.check(R.id.rb_day)
        rg_repeat_type.check(R.id.rb_each)
        bt_complete.setOnClickListener { tryCreatePlanList() }
        bt_subtract.setOnClickListener {
            val int = Integer.parseInt(tv_num.text.toString())
            if (int > 1) {
                tv_num.text = (int - 1).toString()
            }
        }
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
        cb1.setOnCheckedChangeListener(onCheckedChangeListener)
        cb2.setOnCheckedChangeListener(onCheckedChangeListener)
        cb3.setOnCheckedChangeListener(onCheckedChangeListener)
        cb4.setOnCheckedChangeListener(onCheckedChangeListener)
        cb5.setOnCheckedChangeListener(onCheckedChangeListener)
        cb6.setOnCheckedChangeListener(onCheckedChangeListener)
        cb7.setOnCheckedChangeListener(onCheckedChangeListener)
    }

    fun tryCreatePlanList() {
        if (et_title.text.isNullOrBlank()) {
            til_title.isErrorEnabled = true
            til_title.error = "请输入计划标题"
            et_title.requestFocus()
            MethodUtil.showInputMethod(context, et_title)
            return
        }
        realm!!.executeTransaction {
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
            realm!!.insert(PLAN_LIST(planType, et_title.text.toString(), et_describe.text.toString(), Date(), planType != Const.TYPE_PLAN_YEAR, Integer.parseInt(tv_num.text.toString()), repeatType, days))
            ToastUtil.showToast("创建成功")
            context.finish()
        }
    }
}
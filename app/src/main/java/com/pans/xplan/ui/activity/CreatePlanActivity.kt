package com.pans.xplan.ui.activity

import android.view.MenuItem
import android.widget.RadioGroup
import com.pans.xplan.R
import com.pans.xplan.basic.BaseActivity
import com.pans.xplan.data.realm.entity.PLAN
import com.pans.xplan.util.ToastUtil
import io.realm.Realm
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

    val rgListener: (RadioGroup, Int) -> Unit = { group, checkedId ->
        when (checkedId) {
            R.id.rb_day -> {
                rb_each.text = getString(R.string.repeat_each, getString(R.string.day))
                planType = PLAN.PLAN_DAY
            }
            R.id.rb_week -> {
                rb_each.text = getString(R.string.repeat_each, getString(R.string.week))
                planType = PLAN.PLAN_WEEK
            }
            R.id.rb_month -> {
                rb_each.text = getString(R.string.repeat_each, getString(R.string.month))
                planType = PLAN.PLAN_MONTH
            }
            R.id.rb_year -> {
                rb_each.text = getString(R.string.repeat_each, getString(R.string.year))
                planType = PLAN.PLAN_YEAR
            }
            R.id.rb_once -> repeatType = PLAN.REPEAT_ONCE
            R.id.rb_each -> repeatType = PLAN.REPEAT_EACH
        }
    }

    override fun getLayoutResId(): Int = R.layout.activity_create_plan

    override fun showActionBar(): Boolean = true

    override fun showBackup(): Boolean = true

    override fun getTitleString(): String = "新建"

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            context.finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initData() {

    }

    override fun initView() {

        rg_plan_type.setOnCheckedChangeListener(rgListener)
        rg_repeat_type.setOnCheckedChangeListener(rgListener)
        //默认
        rg_plan_type.check(R.id.rb_day)
        rg_repeat_type.check(R.id.rb_once)
        bt_complete.setOnClickListener {
            if (et_title.text.isNullOrBlank()) {
                ToastUtil.showToast("标题为空")
                return@setOnClickListener
            }
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            val plan = PLAN(planType,et_title.text.toString(),et_describe.text.toString(),null,Date(),planType!=PLAN.PLAN_YEAR,1,repeatType)
            realm.insert(plan)
            realm.commitTransaction()
            ToastUtil.showToast("创建成功")
            realm.close()
            context.finish()
        }
    }
}
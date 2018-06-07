package com.pans.xplan.ui.fragment

import android.support.v7.widget.RecyclerView
import com.pans.xplan.R
import com.pans.xplan.basic.BaseFragment
import com.pans.xplan.data.Const
import com.pans.xplan.data.realm.RealmController
import com.pans.xplan.data.realm.entity.PLAN_LIST
import com.pans.xplan.ui.adapter.PlanListAdapter
import com.pans.xplan.util.LayoutManagerFactory
import com.pans.xplan.util.bus.EventMessage
import com.pans.xplan.util.bus.RxBus
import com.pans.xplan.widget.ItemSpaceDecoration
import io.realm.RealmChangeListener
import io.realm.RealmResults

/**
 * @author android01
 * @date 2018/5/15.
 * @time 下午2:13.
 */
class PlanListFragment : BaseFragment() {
    private lateinit var plans: RealmResults<PLAN_LIST>
    private lateinit var adapter: PlanListAdapter

    override fun getTitleString(): String = arguments!!.getString(Const.FRAGMENT_TITLE)

    override fun getLayoutResId(): Int = R.layout.layout_recycler_view

    override fun getRealmInstance(): Boolean = true

    override fun initData() {
        plans = RealmController().findListByPlanType(realm!!, arguments!!.getString(Const.FIELD_PLAN_TYPE),true)
        plans.addChangeListener(RealmChangeListener {
            adapter.notifyDataSetChanged()
        })
    }

    override fun initView() {
        val recyclerView = rootView as RecyclerView
        recyclerView.layoutManager = LayoutManagerFactory.createLinearLayoutManager(context, true)
        adapter = PlanListAdapter(context,realm!!)
        adapter.setList(plans)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(ItemSpaceDecoration(10, 5, 5, 10))
    }

    override fun firstVisibleToUser() {

    }

    override fun onVisibleToUserChanged(isVisibleToUser: Boolean, invokeInResumeOrPause: Boolean) {
        super.onVisibleToUserChanged(isVisibleToUser, invokeInResumeOrPause)
        if (isVisibleToUser) {
            when (arguments!!.getString(Const.FIELD_PLAN_TYPE)) {
                Const.TYPE_PLAN_DAY -> {
                    RxBus.get().post(EventMessage(EventMessage.TYPE.SHOW_DAY))
                }
                Const.TYPE_PLAN_WEEK -> {
                    RxBus.get().post(EventMessage(EventMessage.TYPE.SHOW_WEEK))
                }
                Const.TYPE_PLAN_MONTH -> {
                    RxBus.get().post(EventMessage(EventMessage.TYPE.SHOW_MONTH))
                }
                Const.TYPE_PLAN_YEAR -> {
                    RxBus.get().post(EventMessage(EventMessage.TYPE.SHOW_YEAR))
                }
            }
        }
    }
}

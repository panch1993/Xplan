package com.pans.xplan.ui.fragment

import android.support.v7.widget.RecyclerView
import com.pans.xplan.R
import com.pans.xplan.basic.BaseFragment
import com.pans.xplan.data.Const
import com.pans.xplan.data.realm.entity.PLAN
import com.pans.xplan.ui.adapter.PlanListAdapter
import com.pans.xplan.util.LayoutManagerFactory
import com.pans.xplan.widget.ItemSpaceDecoration
import io.realm.RealmResults

/**
 * @author android01
 * @date 2018/5/15.
 * @time 下午2:13.
 */
class PlanListFragment:BaseFragment() {
    lateinit var plans:RealmResults<PLAN>

    override fun getTitleString(): String = arguments!!.getString(Const.FRAGMENT_TITLE)

    override fun getLayoutResId(): Int = R.layout.layout_recycler_view

    override fun getRealmInstance(): Boolean = true

    override fun initData() {
        plans = realm?.where(PLAN::class.java)?.equalTo(Const.FIELD_PLAN_TYPE, arguments!!.getString(Const.FIELD_PLAN_TYPE))?.findAll() as RealmResults<PLAN>
    }

    override fun initView() {
        val recyclerView = rootView as RecyclerView
        recyclerView.layoutManager = LayoutManagerFactory.createLinearLayoutManager(context,true)
        val adapter = PlanListAdapter(context)
        adapter.setList(plans)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(ItemSpaceDecoration(10,5,5,10))
    }

    override fun firstVisibleToUser() {

    }
}

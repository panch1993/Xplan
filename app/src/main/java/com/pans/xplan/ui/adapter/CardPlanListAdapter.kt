package com.pans.xplan.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.pans.xplan.R
import com.pans.xplan.data.realm.entity.PLAN
import com.pans.xplan.ui.adapter.holder.EmptyHolder
import com.pans.xplan.util.ResourceUtil
import io.realm.Realm
import io.realm.RealmResults

/**
 * @author android01
 * @date 2018/5/18.
 * @time 下午6:47.
 */
class CardPlanListAdapter(val context: Context, val realm: Realm) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mList: RealmResults<PLAN>? = null


    fun setList(list: RealmResults<PLAN>) {
        mList?.removeAllChangeListeners()
        mList = list
        mList?.addChangeListener { t: RealmResults<PLAN>? ->
            notifyDataSetChanged()
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(viewType, parent, false)
        return if (mList == null || mList!!.isEmpty()) EmptyHolder(view) else ItemHolder(view)
    }

    override fun getItemCount(): Int = if (mList == null || mList!!.isEmpty()) 1 else mList!!.size

    override fun getItemViewType(position: Int): Int = if (mList == null || mList!!.isEmpty()) R.layout.layout_empty_chart else R.layout.item_card_plan

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) {
            val plan = mList!![position]
            holder.tvTitle.text = plan!!.plan_title
            if (plan.expected_completion_number > 1) {
                holder.tvCompleteTimes.visibility = View.VISIBLE
                holder.tvCompleteTimes.text = ResourceUtil.getString(R.string.complete_times, plan.complete_times.toString(), plan.expected_completion_number.toString())
                holder.aivComplete.setImageResource(R.drawable.selector_plan_check_add)
            } else {
                holder.tvCompleteTimes.visibility = View.GONE
                holder.aivComplete.setImageResource(R.drawable.selector_plan_check)
            }

            holder.aivComplete.isSelected = plan.complete_times == plan.expected_completion_number

            holder.aivComplete.setOnClickListener { view ->
                realm.executeTransaction {
                    val iPlan = mList!![holder.adapterPosition]
                    if (view.isSelected) {
                        iPlan!!.complete_times = 0
                    } else {
                        iPlan!!.complete_times = iPlan.complete_times + 1
                    }
                    CardPlanListAdapter@ this.notifyItemChanged(holder.adapterPosition)
                }
            }
        }
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvTitle: TextView = itemView.findViewById(R.id.tv_plan_title)
        var tvCompleteTimes: TextView = itemView.findViewById(R.id.tv_complete_times)
        var aivComplete: ImageView = itemView.findViewById(R.id.aiv_plan_complete)
    }
}
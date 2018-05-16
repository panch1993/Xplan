package com.pans.xplan.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pans.xplan.R
import com.pans.xplan.data.Const
import com.pans.xplan.data.realm.entity.PLAN
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author android01
 * @date 2018/5/16.
 * @time 下午2:54.
 */
class PlanListAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var mList: List<PLAN>

    val format: SimpleDateFormat = SimpleDateFormat(Const.Y_M_D_FORMAT, Locale.getDefault())

    fun setList(list: List<PLAN>) {
        mList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(viewType, parent, false)
        return if (mList.isEmpty()) EmptyHolder(view) else PlanHolder(view)
    }

    override fun getItemCount(): Int = if (mList.isEmpty()) 1 else mList.size

    override fun getItemViewType(position: Int): Int = if (mList.isEmpty()) R.layout.layout_empty else R.layout.item_plan

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PlanHolder) {
            val plan = mList[position]
            holder.tvTitle.text = plan.plan_title
            holder.tvDescribe.text = plan.plan_describe
            holder.tvCreateDate.text = context.getString(R.string.create_date, format.format(plan.create_date))
        } else if (holder is EmptyHolder) {

        }
    }


    inner class PlanHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        var tvDescribe: TextView = itemView.findViewById(R.id.tv_describe)
        var tvCreateDate: TextView = itemView.findViewById(R.id.tv_create_date)
    }

    inner class EmptyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}

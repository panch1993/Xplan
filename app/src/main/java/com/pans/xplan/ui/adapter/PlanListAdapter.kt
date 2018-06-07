package com.pans.xplan.ui.adapter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.pans.xplan.R
import com.pans.xplan.data.Const
import com.pans.xplan.data.realm.entity.PLAN
import com.pans.xplan.data.realm.entity.PLAN_LIST
import com.pans.xplan.ui.activity.CreatePlanActivity
import com.pans.xplan.ui.adapter.holder.EmptyHolder
import com.pans.xplan.util.ResourceUtil
import io.realm.Realm
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author android01
 * @date 2018/5/16.
 * @time 下午2:54.
 */
class PlanListAdapter(val context: Context, val realm: Realm) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var mList: List<PLAN_LIST>

    val format: SimpleDateFormat = SimpleDateFormat(Const.Y_M_D_FORMAT, Locale.getDefault())

    fun setList(list: List<PLAN_LIST>) {
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
            when (plan.repeat_type) {
                Const.TYPE_REPEAT_ONCE -> holder.aivRepeatType.setImageResource(R.drawable.ic_repeat_once)
                Const.TYPE_REPEAT_EACH -> holder.aivRepeatType.setImageResource(if (plan.isPlan_enable) R.drawable.ic_repeat_each else R.drawable.ic_unable_mark)
                Const.TYPE_REPEAT_SPECIAL -> holder.aivRepeatType.setImageResource(if (plan.isPlan_enable) R.drawable.ic_repeat_special else R.drawable.ic_unable_mark)
            }
            holder.aivRepeatType.setOnClickListener {
                if (plan.repeat_type == Const.TYPE_REPEAT_ONCE) return@setOnClickListener
                realm.executeTransaction {
                    plan.isPlan_enable = !plan.isPlan_enable
                }
            }
            holder.tvTitle.setTextColor(ResourceUtil.getColor(if (plan.isPlan_enable) R.color.colorPrimaryText else R.color.colorSecondText))
            holder.tvDescribe.text = plan.plan_describe
            holder.tvCreateDate.text = context.getString(R.string.create_date, format.format(plan.create_date))
            holder.itemView.setOnClickListener {
                context.startActivity(Intent(context, CreatePlanActivity::class.java)
                        .putExtra(Const.PLAN_KEY,plan.primary_key)
                        .putExtra(Const.IS_EDIT, true))
            }
            holder.itemView.setOnLongClickListener {
                val alertDialog = AlertDialog.Builder(context)
                        .setIcon(R.drawable.ic_warning)
                        .setMessage(context.getString(R.string.tip_delete))
                        .setPositiveButton(context.getString(R.string.delete), { dialog, which ->
                            realm.executeTransaction {
                                val plaN_LIST = mList[holder.adapterPosition]
                                realm.where(PLAN::class.java)
                                        .equalTo(Const.FIELD_PRIMARY_KEY, plaN_LIST.primary_key)
                                        .findAll().deleteAllFromRealm()
                                plaN_LIST.deleteFromRealm()
                            }
                            dialog.dismiss()
                        })
                        .setNegativeButton(context.getString(R.string.cancel), { dialog, which ->
                            dialog.dismiss()
                        })
                        .setCancelable(true)
                        .create()
                alertDialog.show()
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.RED)

                return@setOnLongClickListener true
            }
        } else if (holder is EmptyHolder) {
            holder.itemView.setOnClickListener {
                val make = Snackbar.make(holder.itemView.parent as ViewGroup, R.string.tip_create_plan, Snackbar.LENGTH_SHORT)
                make.view.findViewById<TextView>(R.id.snackbar_text).setTextColor(Color.WHITE)
                make.setAction("新建↑", { context.startActivity(Intent(context, CreatePlanActivity::class.java)) })
                make.show()
            }
        }
    }


    inner class PlanHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        var tvDescribe: TextView = itemView.findViewById(R.id.tv_describe)
        var tvCreateDate: TextView = itemView.findViewById(R.id.tv_create_date)
        var aivRepeatType: ImageView = itemView.findViewById(R.id.aiv_repeat_type)
    }


}

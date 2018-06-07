package com.pans.xplan.data.realm

import com.pans.xplan.data.Const
import com.pans.xplan.data.realm.entity.PLAN
import com.pans.xplan.data.realm.entity.PLAN_LIST
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.Sort

/**
 * @author android01
 * @date 2018/5/14.
 * @time 上午11:26.
 */
class RealmController {

    companion object {
        //数据库版本号
        private const val versionCode = 1L
        //数据库名称
        private const val fileName = "xPlan"
        //配置
        val realmConfiguration = RealmConfiguration.Builder()
                .name(fileName)
                .schemaVersion(versionCode)
                //数据库版本变更操作
                .migration(Migration())
                .build()!!
    }

    /**
     * 获取该类别下所有计划
     */
    fun findPlansByPlanType(realm: Realm, plan_type: String, desSort: Boolean): RealmResults<PLAN> =
            realm.where(PLAN::class.java)
                    .equalTo(Const.FIELD_PLAN_TYPE, plan_type)
                    .findAll()
                    .sort(Const.FIELD_MODIFIER_DATE, if (desSort) Sort.DESCENDING else Sort.ASCENDING)

    /**
     * 获取类别下所有清单
     */
    fun findListByPlanType(realm: Realm, plan_type: String, desSort: Boolean): RealmResults<PLAN_LIST> =
            realm.where(PLAN_LIST::class.java)
                    .equalTo(Const.FIELD_PLAN_TYPE, plan_type)
                    .findAll()
                    .sort(Const.FIELD_MODIFIER_DATE, if (desSort) Sort.DESCENDING else Sort.ASCENDING)

    /**
     * 校验该时间的有无计划
     */
    fun checkPlanExist(realm: Realm, key: PLAN_LIST, createTime: Long): Boolean {
        val plan = realm.where(PLAN::class.java)
                .equalTo(Const.FIELD_PRIMARY_KEY, key.primary_key)
                .equalTo("create_time", createTime)
                .findFirst()
        return plan != null
    }

    fun getEnablePlanList(realm: Realm, plan_type: String): RealmResults<PLAN_LIST> =
            realm.where(PLAN_LIST::class.java)
                    .equalTo(Const.FIELD_PLAN_TYPE, plan_type)
                    .equalTo(Const.FIELD_PLAN_ENABLE, true)
                    .findAll()
                    .sort(Const.FIELD_MODIFIER_DATE, Sort.DESCENDING)

    fun getAllPlanByTime(realm:Realm,plan_type: String,createTime: Long):RealmResults<PLAN> =
            realm.where(PLAN::class.java)
                    .equalTo(Const.FIELD_PLAN_TYPE,plan_type)
                    .equalTo("create_time", createTime)
                    .findAll()

    fun getAllPlanByType(realm: Realm,plan_type: String):RealmResults<PLAN> =
            realm.where(PLAN::class.java)
                    .equalTo(Const.FIELD_PLAN_TYPE,plan_type)
                    .findAll()


}
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
        val versionCode = 2L
        //数据库名称
        val fileName = "xPlan"
        //配置
        val realmConfiguration = RealmConfiguration.Builder()
                .name(fileName)
                .schemaVersion(versionCode)
                //数据库版本变更操作
                .migration(Migration())
                .build()
    }

    /**
     * 获取该类别下所有计划
     */
    fun findPlansByPlanType(realm: Realm,plan_type: String,desSort:Boolean):RealmResults<PLAN> =
            realm.where(PLAN::class.java)
                    .equalTo(Const.FIELD_PLAN_TYPE,plan_type)
                    .findAll()
                    .sort(Const.FIELD_CREATE_DATE,if (desSort) Sort.DESCENDING else Sort.ASCENDING)

    /**
     * 获取类别下所有清单
     */
    fun findListByPlanType(realm: Realm,plan_type: String,desSort:Boolean):RealmResults<PLAN_LIST> =
            realm.where(PLAN_LIST::class.java)
                    .equalTo(Const.FIELD_PLAN_TYPE,plan_type)
                    .findAll()
                    .sort(Const.FIELD_CREATE_DATE,if (desSort) Sort.DESCENDING else Sort.ASCENDING)
}
package com.pans.xplan.data.realm

import io.realm.RealmConfiguration

/**
 * @author android01
 * @date 2018/5/14.
 * @time 上午11:26.
 */
class RealmController {

    companion object {
        //数据库版本号
        val versionCode = 1L
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
}
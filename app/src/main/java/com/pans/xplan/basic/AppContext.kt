package com.pans.xplan.basic

import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatDelegate
import com.pans.xplan.data.Const
import com.pans.xplan.data.realm.RealmController
import com.pans.xplan.util.SpUtil
import com.umeng.commonsdk.UMConfigure
import io.realm.Realm

/**
 * @author android01
 * @date 2018/5/14.
 * @time 上午11:03.
 */
class AppContext : Application() {
    companion object {
        @JvmStatic
        lateinit var instance: AppContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val sp = getSharedPreferences(Const.SP_NAME, Context.MODE_PRIVATE)
        SpUtil.get().init(sp)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        Realm.init(this)
        Realm.setDefaultConfiguration(RealmController.realmConfiguration)

        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null)
    }
}
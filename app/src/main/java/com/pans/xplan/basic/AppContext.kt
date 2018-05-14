package com.pans.xplan.basic

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.umeng.commonsdk.UMConfigure

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

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

//        Realm.init(this)
//        Realm.setDefaultConfiguration(RealmController.realmConfiguration)

        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null)
    }
}
package com.pans.xplan.ui.activity

import android.content.Intent
import com.pans.xplan.R
import com.pans.xplan.basic.BaseActivity
import com.pans.xplan.data.Const
import com.pans.xplan.util.SpUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author android01
 * @date 2018/5/17.
 * @time 下午6:28.
 */
class SplashActivity : BaseActivity() {
    var disposable: Disposable? = null

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun initData() {

    }

    override fun initView() {
        disposable = Observable.interval(2, TimeUnit.SECONDS)
                .take(1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { aLong ->
                    if (SpUtil.get(Const.FINGER_ENABLE, false)) {
                        startActivity(Intent(context, FingerActivity::class.java))
                    } else {
                        startActivity(Intent(context, MainActivity::class.java))
                    }
                    context.finish()
                }

    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

}
package com.pans.xplan.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.view.WindowManager
import com.pans.xplan.R
import com.pans.xplan.basic.BaseActivity
import com.pans.xplan.util.ToastUtil
import kotlinx.android.synthetic.main.activity_finger.*

/**
 * @author android01
 * @date 2018/5/17.
 * @time 下午4:54.
 */
class FingerActivity:BaseActivity() {
    private lateinit var fingerprintManagerCompat:FingerprintManagerCompat
    private lateinit var keyguardManager: KeyguardManager


    @SuppressLint("NewApi")
    var callBack = object :FingerprintManagerCompat.AuthenticationCallback(){
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
            tv_state.text = if (errString.toString()!= "0") errString.toString() else "请稍候再试"
        }

        override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
            tv_state.text = "指纹识别成功"
            startActivity(Intent(context,MainActivity::class.java))
            context.finish()
        }


        override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
            tv_state.text = helpString.toString()
        }

        override fun onAuthenticationFailed() {
            tv_state.text = "指纹识别失败"
        }
    }
    override fun getLayoutResId(): Int = R.layout.activity_finger

    override fun beforeSetContentView(savedInstanceState: Bundle?) {
        super.beforeSetContentView(savedInstanceState)
        //不显示通知栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
    override fun onBackPressed() {

    }
    override fun initData() {

    }

    override fun initView() {
        iv_close.setOnClickListener { context.finish() }
        keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        fingerprintManagerCompat = FingerprintManagerCompat.from(this)

        if (isFinger()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                ToastUtil.showToast("没有指纹识别权限")
                return
            }
            fingerprintManagerCompat.authenticate(null,0,null,callBack,null)
        }
    }


    fun isFinger(): Boolean {
        if (!fingerprintManagerCompat.isHardwareDetected) {
            ToastUtil.showToast("没有指纹识别模块")
            return false
        }
        if (!keyguardManager.isKeyguardSecure) {
            ToastUtil.showToast("没有开启锁屏密码")
            return false
        }
        if (!fingerprintManagerCompat.hasEnrolledFingerprints()) {
            ToastUtil.showToast("没有录入指纹")
            return false
        }
        return true
    }

}
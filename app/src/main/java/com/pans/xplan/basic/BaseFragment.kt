package com.pans.xplan.basic

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pans.xplan.interfaces.GetTitleImpl
import com.pans.xplan.util.FragmentUserVisibleController
import io.realm.Realm

/**
 * @author android01
 * @date 2018/5/14.
 * @time 上午11:02.
 */
abstract class BaseFragment : Fragment(), FragmentUserVisibleController.UserVisibleCallback,GetTitleImpl {
    private var userVisibleController: FragmentUserVisibleController = FragmentUserVisibleController(this, this)
    lateinit var rootView: View
    lateinit var context: BaseActivity
    var realm: Realm? = null
    private var isInit = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = activity as BaseActivity
        if (getRealmInstance()) realm = Realm.getDefaultInstance()
        initFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userVisibleController.activityCreated()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(getLayoutResId(), container, false)
        initData()
        initView()
        return rootView
    }


    override fun onResume() {
        super.onResume()
        userVisibleController.resume()
    }

    override fun onPause() {
        super.onPause()
        userVisibleController.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm?.close()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        userVisibleController.setUserVisibleHint(isVisibleToUser)
    }

    override fun setWaitingShowToUser(waitingShowToUser: Boolean) {
        userVisibleController.isWaitingShowToUser = waitingShowToUser
    }

    override fun isWaitingShowToUser(): Boolean = userVisibleController.isWaitingShowToUser

    override fun isVisibleToUser(): Boolean = userVisibleController.isVisibleToUser

    override fun callSuperSetUserVisibleHint(isVisibleToUser: Boolean) = super.setUserVisibleHint(isVisibleToUser)


    override fun onVisibleToUserChanged(isVisibleToUser: Boolean, invokeInResumeOrPause: Boolean) {
        if (isInit && isVisibleToUser) {
            isInit = false
            firstVisibleToUser()
        }
    }

    override fun getTitleString(): String = ""

    open fun getRealmInstance(): Boolean = false

    open fun initFragment() {}

    open fun firstVisibleToUser() {}

    abstract fun getLayoutResId(): Int

    abstract fun initData()

    abstract fun initView()
}
package com.pans.xplan.basic

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pans.xplan.util.FragmentUserVisibleController

/**
 * @author android01
 * @date 2018/5/14.
 * @time 上午11:02.
 */
abstract class BaseFragment : Fragment(), FragmentUserVisibleController.UserVisibleCallback {
    private var userVisibleController: FragmentUserVisibleController? = null
    protected var rootView: View? = null
    protected var context: BaseActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = activity as BaseActivity?
        userVisibleController = FragmentUserVisibleController(this, this)
        initFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userVisibleController?.activityCreated()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(getLayoutResId(), container, false)
        return rootView
    }


    override fun onResume() {
        super.onResume()
        userVisibleController!!.resume()
    }

    override fun onPause() {
        super.onPause()
        userVisibleController!!.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        userVisibleController!!.setUserVisibleHint(isVisibleToUser)
    }

    override fun setWaitingShowToUser(waitingShowToUser: Boolean) {
        userVisibleController!!.isWaitingShowToUser = waitingShowToUser
    }

    override fun isWaitingShowToUser(): Boolean {
        return userVisibleController!!.isWaitingShowToUser
    }

    override fun isVisibleToUser(): Boolean {
        return userVisibleController!!.isVisibleToUser
    }

    override fun onVisibleToUserChanged(isVisibleToUser: Boolean, invokeInResumeOrPause: Boolean) {

    }

    protected fun initFragment() {}

    abstract fun getLayoutResId(): Int
}
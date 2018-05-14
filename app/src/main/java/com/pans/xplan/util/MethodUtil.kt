package com.pans.xplan.util

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.text.TextUtils
import android.view.WindowManager
import com.pans.xplan.basic.AppContext
import java.net.NetworkInterface
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by panchenhuan on 17/3/18..
 */
class MethodUtil {

    companion object {
        /**
         * 根据手机的分辨率从dp 的单位 转成为px(像素)
         */
        fun dp2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        fun dp2px(dpValue: Float): Int {
            val scale = AppContext.instance.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从px(像素) 的单位 转成为dp
         */
        fun px2dp(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * 根据手机分辨率，从sp转换为px
         * @param spValue   字号大小
         */
        fun sp2px(spValue: Float): Int {
            val fontScale = AppContext.instance.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        fun getWindowWidth(activity: Context): Int {
            val wm = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            return wm.defaultDisplay.width
        }

        fun getWindowHeigth(activity: Context): Int {
            val wm = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val width = wm.defaultDisplay.width
            return wm.defaultDisplay.height
        }

        /**
         *
         * 获取分页的总页数
         */
        fun getTotalPage(pagesize: Int, totalcount: Int): Int {
            return if (totalcount <= pagesize) {
                1
            } else {
                totalcount / pagesize + 1
            }
        }


        fun getDisplayDateTime(originalDate: Long): String {
            val today = Calendar.getInstance()
            val ty = today.get(Calendar.YEAR)
            val td = today.get(Calendar.DAY_OF_YEAR)

            val original = Calendar.getInstance()
            original.timeInMillis = originalDate
            val oy = original.get(Calendar.YEAR)
            val od = original.get(Calendar.DAY_OF_YEAR)
            var returnMsg = ""
            val dateFormat: SimpleDateFormat
            if (ty == oy) {
                when (td - od) {
                    0 -> {
                        dateFormat = SimpleDateFormat("HH:mm", Locale.CHINA)
                        returnMsg = dateFormat.format(Date(originalDate))
                    }
                    1 -> {
                        dateFormat = SimpleDateFormat("HH:mm", Locale.CHINA)
                        returnMsg = "昨天 " + dateFormat.format(Date(originalDate))
                    }
                    2 -> {
                        dateFormat = SimpleDateFormat("HH:mm", Locale.CHINA)
                        returnMsg = "前天 " + dateFormat.format(Date(originalDate))
                    }
                    3 -> {
                        dateFormat = SimpleDateFormat("HH:mm", Locale.CHINA)
                        returnMsg = convertNumToWeek(original.get(Calendar.DAY_OF_WEEK)) + " " + dateFormat.format(Date(originalDate))
                    }

                    else -> {
                        dateFormat = SimpleDateFormat("MM-dd HH:mm", Locale.CHINA)
                        returnMsg = dateFormat.format(Date(originalDate))
                    }
                }
            } else {
                dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
                returnMsg = dateFormat.format(Date(originalDate))
            }
            if ("00:00" == returnMsg) {
                returnMsg = "今天"
            }
            return returnMsg
        }

        fun convertNumToWeek(num: Int): String {
            val str: String
            when (num) {
                1 -> str = "星期一"
                2 -> str = "星期二"
                3 -> str = "星期三"
                4 -> str = "星期四"
                5 -> str = "星期五"
                6 -> str = "星期六"
                7 -> str = "星期日"

                else -> str = ""
            }
            return str
        }


        fun getDeviceInfo(context: Context): String? {
            try {
                val json = org.json.JSONObject()
                val tm = context
                        .getSystemService(Context.TELEPHONY_SERVICE) as android.telephony.TelephonyManager
                var device_id: String? = null
                if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                    device_id = tm.deviceId
                }
                val mac = getMac(context)

                json.put("mac", mac)
                if (TextUtils.isEmpty(device_id)) {
                    device_id = mac
                }
                if (TextUtils.isEmpty(device_id)) {
                    device_id = android.provider.Settings.Secure.getString(context.contentResolver,
                            android.provider.Settings.Secure.ANDROID_ID)
                }
                json.put("device_id", device_id)
                return json.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        fun getMac(context: Context?): String {
            var mac: String = ""
            if (context == null) {
                return mac
            }
            if (Build.VERSION.SDK_INT < 23) {
                mac = getMacBySystemInterface(context)
            } else {
                mac = getMacByJavaAPI()
                if (TextUtils.isEmpty(mac)) {
                    mac = getMacBySystemInterface(context)
                }
            }
            return mac

        }

        @TargetApi(9)
        private fun getMacByJavaAPI(): String {
            try {
                val interfaces = NetworkInterface.getNetworkInterfaces()
                while (interfaces.hasMoreElements()) {
                    val netInterface = interfaces.nextElement()
                    if ("wlan0" == netInterface.getName() || "eth0" == netInterface.getName()) {
                        val addr = netInterface.getHardwareAddress()
                        if (addr == null || addr!!.size == 0) {
                            return ""
                        }
                        val buf = StringBuilder()
                        for (b in addr!!) {
                            buf.append(String.format("%02X:", b))
                        }
                        if (buf.length > 0) {
                            buf.deleteCharAt(buf.length - 1)
                        }
                        return buf.toString().toLowerCase(Locale.getDefault())
                    }
                }
            } catch (e: Throwable) {
            }

            return ""
        }

        private fun getMacBySystemInterface(context: Context?): String {
            if (context == null) {
                return ""
            }
            try {
                val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                if (checkPermission(context, Manifest.permission.ACCESS_WIFI_STATE)) {
                    val info = wifi.connectionInfo
                    return info.macAddress
                } else {
                    return ""
                }
            } catch (e: Throwable) {
                return ""
            }

        }

        fun checkPermission(context: Context?, permission: String): Boolean {
            var result = false
            if (context == null) {
                return result
            }
            if (Build.VERSION.SDK_INT >= 23) {
                try {
                    val clazz = Class.forName("android.content.Context")
                    val method = clazz.getMethod("checkSelfPermission", String::class.java)
                    val rest = method.invoke(context, permission) as Int
                    result = rest == PackageManager.PERMISSION_GRANTED
                } catch (e: Throwable) {
                    result = false
                }

            } else {
                val pm = context.packageManager
                if (pm.checkPermission(permission, context.packageName) == PackageManager.PERMISSION_GRANTED) {
                    result = true
                }
            }
            return result
        }

    }
}

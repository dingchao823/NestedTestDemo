package com.example.nestedtestdemo.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.WindowManager
import com.example.nestedtestdemo.MyApplication


object ScreenUtil {

    fun getScreenHeight() : Int{
        val wm = (MyApplication.instance().getSystemService(Context.WINDOW_SERVICE)) as WindowManager
        return wm.defaultDisplay?.height ?: -1
    }

    fun getStatusBarHeight() : Int{
        var height = 0
        val resourceId = MyApplication.instance()
            .resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            height = MyApplication.instance()
                .resources.getDimensionPixelSize(resourceId)
        }
        return height
    }

    /**
     * 是否有底部导航栏
     */
    @SuppressLint("PrivateApi")
    fun checkDeviceHasNavigationBar(context: Context) : Boolean{
        var hasNavigationBar = false
        val rs = context.resources
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0){
            hasNavigationBar = rs.getBoolean(id)
        }
        try {
            val systemPropertyClass = Class.forName("android.os.SystemProperties")
            val method = systemPropertyClass.getMethod("get", String::class.java)
            val navVarOverride = method.invoke(systemPropertyClass, "qemu.hw.mainkeys")
            hasNavigationBar = "1" != navVarOverride
        }catch (e : Exception){
            e.printStackTrace()
        }
        return hasNavigationBar
    }

    /**
     * 底部导航栏是否展示
     */
    @SuppressLint("ObsoleteSdkInt")
    fun isNavigationBarShowing(context: Context) : Boolean{
        val haveNavigationBar =
            checkDeviceHasNavigationBar(
                context
            )
        if (!haveNavigationBar){
            return false
        }
        if (Build.VERSION.SDK_INT >= 17){
            val deviceInfo : String
            when{
                Build.BRAND.equals("HUAWEI", true) -> {
                    deviceInfo = "navigationbar_is_min"
                }
                Build.BRAND.equals("XIAOMI", true) -> {
                    deviceInfo = "force_fsg_nav_bar"
                }
                Build.BRAND.equals("VIVO", true) -> {
                    deviceInfo = "navigation_gesture_on"
                }
                Build.BRAND.equals("OPPO", true) -> {
                    deviceInfo = "navigation_gesture_on"
                }
                else -> {
                    deviceInfo = "navigationbar_is_min"
                }
            }
            if (Settings.Global.getInt(context.contentResolver, deviceInfo, 0) == 0) {
                return true
            }
        }
        return false
    }

    fun getActivityHeight(activity: Activity) : Int{
        val isShow =
            isNavigationBarShowing(
                activity
            )
        if (isShow) {
            return getScreenHeight()
        }else{
            return getRealScreenHeight(
                activity
            )
        }
    }

    fun getRealScreenHeight(activity: Activity): Int {
        val outMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getRealMetrics(outMetrics)
        return outMetrics.heightPixels
    }
}
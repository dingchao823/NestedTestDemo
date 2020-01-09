package com.example.nestedtestdemo.utils

import android.content.Context
import android.view.WindowManager
import com.example.nestedtestdemo.MyApplication

object DimenUtils {

    fun getDisplayWidth(context: Context) : Int{
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

    fun dipTopx(context: Context?, dpValue: Float): Int {
        val scale = context?.resources?.displayMetrics?.density ?: 1f
        return (dpValue * scale + 0.5f).toInt()
    }

    fun dipTopx(dpValue: Float): Int {
        val scale = MyApplication.instance().resources?.displayMetrics?.density ?: 1f
        return (dpValue * scale + 0.5f).toInt()
    }

}
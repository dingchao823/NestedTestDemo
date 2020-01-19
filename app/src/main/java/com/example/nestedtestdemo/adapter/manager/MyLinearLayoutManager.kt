package com.example.nestedtestdemo.adapter.manager

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class MyLinearLayoutManager(context: Context?, orientation: Int, reverseLayout: Boolean) :
    LinearLayoutManager(context, orientation, reverseLayout) {

    override fun canScrollHorizontally(): Boolean {
        return false
    }
}
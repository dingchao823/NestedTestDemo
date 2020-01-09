package com.example.nestedtestdemo.config

import androidx.viewpager.widget.ViewPager
import com.example.nestedtestdemo.widget.NestedInnerRecyclerView
import com.example.nestedtestdemo.widget.NestedOuterRecyclerView
import com.example.nestedtestdemo.widget.OverScroller
import com.example.nestedtestdemo.widget.RecyclerTabLayout

object Constant {

    var outerRecyclerView : NestedOuterRecyclerView? = null
    var innerRecyclerView : NestedInnerRecyclerView? = null
    var tabLayout : RecyclerTabLayout? = null
    var viewPager : ViewPager? = null
    var overScroller : OverScroller.SplineOverScroller? = null

}
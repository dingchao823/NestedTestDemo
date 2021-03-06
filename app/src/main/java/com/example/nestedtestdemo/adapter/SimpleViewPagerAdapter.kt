package com.example.nestedtestdemo.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.example.nestedtestdemo.R
import com.example.nestedtestdemo.config.Constant
import com.example.nestedtestdemo.utils.DimenUtils
import com.example.nestedtestdemo.utils.ScreenUtil
import com.example.nestedtestdemo.widget.RecyclerTabLayout

class SimpleViewPagerAdapter(var fm : FragmentManager,
                             val activity : Activity)
    : BaseDelegateAdapter<SimpleViewPagerAdapter.ViewHolder, String>(){

    init {
        for(index in 0..20) {
            newDataSource.add("位置$index")
        }
    }

    override fun onCreateLayoutHelper(): LayoutHelper {
        return LinearLayoutHelper()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = getRootView(p0,
            R.layout.recycler_item_nested_view_pager
        )
        val viewPager = view?.findViewById<ViewPager>(R.id.view_Pager)
        val screenHeight = ScreenUtil.getActivityHeight(activity)
        val statusHeight = ScreenUtil.getStatusBarHeight()
        val tabBarHeight = DimenUtils.dipTopx(48f)
        var viewPagerHeight = screenHeight - statusHeight - tabBarHeight
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, viewPagerHeight)
        viewPager?.layoutParams = params
        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {

    }

    override fun getViewType(position: Int): Int {
        return 23
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val viewPager : ViewPager = itemView.findViewById(R.id.view_Pager)
        val tabLayout : RecyclerTabLayout = itemView.findViewById(R.id.tab_layout)

        init {
            viewPager.offscreenPageLimit = 1
            viewPager.adapter = SimpleFragmentAdapter(fm)

            tabLayout.isNestedScrollingEnabled = true
            tabLayout.setUpWithAdapter(SimpleTabAdapter(activity, viewPager, newDataSource))

            Constant.tabLayout = tabLayout
            Constant.viewPager = viewPager
        }
    }

    override fun getItemCount(): Int {
        return 1
    }
}
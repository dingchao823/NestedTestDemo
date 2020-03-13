package com.example.nestedtestdemo.activity

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.example.nestedtestdemo.R
import com.example.nestedtestdemo.adapter.*
import com.example.nestedtestdemo.utils.ReflectUtil
import com.example.nestedtestdemo.utils.ScreenUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val virtualLayoutManager = VirtualLayoutManager(this)
        val adapter = DelegateAdapter(virtualLayoutManager)
        outer_recyclerview.viewPagerStickyHeight =
            ScreenUtil.getStatusBarHeight()
        outer_recyclerview.layoutManager = VirtualLayoutManager(this)
        outer_recyclerview.adapter = adapter

        adapter.addAdapter(SimpleBannerAdapter(this))

        for (index in 1..8){
            adapter.addAdapter(SimpleImageAdapter())
            adapter.addAdapter(
                SimpleDividerAdapter(
                    10
                )
            )
        }

        adapter.addAdapter(SimpleOneScrollAdapter())
        adapter.addAdapter(
            SimpleDividerAdapter(
                10
            )
        )
        adapter.addAdapter(
            SimpleViewPagerAdapter(
                supportFragmentManager,
                this
            )
        )

        ReflectUtil.reflect(outer_recyclerview)
    }
}

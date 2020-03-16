package com.example.nestedtestdemo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.example.nestedtestdemo.R
import com.example.nestedtestdemo.adapter.*
import com.example.nestedtestdemo.utils.ReflectUtil
import com.example.nestedtestdemo.utils.ScreenUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var fatherAdapter : DelegateAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val virtualLayoutManager = VirtualLayoutManager(this)
        fatherAdapter = DelegateAdapter(virtualLayoutManager)

        // 设置 fling 相关设置
        ReflectUtil.reflect(outer_recyclerview)

        initRecycler()

        refresh.setOnClickListener {
            fatherAdapter?.clear()
            initRecycler()
        }
    }

    private fun initRecycler() {
        outer_recyclerview.viewPagerStickyHeight = ScreenUtil.getStatusBarHeight()
        outer_recyclerview.layoutManager = VirtualLayoutManager(this)
        outer_recyclerview.adapter = fatherAdapter

        // 添加轮播
        fatherAdapter?.addAdapter(SimpleBannerAdapter(this))

        // 添加图片
        for (index in 1..8){
            fatherAdapter?.addAdapter(SimpleImageAdapter())
            fatherAdapter?.addAdapter(SimpleDividerAdapter(16))
        }

        // 添加一行可滑动
        fatherAdapter?.addAdapter(SimpleOneScrollAdapter())
        fatherAdapter?.addAdapter(SimpleDividerAdapter(16))

        // 添加 viewPager
        fatherAdapter?.addAdapter(SimpleViewPagerAdapter(supportFragmentManager, this))
    }
}

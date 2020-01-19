package com.example.nestedtestdemo.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.example.nestedtestdemo.R
import com.example.nestedtestdemo.utils.ScreenUtil
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.BannerViewPager.OnPageClickListener
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.bannerview.constants.IndicatorSlideMode
import com.zhpan.bannerview.constants.IndicatorStyle
import com.zhpan.bannerview.utils.BannerUtils

class SimpleBannerAdapter(context : Context) : BaseDelegateAdapter<SimpleBannerAdapter.ViewHolder, String>(){

    private var isInit = false

    override fun getViewType(position: Int): Int {
        return 90
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item_banner, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!isInit){

            val list = ArrayList<String>()
            for (index in 1..10){
                list.add("sadasdasd")
            }

            holder.banner.setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorGap(BannerUtils.dp2px(4f))
                .setPageMargin(0)
                .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
                .setIndicatorHeight(20)
                .setOnPageClickListener(OnPageClickListener { position: Int ->
                    Log.e("dc", "我点击了 $position")
                })
                .setIndicatorWidth(
                    60,
                    100
                )
                .setHolderCreator {
                    ImageViewHolder()
                }
                .create(list)

            isInit = true
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateLayoutHelper(): LayoutHelper {
        return LinearLayoutHelper()
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val banner : BannerViewPager<String, ImageViewHolder> = itemView.findViewById(R.id.banner_view_pager)
    }

    inner class ImageViewHolder : com.zhpan.bannerview.holder.ViewHolder<String>{

        override fun getLayoutId(): Int {
            return R.layout.recycler_item_banner_inner
        }

        override fun onBind(itemView: View?, data: String?, position: Int, size: Int) {
            itemView?.findViewById<ImageView>(R.id.image_view)?.setImageResource(R.mipmap.comvip_list_banner)
        }

    }

}
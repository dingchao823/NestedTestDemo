package com.example.nestedtestdemo.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.example.nestedtestdemo.adapter.manager.MyLinearLayoutManager
import com.example.nestedtestdemo.utils.DimenUtils

class SimpleOneScrollAdapter : BaseDelegateAdapter<SimpleOneScrollAdapter.ViewHolder, String>(){

    private val adapter =
        SimpleRecyclerAdapter(context)

    override fun getViewType(position: Int): Int {
        return 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.recyclerView.let {
            if (it.adapter == null){
                it.layoutManager = MyLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                it.adapter = adapter
            }
        }

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val recyclerView = RecyclerView(context!!)
        recyclerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DimenUtils.dipTopx(context, 100f))
        return ViewHolder(
            recyclerView
        )
    }

    override fun onCreateLayoutHelper(): LayoutHelper {
        return LinearLayoutHelper()
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val recyclerView = itemView as RecyclerView
    }

    override fun getItemCount(): Int {
        return 1
    }
}
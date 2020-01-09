package com.example.nestedtestdemo.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.nestedtestdemo.utils.DimenUtils


class StaggeredDividerItemDecoration(private val context: Context, private val interval: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        var position = parent.getChildAdapterPosition(view)
        view.layoutParams?.let {
            if (it is StaggeredGridLayoutManager.LayoutParams){
                position = it.spanIndex
            }
        }
        val interval_16 = DimenUtils.dipTopx(16f)
        val interval_11 = DimenUtils.dipTopx(11f)
        val interval_12 = DimenUtils.dipTopx(12f)
        if (position % 2 == 0){
            outRect.left = interval_16
            outRect.right = 0
        }else{
            outRect.left = interval_11
            outRect.right = interval_16
        }
        outRect.bottom = interval_12
    }

    private fun secondWay(){

    }

}
package com.example.nestedtestdemo.utils

import android.content.Context
import android.util.Log
import android.view.ViewConfiguration
import android.view.animation.Interpolator
import androidx.recyclerview.widget.RecyclerView
import com.example.nestedtestdemo.widget.NestedInnerRecyclerView
import com.example.nestedtestdemo.widget.NestedOuterRecyclerView
import com.example.nestedtestdemo.widget.OverScroller
import kotlin.math.abs

/**
 * Fling 事件传递类
 *
 * @author 0004640
 */
class FlingUtils(val context : Context) {

    private var isDebug = true
    private val sQuinticInterpolator = Interpolator { t ->
        var time = t
        time -= 1.0f
        time * time * time* time * time + 1.0f
    }
    private var overScroller =
        OverScroller(
            context,
            sQuinticInterpolator
        )
    private val vc = ViewConfiguration.get(context)
    private val minFlingVelocity = vc.scaledMinimumFlingVelocity

    private var outerRecyclerView : NestedOuterRecyclerView? = null
    private val outerScrollListener = OuterScrollListener()
    private var outerScrollState = RecyclerView.SCROLL_STATE_IDLE
    var outerScrollSumDistance = 0
    var outerIdleScrollSumDistance = 0
    var outerNeedScrollDistance = 0
    var outerNeedFlingVelocity = 0
    var outerLeftScrollDistance = 0
    var outerLeftFlingVelocity  = 0

    private var innerRecyclerView : NestedInnerRecyclerView? = null
    private val innerScrollListener = InnerScrollListener()
    private var innerScrollState = RecyclerView.SCROLL_STATE_IDLE
    var innerScrollSumDistance = 0
    var innerNeedScrollDistance = 0
    var innerNeedFillingVelocity = 0
    var innerLeftScrollDistance = 0
    var innerLeftFlingVelocity = 0

    private var isEnable = true

    fun attach(outerRecyclerView: NestedOuterRecyclerView? = null, innerRecyclerView : NestedInnerRecyclerView? = null){
        if (!isEnable){
            return
        }
        if (this.outerRecyclerView != outerRecyclerView) {
            outerRecyclerView?.let {
                this.outerRecyclerView?.removeOnScrollListener(innerScrollListener)
                this.outerRecyclerView = it
                it.removeOnScrollListener(outerScrollListener)
                it.addOnScrollListener(outerScrollListener)
            }
        }
        if (this.innerRecyclerView != innerRecyclerView) {
            innerRecyclerView?.let {
                this.innerRecyclerView?.removeOnScrollListener(innerScrollListener)
                this.innerRecyclerView = it
                it.removeOnScrollListener(innerScrollListener)
                it.addOnScrollListener(innerScrollListener)
            }
        }
    }

    inner class OuterScrollListener : RecyclerView.OnScrollListener(){

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (outerScrollState == RecyclerView.SCROLL_STATE_SETTLING){
                outerScrollSumDistance += dy
            }
            if (innerScrollState == RecyclerView.SCROLL_STATE_SETTLING
                    && outerScrollState == RecyclerView.SCROLL_STATE_IDLE){
                outerIdleScrollSumDistance += dy
            }else{
                outerIdleScrollSumDistance = 0
            }
            printLog("Outer --> onScrolled, state = ${outerScrollState}, " +
                    "outerScrollSumDistance=$outerScrollSumDistance, " +
                    "outerIdleScrollSumDistance=$outerIdleScrollSumDistance")
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (outerScrollState == RecyclerView.SCROLL_STATE_SETTLING
                    && newState == RecyclerView.SCROLL_STATE_DRAGGING){
                clearOuter()
            }
            printLog("Outer --> onScrollStateChanged, outerScrollState=$outerScrollState")
            if (outerScrollState == RecyclerView.SCROLL_STATE_SETTLING
                    && newState == RecyclerView.SCROLL_STATE_IDLE){
                handleFatherFillingIntoChild()
            }
            outerScrollState = newState
        }

    }

    private fun handleFatherFillingIntoChild() {
        innerRecyclerView?.let { innerRecyclerView ->
            if (outerNeedFlingVelocity > 0 && outerScrollSumDistance > 0) {
                outerNeedScrollDistance = overScroller.mScrollerY.getSplineFlingDistance(outerNeedFlingVelocity).toInt()
                printLog("Outer --> 需要 Fling $outerNeedFlingVelocity 和 滑动 ${outerNeedScrollDistance}，父类实际滑动 $outerScrollSumDistance")
                // 如果父类需要滑动的距离大于当前已经 fling 的距离，证明需要子类去继续 fling
                if (abs(outerNeedScrollDistance) > abs(outerScrollSumDistance)) {
                    outerLeftScrollDistance = abs(outerNeedScrollDistance) - abs(outerScrollSumDistance)
                    outerLeftFlingVelocity = overScroller.mScrollerY.getSplineFlingVelocity(outerLeftScrollDistance.toDouble())
                    printLog("Outer --> 还需要 fling $outerLeftFlingVelocity")
                    // 大于子类允许滑动的最大距离，子类才会动
                    if (outerLeftFlingVelocity >= minFlingVelocity) {
                        innerRecyclerView.fling(0, outerLeftFlingVelocity)
                        printLog("Outer --> 子类 fling 了 $outerLeftFlingVelocity")
                    }
                }
            }else{
                printLog("Outer --> fling 被拒绝了， outerNeedFlingVelocity=$outerNeedFlingVelocity, outerScrollSumDistance = $outerScrollSumDistance")
            }
        }
        clearOuter()
    }

    inner class InnerScrollListener : RecyclerView.OnScrollListener(){

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (innerScrollState == RecyclerView.SCROLL_STATE_SETTLING){
                innerScrollSumDistance += dy
            }
            printLog("Inner --> onScrolled， innerScrollSumDistance=$innerScrollSumDistance")
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (innerScrollState == RecyclerView.SCROLL_STATE_SETTLING
                    && newState == RecyclerView.SCROLL_STATE_DRAGGING){
                clearInner()
            }
            innerScrollState = newState
            printLog("Inner --> onScrollStateChanged， innerScrollState=$innerScrollState")
            if (innerScrollState == RecyclerView.SCROLL_STATE_IDLE){
                handleChildFillingIntoParent()
            }
        }
    }

    private fun handleChildFillingIntoParent(){
        outerRecyclerView?.let { outerRv ->
            if (innerNeedFillingVelocity < 0 && innerScrollSumDistance < 0){
                innerNeedScrollDistance = overScroller.mScrollerY.getSplineFlingDistance(innerNeedFillingVelocity).toInt()
                printLog("Inner --> 需要 fling $innerNeedFillingVelocity, 滑动 ${innerNeedScrollDistance}，子类滑动了 $innerScrollSumDistance")
                if (abs(innerNeedScrollDistance) > abs(innerScrollSumDistance)){
                    val currentVelocity = ReflectUtil.getCurrVelocity()
                    printLog("当前 currentVelocity=$currentVelocity")
                    if (abs(currentVelocity) > minFlingVelocity){
                        outerRv.fling(0, -abs(currentVelocity.toInt()))
                    }
                }
            }else{
                printLog("Inner --> Filing被拒绝了, fling = $innerNeedFillingVelocity, innerScrollSumDistance = $innerScrollSumDistance")
            }
        }
        clearInner()
    }

    private fun clearOuter(){
        outerScrollSumDistance = 0
        outerNeedScrollDistance = 0
        outerNeedFlingVelocity = 0
        outerLeftScrollDistance = 0
        outerLeftFlingVelocity  = 0
        printLog("Outer --> clearOuter()")
    }

    private fun clearInner(){
        innerScrollSumDistance = 0
        innerNeedScrollDistance = 0
        innerNeedFillingVelocity = 0
        innerLeftScrollDistance = 0
        innerLeftFlingVelocity = 0
        printLog("Inner --> clearInner()")
    }

    private fun printLog(content : String) {
        if (isDebug) {
            Log.e("dc", content)
        }
    }

}
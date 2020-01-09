package com.example.nestedtestdemo.utils;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nestedtestdemo.config.Constant;
import com.example.nestedtestdemo.widget.OverScroller;

import java.lang.reflect.Field;

public class ReflectUtil {

    public static void reflect(RecyclerView recyclerView) throws NoSuchFieldException, IllegalAccessException {
        Field field = recyclerView.getClass().getSuperclass().getDeclaredField("mViewFlinger");
        field.setAccessible(true);

        Object viewFlinger = field.get(recyclerView);

        field = viewFlinger.getClass().getDeclaredField("mOverScroller");
        field.setAccessible(true);
        Object overScroller = field.get(viewFlinger);

        field = overScroller.getClass().getDeclaredField("mScrollerY");
        field.setAccessible(true);
        Object mScrollerY = field.get(overScroller);

        if (mScrollerY instanceof OverScroller.SplineOverScroller) {
            Constant.INSTANCE.setOverScroller((OverScroller.SplineOverScroller) mScrollerY);
        }
    }

    public static float getCurrVelocity() throws NoSuchFieldException, IllegalAccessException {

        Field field = Constant.INSTANCE.getOverScroller().getClass().getDeclaredField("mCurrVelocity");
        field.setAccessible(true);
        Object mCurrVelocity = field.get(Constant.INSTANCE.getOverScroller());

        if (mCurrVelocity instanceof Float){
            return (float) mCurrVelocity;
        }
        return -1f;
    }

}

package com.example.nestedtestdemo.utils;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AopImpl {

    @Pointcut("execution(* androidx.recyclerview.widget.StaggeredGridLayoutManager.recycleFromStart(..))")
    public void pointCutStaggerLayoutManagerRecycleFromStart(JoinPoint joinPoint){
        try{
            Log.e("dc", "[pointCut] StaggerLayoutManagerRecycleFromStart **************");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Around("pointCutStaggerLayoutManagerRecycleFromStart()")
    public void aroundStaggerLayoutManagerRecycleFromStart(ProceedingJoinPoint joinPoint){
        try{
            joinPoint.proceed();
        } catch (Throwable e){
            e.printStackTrace();
        }
    }

}

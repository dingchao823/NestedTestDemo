package com.example.nestedtestdemo

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    companion object {

        private var instance: Application? = null

        fun instance() = instance!!

    }
}
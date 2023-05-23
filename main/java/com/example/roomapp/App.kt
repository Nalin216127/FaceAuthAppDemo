package com.example.roomapp

import android.app.Application
import android.content.Context

open class App: Application()
{
    companion object {
        lateinit var instance: App
            private set

//        val appContext: Context by lazy {
//            instance.applicationContext
//        }
    }

}
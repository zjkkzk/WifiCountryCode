package io.github.mirukurusan.wificountrycode

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * 初始化并提供一个可以全局访问的Context
 */
class ContextAmbient: Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var current: Context
    }

    override fun onCreate() {
        super.onCreate()
        // applicationContext返回应用的ApplicationContext，与生命周期绑定
        current = applicationContext
    }
}
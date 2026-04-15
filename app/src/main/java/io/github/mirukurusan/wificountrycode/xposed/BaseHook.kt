package io.github.mirukurusan.wificountrycode.xposed

abstract class BaseHook {
    abstract fun init()
    abstract val name: String
    var isInit: Boolean = false
}
package io.github.mirukurusan.wificountrycode.config

import android.util.Log
import io.github.mirukurusan.wificountrycode.data.Configuration

internal object LocalHookConfig {
    const val TAG = "LocalHookConfig"

    lateinit var config: Configuration
        private set

    fun loadConfig() {
        Log.d(TAG, "Loading config")
        config = HookConfig.XPOSED.get()
        Log.d(TAG, "Config loaded: $config")
    }
}

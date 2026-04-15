package io.github.mirukurusan.wificountrycode.config

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import de.robv.android.xposed.XSharedPreferences
import io.github.mirukurusan.wificountrycode.BuildConfig
import io.github.mirukurusan.wificountrycode.ContextAmbient
import io.github.mirukurusan.wificountrycode.data.Configuration

/**
 * Hook configuration storage manager
 */
object HookConfig {
    const val TAG = "HookConfig"
    private const val PREF_NAME = "wifi_country_code_config"
    private const val KEY_CONFIG = "config"

    // Used in Xposed module environment (during hook process) to read configuration
    object XPOSED {
        private val xSharedPref by lazy {
            XSharedPreferences(
                BuildConfig.APPLICATION_ID,
                PREF_NAME
            )
        }

        fun get(): Configuration {
            Log.d(TAG, "Reading config from XSharedPreferences")
            val config = xSharedPref.getString(KEY_CONFIG, "")!!
            return try {
                Configuration.fromJSON(config)
            } catch (e: Exception) {
                Log.d(TAG, "Failed to read config: $config, error: $e")
                Configuration.default
            }
        }
    }

    // Used in module app to read and edit configuration
    @SuppressLint("WorldReadableFiles")
    object APP {
        private val sharedPref by lazy {
            try {
                ContextAmbient.current.getSharedPreferences(
                    PREF_NAME,
                    Context.MODE_WORLD_READABLE
                )
            } catch (e: Exception) {
                ContextAmbient.current.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            }
        }

        var isModuleActive = false

        fun get(): Configuration {
            val config = sharedPref.getString(KEY_CONFIG, "")!!
            return try {
                Configuration.fromJSON(config)
            } catch (e: Exception) {
                Configuration.default
            }
        }

        fun save(config: Configuration) {
            sharedPref.edit().putString(KEY_CONFIG, config.toJSON()).apply()
            Log.d(TAG, "Config saved: ${config.toJSON()}")
        }
    }
}

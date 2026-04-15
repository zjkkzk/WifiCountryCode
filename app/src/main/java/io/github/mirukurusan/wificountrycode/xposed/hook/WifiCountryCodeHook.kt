package io.github.mirukurusan.wificountrycode.xposed.hook

import android.annotation.SuppressLint
import android.util.Log
import io.github.kyuubiran.ezxhelper.core.finder.MethodFinder
import io.github.kyuubiran.ezxhelper.core.finder.MethodFinder.`-Static`.methodFinder
import io.github.kyuubiran.ezxhelper.xposed.dsl.HookFactory.`-Static`.createHook
import io.github.mirukurusan.wificountrycode.config.LocalHookConfig
import io.github.mirukurusan.wificountrycode.xposed.BaseHook

object WifiCountryCodeHook : BaseHook() {

    override val name: String = "WifiCountryCodeHook"

    @SuppressLint("PrivateApi")
    override fun init() {
        // Load configuration
        LocalHookConfig.loadConfig()
        
        val config = LocalHookConfig.config
        
        if (!config.enabled) {
            Log.i(name, "Hook disabled by config")
            return
        }

        val countryCode = config.countryCode
        Log.i(name, "Hook enabled, country code: $countryCode")

        MethodFinder.fromClass("com.android.server.SystemServiceManager")
            .filterByName("loadClassFromLoader")
            .filterByParamTypes(String::class.java, ClassLoader::class.java).first()
            .createHook {
                before { param ->
                    val clzName = param.args[0] as String
                    val cl = param.args[1] as ClassLoader
                    if (clzName == "com.android.server.wifi.WifiService") {
                        Log.i(name, "Hook WifiService")
                        cl.loadClass("com.android.server.wifi.WifiCountryCode").run {
                            methodFinder().filterByName("setTelephonyCountryCode")
                                .filterByParamTypes(String::class.java).first()
                                .createHook {
                                    before { param ->
                                        Log.i(name, "Hook setTelephonyCountryCode")
                                        param.args[0] = countryCode
                                    }
                                }
                            arrayOf(
                                "pickCountryCode",
                                "getCountryCode",
                                "getCurrentDriverCountryCode"
                            ).forEach { methodName ->
                                methodFinder().filterByName(methodName).first()
                                    .createHook {
                                        before { param ->
                                            Log.i(name, "Hook $methodName")
                                            param.result = countryCode
                                            return@before
                                        }
                                    }
                            }
                        }
                    }
                }
            }
    }
}

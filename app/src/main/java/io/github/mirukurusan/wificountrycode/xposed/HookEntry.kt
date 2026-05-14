package io.github.mirukurusan.wificountrycode.xposed

import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.kyuubiran.ezxhelper.core.finder.MethodFinder
import io.github.kyuubiran.ezxhelper.xposed.EzXposed
import io.github.kyuubiran.ezxhelper.xposed.dsl.HookFactory.`-Static`.createHook
import io.github.mirukurusan.wificountrycode.BuildConfig
import io.github.mirukurusan.wificountrycode.xposed.hook.WifiCountryCodeHook

class HookEntry : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam == null) return

        // Self-hook: when loading the module's own app process,
        // hook isModuleActive() to return true so the UI can detect activation
        if (lpparam.packageName == BuildConfig.APPLICATION_ID) {
            try {
                MethodFinder.fromClass(
                    "io.github.mirukurusan.wificountrycode.config.HookConfig\$APP",
                    lpparam.classLoader
                ).filterByName("isModuleActive").first().createHook {
                    after { param ->
                        param.result = true
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to self-hook isModuleActive", e)
            }
            return
        }

        EzXposed.initHandleLoadPackage(lpparam)
        initHooks(
            WifiCountryCodeHook
        )
    }

    fun initHooks(vararg hooks: BaseHook) {
        for (h in hooks) {
            try {
                if (h.isInit) continue
                h.init()
                h.isInit = true
            } catch (e: Exception) {
                Log.e("HookEntry", "Failed to initialize hook: ${h.name}", e)
            }
        }
    }

    companion object {
        private const val TAG = "HookEntry"
    }
}
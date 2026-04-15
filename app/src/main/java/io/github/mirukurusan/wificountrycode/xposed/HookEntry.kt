package io.github.mirukurusan.wificountrycode.xposed

import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.kyuubiran.ezxhelper.xposed.EzXposed
import io.github.mirukurusan.wificountrycode.config.HookConfig
import io.github.mirukurusan.wificountrycode.xposed.hook.WifiCountryCodeHook

class HookEntry : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        HookConfig.APP.isModuleActive = true
        EzXposed.initHandleLoadPackage(lpparam!!)
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
}
package io.github.mirukurusan.wificountrycode.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import io.github.mirukurusan.wificountrycode.config.HookConfig
import io.github.mirukurusan.wificountrycode.data.Configuration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConfigViewModel : ViewModel() {
    
    val configState = State.default

    // Load configuration
    fun loadConfig() {
        viewModelScope.launch(Dispatchers.IO) {
            val config = HookConfig.APP.get()
            configState.toState(config)
        }
    }

    // Save configuration
    fun saveConfig() {
        viewModelScope.launch(Dispatchers.IO) {
            val configuration = Configuration(configState)
            HookConfig.APP.save(configuration)
        }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ConfigViewModel() as T
            }
        }
    }

    class State {
        val enabled = mutableStateOf(false)
        val countryCode = mutableStateOf(Configuration.DEFAULT_COUNTRY_CODE)

        fun toState(config: Configuration) {
            enabled.value = config.enabled
            countryCode.value = config.countryCode
        }

        companion object {
            val default = State()
        }
    }
}

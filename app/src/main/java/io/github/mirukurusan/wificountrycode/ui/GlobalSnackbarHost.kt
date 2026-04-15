package io.github.mirukurusan.wificountrycode.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Suppress("unused")
object GlobalSnackbarHost {

    internal val state by derivedStateOf { SnackbarHostState() }

    internal val onError by derivedStateOf { mutableStateOf(false) }

    @JvmStatic
    fun show(
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration =
            if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
        focusManager: FocusManager,
        scope: CoroutineScope
    ) {
        if (onError.value) onError.value = false
        focusManager.clearFocus()
        scope.launch { state.showSnackbar(message, actionLabel, withDismissAction, duration) }
    }

    @JvmStatic
    fun showError(
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration =
            if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
        focusManager: FocusManager,
        scope: CoroutineScope
    ) {
        onError.value = true
        focusManager.clearFocus()
        scope.launch { state.showSnackbar(message, actionLabel, withDismissAction, duration) }
    }

    @JvmStatic
    fun showAndDismissPrevious(
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration =
            if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
        focusManager: FocusManager,
        scope: CoroutineScope
    ) {
        scope.launch {
            if (state.currentSnackbarData != null) {
                state.currentSnackbarData?.dismiss()
                delay(200)
            }
            show(message, actionLabel, withDismissAction, duration, focusManager, scope)
        }
    }

    @JvmStatic
    fun showErrorAndDismissPrevious(
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration =
            if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
        focusManager: FocusManager,
        scope: CoroutineScope
    ) {
        scope.launch {
            if (state.currentSnackbarData != null) {
                state.currentSnackbarData?.dismiss()
                delay(200) // 使用 delay 替代 Thread.sleep
            }
            showError(message, actionLabel, withDismissAction, duration, focusManager, scope)
        }
    }

    @JvmStatic
    fun showSuccess(focusManager: FocusManager, scope: CoroutineScope) {
        showAndDismissPrevious(
            message = "Success",
            withDismissAction = true,
            duration = SnackbarDuration.Short,
            focusManager = focusManager,
            scope = scope
        )
    }
}
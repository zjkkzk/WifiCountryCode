package io.github.mirukurusan.wificountrycode.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.github.mirukurusan.wificountrycode.ui.route.screen.LauncherScreen

object LocalNavController {
    private class Builder(val navHost: NavHostController)

    private lateinit var instance: Builder

    val current: NavHostController get() = instance.navHost

    @Throws(IllegalStateException::class, IllegalStateException::class)
    fun setup(navHost: NavHostController) {
        if (!::instance.isInitialized) {
            instance = Builder(navHost)
        }
    }
}

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    LocalNavController.setup(navController)
    
    // Main screen with bottom navigation handles all navigation internally
    LauncherScreen()
}

@Composable
@Preview
private fun NavigationHostPreview() {
    NavigationHost()
}

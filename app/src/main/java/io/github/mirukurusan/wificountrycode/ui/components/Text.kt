package io.github.mirukurusan.wificountrycode.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
fun SmallTitle(label: @Composable () -> String) {
    Text(
        text = label(),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface.copy(0.6F),
        modifier = Modifier.padding(top = 16.dp, bottom = 1.dp)
    )
}

@Composable
fun TextContent(
    fontFamily: FontFamily = FontFamily.SansSerif,
    onClick: () -> Unit = {},
    content: @Composable () -> String
) {
    Text(
        text = content(),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface.copy(0.9F),
        fontFamily = fontFamily,
        modifier = Modifier.clickable { onClick() }
    )
}
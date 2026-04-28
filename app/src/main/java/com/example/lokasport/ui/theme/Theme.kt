package com.example.lokasport.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = SageGreen,
    secondary = Terracotta,
    background = CreamBg,
    surface = WhiteSurface,
    onPrimary = Color.White,
    onBackground = DarkOlive,
    onSurface = DarkOlive
)

private val DarkColorScheme = darkColorScheme(
    primary = SageGreen,
    secondary = Terracotta,
    background = DarkOlive,
    surface = Color(0xFF2D3748),
    onPrimary = Color.White,
    onBackground = CreamBg,
    onSurface = CreamBg
)

@Composable
fun LokaSportTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
package com.lukakordzaia.core_compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = _FFFFFF,
    secondary = _5A67CE,
    onSecondary = _FFFFFF,
    background = _F1F1F5,
    onPrimary = _1F1F1F
)

private val LightColorPalette = lightColors(
    primary = _FFFFFF,
    secondary = _5A67CE,
    onSecondary = _FFFFFF,
    background = _F1F1F5,
    onPrimary = _1F1F1F

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun SubscriptionManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = LightColorPalette

    rememberSystemUiController().apply {
        setStatusBarColor(color = _F1F1F5)
        setNavigationBarColor(color = _FFFFFF)
        statusBarDarkContentEnabled = true
        navigationBarDarkContentEnabled = true
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun LoginTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = LightColorPalette

    rememberSystemUiController().apply {
        setStatusBarColor(color = _5A67CE)
        setNavigationBarColor(color = _5A67CE)
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
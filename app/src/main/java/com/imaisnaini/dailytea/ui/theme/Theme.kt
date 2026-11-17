package com.imaisnaini.dailytea.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*

private val DarkColorPalette = darkColors(
    primary = PrimaryBlue,
    onPrimary = TextOnPrimary,
    primaryVariant = BrandBlue,
    secondary = BrandYellow,
    secondaryVariant = SoftYellow,
    onSecondary = TextOnYellow,
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    error = ErrorRed
)

private val LightColorPalette = lightColors(
    primary = PrimaryBlue,
    onPrimary = TextOnPrimary,
    primaryVariant = BrandBlue,
    secondary = BrandYellow,
    secondaryVariant = SoftYellow,
    onSecondary = TextOnYellow,
    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceLight,
    onSurface = TextPrimary,
    error = ErrorRed
)

@Composable
fun MyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content
    )
}
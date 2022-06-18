package com.example.rememberme.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.rememberme.ui.theme.Shapes

val light = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryVariantColor,
    secondary = SecondaryColorForLight,
    secondaryVariant = SecondaryColorForLight,
    background = BackgroundColorForLight,
    surface = SurfaceColorForLight,
    error = ErrorColor,
    onPrimary = WhiteColor,
    onSecondary = WhiteColor,
    onBackground = BlackColor,
    onSurface = BlackColor,
    onError = ErrorColor
)
val dark = darkColors(
    background = BackgroundColorForDark,
    surface = SurfaceColorForDark,
    primary = PrimaryColor,
    secondary = SecondaryColorForDark,
    onBackground = OnBackgroundColor,
    onSurface = WhiteColor,
    onPrimary = WhiteColor,
    onSecondary = WhiteColor,
    primaryVariant = PrimaryVariantColor,
    secondaryVariant = SecondaryColorForDark,
    onError = ErrorColor,
    error = ErrorColor,
)
@Composable
fun JetPackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) dark else light,
       // typography = Typography,
        shapes = Shapes,
        content = content
    )
}


package com.lans.sleep_care.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColor = darkColorScheme(
    primary = Primary,
    onPrimary = White,
    primaryContainer = PrimaryVariant,
    onPrimaryContainer = White,

    secondary = Secondary,
    onSecondary = Black,
    secondaryContainer = SecondaryVariant,
    onSecondaryContainer = Black,

    background = SecondaryVariant,
    onBackground = Black,

    surface = White,
    onSurface = Black,

    surfaceVariant = White,
    onSurfaceVariant = Black,

    error = Danger,
    onError = White,

    outline = DarkGray
)

private val lightColor = lightColorScheme(
    primary = Primary,
    onPrimary = White,
    primaryContainer = PrimaryVariant,

    secondary = Secondary,
    onSecondary = Black,
    secondaryContainer = SecondaryVariant,

    background = SecondaryVariant,
    onBackground = Black,

    surface = White,
    onSurface = Black,

    error = Danger,
    onError = White,

    outline = DarkGray
)

@Composable
fun SleepCareTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColor
        else -> lightColor
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = if (darkTheme) {
                Black.toArgb()
            } else {
                White.toArgb()
            }
            window.navigationBarColor = if (darkTheme) {
                Black.toArgb()
            } else {
                White.toArgb()
            }
            val wic = WindowCompat.getInsetsController(window, view)
            wic.isAppearanceLightStatusBars = !darkTheme
            wic.isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
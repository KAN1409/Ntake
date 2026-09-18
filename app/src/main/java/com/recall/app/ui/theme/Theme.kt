package com.recall.app.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val MemoryColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = SecondaryLight,
    onSecondary = DarkBackground,
    secondaryContainer = SecondaryDark,
    onSecondaryContainer = DarkOnSurface,
    tertiary = AccentTeal,
    onTertiary = DarkBackground,
    tertiaryContainer = Color(0xFF17312D),
    onTertiaryContainer = Color(0xFFD4E9E3),
    error = ErrorRed,
    onError = Color.White,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = OutlineDark,
    outlineVariant = Color(0xFF343A40)
)

@Composable
fun RecallTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DarkBackground.toArgb()
            window.navigationBarColor = DarkBackground.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
            }
        }
    }
    MaterialTheme(colorScheme = MemoryColorScheme, typography = Typography, content = content)
}

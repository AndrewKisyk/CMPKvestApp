package ui.theme.color

import androidx.compose.material3.lightColorScheme

internal fun lightColorScheme(): AppColors = AppColors(
    ripple = AppColorPalette.sand,
    splash = AppColorPalette.blue,
    materialColors = lightColorScheme(
        primary = AppColorPalette.brown,
        error = AppColorPalette.red,
    ),
)

package ui.theme.color

import androidx.compose.material3.darkColorScheme

internal fun darkColorScheme(): AppColors = AppColors(
    ripple = AppColorPalette.sand,
    splash = AppColorPalette.blue,
    materialColors = darkColorScheme(
        primary = AppColorPalette.brown,
        error = AppColorPalette.red,
    ),
)

package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = CyanNeon,
    onPrimary = CyanNeonDark,
    primaryContainer = Color(0xFF004D40),
    onPrimaryContainer = CyanNeon,
    secondary = ElectricBlue,
    onSecondary = ElectricBlueDark,
    secondaryContainer = Color(0xFF0C2A59),
    onSecondaryContainer = Color(0xFFB8D0FF),
    tertiary = AmberGold,
    onTertiary = AmberGoldDark,
    tertiaryContainer = Color(0xFF4A3400),
    onTertiaryContainer = Color(0xFFFFDF9E),
    background = TechDarkBg,
    onBackground = TextPrimary,
    surface = TechSurface,
    onSurface = TextPrimary,
    surfaceVariant = TechSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    surfaceContainer = TechSurfaceCard,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    error = CoralNeon,
    onError = Color.White
  )

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = DarkColorScheme,
    typography = Typography,
    content = content
  )
}

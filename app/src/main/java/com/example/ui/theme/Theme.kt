package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val SensiDarkColorScheme =
  darkColorScheme(
    primary = SensiRed,
    onPrimary = TextWhite,
    primaryContainer = SensiRedDark,
    onPrimaryContainer = TextWhite,
    secondary = SensiRedLight,
    onSecondary = DarkBg,
    secondaryContainer = DarkSurfaceHighlight,
    onSecondaryContainer = TextWhite,
    tertiary = AccentYellow,
    onTertiary = DarkBg,
    background = DarkBg,
    onBackground = TextWhite,
    surface = DarkSurface,
    onSurface = TextWhite,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = TextMuted,
    outline = DarkBorder,
    outlineVariant = DarkBorderLight,
  )

private val SensiLightColorScheme =
  lightColorScheme(
    primary = SensiRed,
    onPrimary = TextWhite,
    primaryContainer = SensiRedLight,
    onPrimaryContainer = WhiteBg,
    secondary = SensiRedDark,
    onSecondary = TextWhite,
    background = WhiteBg,
    onBackground = WhiteTextPrimary,
    surface = WhiteSurface,
    onSurface = WhiteTextPrimary,
    surfaceVariant = WhiteSurfaceElevated,
    onSurfaceVariant = WhiteTextMuted,
    outline = WhiteBorder,
    outlineVariant = WhiteBorderLight,
  )

@Composable
fun MyApplicationTheme(
  isWhiteTheme: Boolean = true, // Default to White FF Sensi Bot theme as requested
  content: @Composable () -> Unit,
) {
  val palette = if (isWhiteTheme) WhiteSensiPalette else DarkSensiPalette
  val colorScheme = if (isWhiteTheme) SensiLightColorScheme else SensiDarkColorScheme

  CompositionLocalProvider(LocalSensiColors provides palette) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
  }
}



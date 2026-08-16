package com.example.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

// Primary Red Accents (Matching Reference & White FF Sensi Bot APK)
val SensiRed = Color(0xFFD81A24)
val SensiRedDark = Color(0xFFB3121B)
val SensiRedGlow = Color(0x33D81A24)
val SensiRedLight = Color(0xFFFF3845)

// Dark Theme Colors
val DarkBg = Color(0xFF000000)
val DarkSurface = Color(0xFF08080B)
val DarkSurfaceElevated = Color(0xFF0F0F14)
val DarkSurfaceHighlight = Color(0xFF181820)
val DarkBorder = Color(0xFF1F1F2A)
val DarkBorderLight = Color(0xFF2E2E3C)

// White Theme Colors (Full White FF Sensi Bot Mode)
val WhiteBg = Color(0xFFF4F6FA)
val WhiteSurface = Color(0xFFFFFFFF)
val WhiteSurfaceElevated = Color(0xFFFFFFFF)
val WhiteSurfaceHighlight = Color(0xFFEAEFF8)
val WhiteBorder = Color(0xFFE2E8F0)
val WhiteBorderLight = Color(0xFFCBD5E1)
val WhiteTextPrimary = Color(0xFF0F172A)
val WhiteTextMuted = Color(0xFF64748B)

// Default Text Colors
val TextWhite = Color(0xFFFFFFFF)
val TextMuted = Color(0xFF9E9EB0)
val TextSubtle = Color(0xFF656578)
val TextSuccess = Color(0xFF22C55E)
val TextWarning = Color(0xFFF59E0B)

// Card & Component Accents
val AccentGreen = Color(0xFF10B981)
val AccentBlue = Color(0xFF3B82F6)
val AccentYellow = Color(0xFFEAB308)

data class SensiColors(
  val isWhite: Boolean,
  val bg: Color,
  val surface: Color,
  val surfaceElevated: Color,
  val surfaceHighlight: Color,
  val border: Color,
  val borderLight: Color,
  val textPrimary: Color,
  val textMuted: Color,
  val primaryRed: Color,
  val primaryRedDark: Color,
  val primaryRedGlow: Color
)

val DarkSensiPalette = SensiColors(
  isWhite = false,
  bg = DarkBg,
  surface = DarkSurface,
  surfaceElevated = DarkSurfaceElevated,
  surfaceHighlight = DarkSurfaceHighlight,
  border = DarkBorder,
  borderLight = DarkBorderLight,
  textPrimary = TextWhite,
  textMuted = TextMuted,
  primaryRed = SensiRed,
  primaryRedDark = SensiRedDark,
  primaryRedGlow = SensiRedGlow
)

val WhiteSensiPalette = SensiColors(
  isWhite = true,
  bg = WhiteBg,
  surface = WhiteSurface,
  surfaceElevated = WhiteSurfaceElevated,
  surfaceHighlight = WhiteSurfaceHighlight,
  border = WhiteBorder,
  borderLight = WhiteBorderLight,
  textPrimary = WhiteTextPrimary,
  textMuted = WhiteTextMuted,
  primaryRed = SensiRed,
  primaryRedDark = SensiRedDark,
  primaryRedGlow = Color(0x22D81A24)
)

val LocalSensiColors = compositionLocalOf { WhiteSensiPalette }

object SensiTheme {
  val colors: SensiColors
    @Composable
    @ReadOnlyComposable
    get() = LocalSensiColors.current
}



package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val LocalPremiumThemeKey = staticCompositionLocalOf { "" }

private val DarkColorScheme =
  darkColorScheme(primary = Purple80, secondary = PurpleGrey80, tertiary = Pink80)

private val LightColorScheme =
  lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
  )

private val OceanBlueColorScheme = lightColorScheme(
  primary = Color(0xFF0D47A1),
  onPrimary = Color.White,
  primaryContainer = Color(0xFFBBDEFB),
  onPrimaryContainer = Color(0xFF001D36),
  secondary = Color(0xFFFF6F00),
  onSecondary = Color.White,
  secondaryContainer = Color(0xFFFFF3E0),
  onSecondaryContainer = Color(0xFF3E1F00),
  background = Color(0xFFF0F4FA),
  surface = Color(0xFFFFFFFF),
  onBackground = Color(0xFF0D1B2A),
  onSurface = Color(0xFF0D1B2A)
)

private val RubyRoseColorScheme = lightColorScheme(
  primary = Color(0xFFC2185B),
  onPrimary = Color.White,
  primaryContainer = Color(0xFFF8BBD0),
  onPrimaryContainer = Color(0xFF3E0020),
  secondary = Color(0xFF7B1FA2),
  onSecondary = Color.White,
  secondaryContainer = Color(0xFFE1BEE7),
  onSecondaryContainer = Color(0xFF2E004E),
  background = Color(0xFFFEF1F5),
  surface = Color(0xFFFFFFFF),
  onBackground = Color(0xFF1A0D14),
  onSurface = Color(0xFF1A0D14)
)

private val EmeraldColorScheme = lightColorScheme(
  primary = Color(0xFF004D40),
  onPrimary = Color.White,
  primaryContainer = Color(0xFFB2DFDB),
  onPrimaryContainer = Color(0xFF00201A),
  secondary = Color(0xFFE65100),
  onSecondary = Color.White,
  secondaryContainer = Color(0xFFFBE9E7),
  onSecondaryContainer = Color(0xFF2D0B00),
  background = Color(0xFFF0F7F5),
  surface = Color(0xFFFFFFFF),
  onBackground = Color(0xFF00231A),
  onSurface = Color(0xFF00231A)
)

private val RoyalVioletColorScheme = lightColorScheme(
  primary = Color(0xFF4A148C),
  onPrimary = Color.White,
  primaryContainer = Color(0xFFE1BEE7),
  onPrimaryContainer = Color(0xFF200038),
  secondary = Color(0xFFFFC107),
  onSecondary = Color(0xFF1F1400),
  secondaryContainer = Color(0xFFFFF8E1),
  onSecondaryContainer = Color(0xFF2C1B00),
  background = Color(0xFFF7F0FA),
  surface = Color(0xFFFFFFFF),
  onBackground = Color(0xFF1C1020),
  onSurface = Color(0xFF1C1020)
)

private val GalacticColorScheme = darkColorScheme(
  primary = Color(0xFF6C63FF),
  onPrimary = Color(0xFFF0EDFF),
  primaryContainer = Color(0xFF1A1535),
  onPrimaryContainer = Color(0xFFD4CFFF),
  secondary = Color(0xFFFF6B9D),
  onSecondary = Color(0xFF1A0011),
  secondaryContainer = Color(0xFF2D1025),
  onSecondaryContainer = Color(0xFFFFD9E6),
  tertiary = Color(0xFF00D4AA),
  onTertiary = Color(0xFF00301F),
  tertiaryContainer = Color(0xFF0A2A20),
  onTertiaryContainer = Color(0xFFA0F5D8),
  background = Color(0xFF0B0D1A),
  onBackground = Color(0xFFE8E0FF),
  surface = Color(0xFF12152B),
  onSurface = Color(0xFFE8E0FF),
  surfaceVariant = Color(0xFF1C1F3A),
  onSurfaceVariant = Color(0xFFC8C0E0),
  error = Color(0xFFFF5252),
  onError = Color.White,
  outline = Color(0xFF3A3560),
  outlineVariant = Color(0xFF2A2548)
)

private fun isPremiumTheme(themeState: String): Boolean {
  return themeState == "blue" || themeState == "pink" || themeState == "green" || themeState == "purple" || themeState == "galactic"
}

@Composable
fun MyApplicationTheme(
  themeState: String = "system",
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit,
) {
  val isDark = when (themeState) {
    "dark", "galactic" -> true
    "light" -> false
    "blue", "pink", "green", "purple" -> false
    else -> darkTheme
  }

  val colorScheme =
    when (themeState) {
      "blue" -> OceanBlueColorScheme
      "pink" -> RubyRoseColorScheme
      "green" -> EmeraldColorScheme
      "purple" -> RoyalVioletColorScheme
      "galactic" -> GalacticColorScheme
      "dark" -> DarkColorScheme
      "light" -> LightColorScheme
      else -> {
        if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
          val context = LocalContext.current
          if (isDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        } else {
          if (isDark) DarkColorScheme else LightColorScheme
        }
      }
    }

  val isPremium = isPremiumTheme(themeState)
  val typography = when {
    themeState == "galactic" -> GalacticTypography
    isPremium -> PremiumTypography
    else -> Typography
  }
  val premiumKey = if (isPremium) themeState else ""

  CompositionLocalProvider(LocalPremiumThemeKey provides premiumKey) {
    MaterialTheme(colorScheme = colorScheme, typography = typography, content = content)
  }
}

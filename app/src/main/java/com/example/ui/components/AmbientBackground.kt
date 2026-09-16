package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import com.example.ui.theme.LocalPremiumThemeKey
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun AmbientBackground(
    isDarkTheme: Boolean = MaterialTheme.colorScheme.background.luminance() < 0.5f,
    content: @Composable () -> Unit
) {
    val premiumKey = LocalPremiumThemeKey.current

    if (premiumKey.isNotEmpty()) {
        PremiumGradientBackground(themeKey = premiumKey, content = content)
        return
    }

    var hour by remember { mutableStateOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) }

    LaunchedEffect(Unit) {
        while (true) {
            hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            delay(30000)
        }
    }

    val colors = getAmbientColors(hour, isDarkTheme)
    
    val colorStart by animateColorAsState(
        targetValue = colors.first,
        animationSpec = tween(1500),
        label = "BgStartAnimate"
    )
    val colorEnd by animateColorAsState(
        targetValue = colors.second,
        animationSpec = tween(1500),
        label = "BgEndAnimate"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(colorStart, colorEnd)
                )
            )
    ) {
        content()
    }
}

private fun getAmbientColors(hour: Int, isDark: Boolean): Pair<Color, Color> {
    return when (hour) {
        in 6..11 -> {
            if (isDark) {
                Pair(Color(0xFF332015), Color(0xFF1E130D))
            } else {
                Pair(Color(0xFFFFE6D5), Color(0xFFFFF9F5))
            }
        }
        in 12..18 -> {
            if (isDark) {
                Pair(Color(0xFF12223A), Color(0xFF09121F))
            } else {
                Pair(Color(0xFFE1F5FE), Color(0xFFF3F9FC))
            }
        }
        in 19..23 -> {
            if (isDark) {
                Pair(Color(0xFF1E112D), Color(0xFF0E0817))
            } else {
                Pair(Color(0xFFF3E8FB), Color(0xFFF9F5FD))
            }
        }
        else -> {
            if (isDark) {
                Pair(Color(0xFF090A0D), Color(0xFF13151D))
            } else {
                Pair(Color(0xFFECEFF1), Color(0xFFF5F7F8))
            }
        }
    }
}

package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun PremiumGradientBackground(
    themeKey: String,
    content: @Composable () -> Unit
) {
    if (themeKey == "galactic") {
        GalacticBackground(content = content)
        return
    }

    val colors = when (themeKey) {
        "blue" -> listOf(Color(0xFFE3EDF7), Color(0xFFD4E4F7))
        "pink" -> listOf(Color(0xFFFCE4EC), Color(0xFFF3E5F5))
        "green" -> listOf(Color(0xFFE0F2F1), Color(0xFFE8F5E9))
        "purple" -> listOf(Color(0xFFEDE7F6), Color(0xFFF3E5F5))
        else -> listOf(Color.Transparent, Color.Transparent)
    }

    val infiniteTransition = rememberInfiniteTransition(label = "gradient")
    val angleOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gradientAngle"
    )

    val startX = 0.3f + angleOffset * 0.4f
    val endX = 0.7f - angleOffset * 0.4f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = colors,
                    start = Offset(startX * 1000f, 0f),
                    end = Offset(endX * 1000f, 1000f)
                )
            )
    ) {
        content()
    }
}

private data class StarData(
    val xFraction: Float,
    val yFraction: Float,
    val radius: Float,
    val phaseOffset: Float,
    val speed: Float,
    val baseAlpha: Float
)

@Composable
private fun GalacticBackground(
    content: @Composable () -> Unit
) {
    // Generate stars once and remember them
    val stars = remember {
        List(80) {
            StarData(
                xFraction = Random.nextFloat(),
                yFraction = Random.nextFloat(),
                radius = Random.nextFloat() * 1.8f + 0.3f,
                phaseOffset = Random.nextFloat() * 6.2832f,
                speed = Random.nextFloat() * 0.6f + 0.4f,
                baseAlpha = Random.nextFloat() * 0.4f + 0.3f
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "galactic")

    // Slow breathing animation for stars
    val twinklePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.2832f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "twinkle"
    )

    // Subtle nebula drift
    val nebulaDrift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "nebulaDrift"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Base deep space gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0B0D1A),
                            Color(0xFF0E0F24),
                            Color(0xFF110E28),
                            Color(0xFF0D0B1E)
                        )
                    )
                )
        )

        // Stars and nebula canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Nebula glow - top right (purple)
            val nebulaOffsetX1 = w * (0.65f + nebulaDrift * 0.08f)
            val nebulaOffsetY1 = h * (0.15f + nebulaDrift * 0.05f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x186C63FF),
                        Color(0x0D4A3FCC),
                        Color.Transparent
                    ),
                    center = Offset(nebulaOffsetX1, nebulaOffsetY1),
                    radius = w * 0.55f
                ),
                radius = w * 0.55f,
                center = Offset(nebulaOffsetX1, nebulaOffsetY1)
            )

            // Nebula glow - bottom left (pink)
            val nebulaOffsetX2 = w * (0.25f - nebulaDrift * 0.06f)
            val nebulaOffsetY2 = h * (0.75f - nebulaDrift * 0.04f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x12FF6B9D),
                        Color(0x09CC4477),
                        Color.Transparent
                    ),
                    center = Offset(nebulaOffsetX2, nebulaOffsetY2),
                    radius = w * 0.5f
                ),
                radius = w * 0.5f,
                center = Offset(nebulaOffsetX2, nebulaOffsetY2)
            )

            // Nebula glow - center (teal, very subtle)
            val nebulaOffsetX3 = w * (0.5f + nebulaDrift * 0.04f)
            val nebulaOffsetY3 = h * (0.45f + nebulaDrift * 0.03f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x0A00D4AA),
                        Color(0x05008866),
                        Color.Transparent
                    ),
                    center = Offset(nebulaOffsetX3, nebulaOffsetY3),
                    radius = w * 0.4f
                ),
                radius = w * 0.4f,
                center = Offset(nebulaOffsetX3, nebulaOffsetY3)
            )

            // Draw twinkling stars
            for (star in stars) {
                val x = star.xFraction * w
                val y = star.yFraction * h
                val twinkle = sin(twinklePhase * star.speed + star.phaseOffset)
                val alpha = (star.baseAlpha + twinkle * 0.25f).coerceIn(0.05f, 0.9f)
                drawCircle(
                    color = Color(0xFFE8E0FF).copy(alpha = alpha),
                    radius = star.radius,
                    center = Offset(x, y)
                )
            }
        }

        content()
    }
}

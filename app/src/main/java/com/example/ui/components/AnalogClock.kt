package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnalogClock(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var timeMillis by remember { mutableStateOf(System.currentTimeMillis()) }
    val calendar = remember { Calendar.getInstance() }

    LaunchedEffect(Unit) {
        while (true) {
            timeMillis = System.currentTimeMillis()
            delay(100)
        }
    }

    calendar.timeInMillis = timeMillis

    val hour = calendar.get(Calendar.HOUR)
    val minute = calendar.get(Calendar.MINUTE)
    val second = calendar.get(Calendar.SECOND)
    val millisecond = calendar.get(Calendar.MILLISECOND)

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val accentColor = MaterialTheme.colorScheme.error

    Box(
        modifier = modifier
            .size(150.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(130.dp)) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.width / 2

            // Clock face boundary ring
            drawCircle(
                color = primaryColor.copy(alpha = 0.25f),
                radius = radius,
                center = center,
                style = Stroke(width = 3.5.dp.toPx())
            )

            // Center pivot dot
            drawCircle(
                color = primaryColor,
                radius = 4.5.dp.toPx(),
                center = center
            )

            // Hour markings
            for (i in 0 until 12) {
                val angleRad = Math.toRadians((i * 30).toDouble())
                val tickLength = if (i % 3 == 0) 10.dp.toPx() else 5.dp.toPx()
                val startX = (center.x + (radius - tickLength) * sin(angleRad)).toFloat()
                val startY = (center.y - (radius - tickLength) * cos(angleRad)).toFloat()
                val endX = (center.x + radius * sin(angleRad)).toFloat()
                val endY = (center.y - radius * cos(angleRad)).toFloat()
                drawLine(
                    color = primaryColor.copy(alpha = if (i % 3 == 0) 0.6f else 0.3f),
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = if (i % 3 == 0) 2.5.dp.toPx() else 1.5.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            // Hour hand (Short & bold)
            val hourAngleRad = Math.toRadians(((hour % 12) * 30 + minute * 0.5).toDouble())
            val hourLength = radius * 0.52f
            val hourX = (center.x + hourLength * sin(hourAngleRad)).toFloat()
            val hourY = (center.y - hourLength * cos(hourAngleRad)).toFloat()
            drawLine(
                color = primaryColor,
                start = center,
                end = Offset(hourX, hourY),
                strokeWidth = 4.5.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Minute hand (Medium long)
            val minuteAngleRad = Math.toRadians((minute * 6 + second * 0.1).toDouble())
            val minuteLength = radius * 0.72f
            val minuteX = (center.x + minuteLength * sin(minuteAngleRad)).toFloat()
            val minuteY = (center.y - minuteLength * cos(minuteAngleRad)).toFloat()
            drawLine(
                color = secondaryColor,
                start = center,
                end = Offset(minuteX, minuteY),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Second hand (Thin, sweeping)
            val secondAngleRad = Math.toRadians((second * 6 + millisecond * 0.006).toDouble())
            val secondLength = radius * 0.82f
            val secondX = (center.x + secondLength * sin(secondAngleRad)).toFloat()
            val secondY = (center.y - secondLength * cos(secondAngleRad)).toFloat()
            drawLine(
                color = accentColor,
                start = center,
                end = Offset(secondX, secondY),
                strokeWidth = 1.5.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}

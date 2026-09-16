package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ui.components.AmbientBackground
import com.example.ui.translation.Translations
import com.example.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController, viewModel: MainViewModel) {
    val scale = remember { Animatable(0.2f) }
    val alpha = remember { Animatable(0f) }
    val lang = viewModel.language.collectAsState().value

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("inicio/relax-inicio.json"))

    LaunchedEffect(key1 = true) {
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = 0.55f,
                    stiffness = 250f
                )
            )
        }
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = 0.8f,
                    stiffness = 150f
                )
            )
        }

        delay(3500) // Slightly longer to appreciate the new slow animation

        val isObDone = viewModel.prefs.isOnboardingCompleted
        val isEthicsDone = viewModel.prefs.isEthicsAccepted

        if (!isObDone) {
            navController.navigate("onboarding") {
                popUpTo("splash") { inclusive = true }
            }
        } else if (!isEthicsDone) {
            navController.navigate("disclaimer") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("dashboard") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    AmbientBackground {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // New main animation for splash
            LottieAnimation(
                composition = composition,
                modifier = Modifier
                    .size(220.dp)
                    .scale(scale.value)
                    .alpha(alpha.value),
                iterations = LottieConstants.IterateForever,
                speed = 0.3f // Slow and relaxing
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = Translations.getString("app_name", lang),
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .scale(scale.value)
                    .alpha(alpha.value)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = Translations.getString("tagline", lang),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                modifier = Modifier.alpha(alpha.value)
            )
        }
    }
}

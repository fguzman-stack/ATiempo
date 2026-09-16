package com.example

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.app.Notification
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.AddEditScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DisclaimerScreen
import com.example.ui.screens.JournalScreen
import com.example.ui.screens.MiAgendaScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.PrivacidadScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.TuRitmoScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val notificationPermLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        Log.d("MainActivity", "POST_NOTIFICATIONS granted=$isGranted")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        createNotificationChannel()
        checkAllPermissions()

        setContent {
            val viewModel: MainViewModel = viewModel()
            val themeState by viewModel.theme.collectAsState()

            MyApplicationTheme(themeState = themeState) {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "splash",
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable("splash") {
                        SplashScreen(navController = navController, viewModel = viewModel)
                    }
                    composable("onboarding") {
                        OnboardingScreen(navController = navController, viewModel = viewModel)
                    }
                    composable("disclaimer") {
                        DisclaimerScreen(navController = navController, viewModel = viewModel)
                    }
                    composable("dashboard") {
                        DashboardScreen(navController = navController, viewModel = viewModel)
                    }
                    composable(
                        route = "add_edit?id={id}",
                        arguments = listOf(navArgument("id") { type = NavType.IntType; defaultValue = -1 })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("id") ?: -1
                        val finalId = if (id == -1) null else id
                        AddEditScreen(navController = navController, viewModel = viewModel, reminderId = finalId)
                    }
                    composable("settings") {
                        SettingsScreen(navController = navController, viewModel = viewModel)
                    }
                    composable("rhythm") {
                        TuRitmoScreen(navController = navController, viewModel = viewModel)
                    }
                    composable("agenda") {
                        MiAgendaScreen(navController = navController, viewModel = viewModel)
                    }
                    composable("privacy") {
                        PrivacidadScreen(navController = navController, viewModel = viewModel)
                    }
                    composable("journal") {
                        JournalScreen(navController = navController, viewModel = viewModel)
                    }
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "atiempo_notificaciones"
            val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val prefs = com.example.data.preferences.PreferenceManager(this)
            val lang = prefs.selectedLanguage

            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            val channel = NotificationChannel(
                channelId,
                com.example.ui.translation.Translations.getString("notif_chan_main_name", lang),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = com.example.ui.translation.Translations.getString("notif_chan_main_desc", lang)
                enableVibration(true)
                enableLights(true)
                setSound(soundUri, audioAttributes)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            val nm = getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(channel)
        }
    }

    private fun checkAllPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(android.app.AlarmManager::class.java)
            if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                try {
                    val intent = Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error launching exact alarm settings: ${e.message}")
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}

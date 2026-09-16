package com.example.ui.screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.core.content.ContextCompat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ui.components.AmbientBackground
import com.example.ui.components.SweetAlertDialog
import com.example.ui.translation.AppLanguage
import com.example.ui.translation.Translations
import com.example.ui.viewmodel.MainViewModel

@Composable
fun SettingsScreen(navController: NavController, viewModel: MainViewModel) {
    val currentLang by viewModel.language.collectAsState()
    val currentTheme by viewModel.theme.collectAsState()
    val hapticEnabled by viewModel.hapticEnabled.collectAsState()
    val totalCompletions by viewModel.totalCompletions.collectAsState()

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Dialog trigger states
    var showThemeDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var pendingLanguage by remember { mutableStateOf<AppLanguage?>(null) }
    var showDeleteAllDialog by remember { mutableStateOf(false) }


    val premiumUnlocked = totalCompletions >= 50
    val galacticUnlocked = totalCompletions >= 100

    AmbientBackground {
        Scaffold(
            containerColor = Color.Transparent
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(scrollState)
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            viewModel.triggerHapticFeedback()
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = Translations.getString("settings", currentLang),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Section 1: Experiencia
                Text(
                    text = Translations.getString("experience_title", currentLang),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.45f)
                    )
                ) {
                    Column {
                        // Tema
                        SettingsItem(
                            title = Translations.getString("theme", currentLang),
                            subtitle = getThemeDisplayName(currentTheme, currentLang),
                            icon = Icons.Default.Palette,
                            onClick = {
                                viewModel.triggerHapticFeedback()
                                showThemeDialog = true
                            }
                        )
                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f))

                        // Idioma
                        SettingsItem(
                            title = Translations.getString("language", currentLang),
                            subtitle = currentLang.displayName,
                            icon = Icons.Default.Language,
                            onClick = {
                                viewModel.triggerHapticFeedback()
                                showLanguageDialog = true
                            }
                        )
                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f))

                        // Háptico Toggle
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .size(22.dp)
                                        .alpha(0.7f)
                                )
                                Spacer(modifier = Modifier.width(14.dp))
                                Column {
                                    Text(
                                        text = Translations.getString("haptic", currentLang),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                            Switch(
                                checked = hapticEnabled,
                                onCheckedChange = {
                                    viewModel.setHapticEnabled(it)
                                    viewModel.triggerHapticFeedback()
                                },
                                modifier = Modifier.testTag("haptic_toggle_switch")
                            )
                        }
                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f))

                        // Enlace a Tu Ritmo
                        SettingsItem(
                            title = Translations.getString("rhythm", currentLang),
                            subtitle = Translations.getString("rhythm_subtitle", currentLang),
                            icon = Icons.Default.Star,
                            onClick = {
                                viewModel.triggerHapticFeedback()
                                navController.navigate("rhythm")
                            }
                        )
                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f))

                        // Enlace a Mi Agenda
                        SettingsItem(
                            title = Translations.getString("agenda", currentLang),
                            subtitle = Translations.getString("agenda_subtitle", currentLang),
                            icon = Icons.Default.CalendarMonth,
                            onClick = {
                                viewModel.triggerHapticFeedback()
                                navController.navigate("agenda")
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Section 2: Tu Espacio
                Text(
                    text = Translations.getString("your_space_title", currentLang),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.45f)
                    )
                ) {
                    Column {
                        SettingsItem(
                            title = Translations.getString("privacy", currentLang),
                            subtitle = Translations.getString("privacy_subtitle", currentLang),
                            icon = Icons.Default.Lock,
                            onClick = {
                                viewModel.triggerHapticFeedback()
                                navController.navigate("privacy")
                            }
                        )
                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f))
                        SettingsItem(
                            title = Translations.getString("disclaimer_title", currentLang),
                            subtitle = Translations.getString("disclaimer_subtitle", currentLang),
                            icon = Icons.Default.Info,
                            onClick = {
                                viewModel.triggerHapticFeedback()
                                navController.navigate("disclaimer")
                            }
                        )
                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f))
                        SettingsItem(
                            title = Translations.getString("journal_title", currentLang),
                            subtitle = Translations.getString("journal_subtitle", currentLang),
                            icon = Icons.Default.Create,
                            onClick = {
                                viewModel.triggerHapticFeedback()
                                navController.navigate("journal")
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Section 3: Mantenimiento
                Text(
                    text = Translations.getString("maintenance_title", currentLang),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.45f)
                    )
                ) {
                    Column {
                        // Optimización de batería
                        SettingsItem(
                            title = Translations.getString("battery", currentLang),
                            subtitle = Translations.getString("battery_subtitle", currentLang),
                            icon = Icons.Default.Build,
                            onClick = {
                                viewModel.triggerHapticFeedback()
                                requestIgnoreBatteryOptimizations(context)
                            }
                        )
                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f))

                        // Notificaciones
                        val notificationGranted = checkNotificationPermission(context)
                        SettingsItem(
                            title = Translations.getString("notifications_title", currentLang),
                            subtitle = if (notificationGranted) Translations.getString("notifications_on", currentLang) else Translations.getString("notifications_off", currentLang),
                            icon = Icons.Default.Notifications,
                            iconTint = if (notificationGranted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                            onClick = {
                                viewModel.triggerHapticFeedback()
                                openAppNotificationSettings(context)
                            }
                        )
                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f))

                        // Borrar todos los datos
                        SettingsItem(
                            title = Translations.getString("delete_all", currentLang),
                            subtitle = Translations.getString("delete_all_subtitle", currentLang),
                            icon = Icons.Default.Delete,
                            iconTint = MaterialTheme.colorScheme.error,
                            titleColor = MaterialTheme.colorScheme.error,
                            onClick = {
                                viewModel.triggerHapticFeedback()
                                showDeleteAllDialog = true
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Footer
                Text(
                    text = Translations.getString("app_name", currentLang),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = Translations.getString("version", currentLang),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }

    // Theme Chooser Dialog
    if (showThemeDialog) {
        val themes = listOf(
            "system" to Translations.getString("theme_system", currentLang),
            "light" to Translations.getString("theme_light", currentLang),
            "dark" to Translations.getString("theme_dark", currentLang),
            "blue" to Translations.getString("theme_blue", currentLang),
            "pink" to Translations.getString("theme_pink", currentLang),
            "green" to Translations.getString("theme_green", currentLang),
            "purple" to Translations.getString("theme_purple", currentLang),
            "galactic" to Translations.getString("theme_galactic", currentLang)
        )

        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            title = { Text(Translations.getString("theme", currentLang)) },
            text = {
                Column {
                    themes.forEach { (themeKey, name) ->
                        val isPremium = themeKey == "blue" || themeKey == "pink" || themeKey == "green" || themeKey == "purple"
                        val isGalactic = themeKey == "galactic"
                        val isLocked = when {
                            isGalactic -> !galacticUnlocked
                            isPremium -> !premiumUnlocked
                            else -> false
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = !isLocked) {
                                    viewModel.setTheme(themeKey)
                                    viewModel.triggerHapticFeedback()
                                    showThemeDialog = false
                                }
                                .padding(vertical = 12.dp, horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                if (isPremium || isGalactic) {
                                    val dotColor = when (themeKey) {
                                        "blue" -> Color(0xFF0D47A1)
                                        "pink" -> Color(0xFFC2185B)
                                        "green" -> Color(0xFF004D40)
                                        "purple" -> Color(0xFF4A148C)
                                        "galactic" -> Color(0xFF6C63FF)
                                        else -> Color.Transparent
                                    }
                                    Box(
                                        modifier = Modifier
                                            .size(14.dp)
                                            .clip(CircleShape)
                                            .background(dotColor)
                                    )
                                }
                                Text(
                                    text = name,
                                    fontSize = 15.sp,
                                    color = if (isLocked) Color.Gray else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = if (currentTheme == themeKey) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                            if (isLocked) {
                                val needed = when (themeKey) {
                                    "galactic" -> 100
                                    else -> 50
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 3.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "$needed",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Locked Theme",
                                        tint = Color.Gray.copy(alpha = 0.6f),
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showThemeDialog = false }) {
                    Text(Translations.getString("cancel", currentLang))
                }
            }
        )
    }

    // Language Chooser Dialog with Confirmation
    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = { Text(Translations.getString("language", currentLang)) },
            text = {
                Column {
                    AppLanguage.entries.forEach { lang ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    pendingLanguage = lang
                                    showLanguageDialog = false
                                }
                                .padding(vertical = 12.dp, horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = lang.flag, fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = lang.displayName,
                                fontSize = 15.sp,
                                fontWeight = if (currentLang == lang) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showLanguageDialog = false }) {
                    Text(Translations.getString("cancel", currentLang))
                }
            }
        )
    }

    // Language confirm step
    if (pendingLanguage != null) {
        AlertDialog(
            onDismissRequest = { pendingLanguage = null },
            title = { Text(Translations.getString("lang_confirm_title", currentLang)) },
            text = { Text(Translations.getString("lang_confirm_desc", currentLang)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.setLanguage(pendingLanguage!!)
                        viewModel.triggerHapticFeedback()
                        pendingLanguage = null
                    }
                ) {
                    Text(Translations.getString("yes", currentLang))
                }
            },
            dismissButton = {
                TextButton(onClick = { pendingLanguage = null }) {
                    Text(Translations.getString("no", currentLang))
                }
            }
        )
    }

    // Delete All Confirmation Dialog
    if (showDeleteAllDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteAllDialog = false },
            title = { Text(Translations.getString("reset_dialog_title", currentLang)) },
            text = { Text(Translations.getString("delete_confirm", currentLang)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.triggerHapticFeedback()
                        viewModel.clearAllData()
                        showDeleteAllDialog = false
                        navController.navigate("splash") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                ) {
                    Text(
                        text = Translations.getString("delete_confirm_button", currentLang),
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteAllDialog = false }) {
                    Text(Translations.getString("cancel", currentLang))
                }
            }
        )
    }
}

@Composable
fun SettingsItem(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier
                    .size(22.dp)
                    .alpha(0.7f)
            )
            Spacer(modifier = Modifier.width(14.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = titleColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

private fun checkNotificationPermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}

private fun openAppNotificationSettings(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        }
        context.startActivity(intent)
    } else {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
    }
}

private fun getThemeDisplayName(themeKey: String, lang: AppLanguage): String {
    return when (themeKey) {
        "light" -> Translations.getString("theme_light", lang)
        "dark" -> Translations.getString("theme_dark", lang)
        "blue" -> Translations.getString("theme_blue", lang)
        "pink" -> Translations.getString("theme_pink", lang)
        "green" -> Translations.getString("theme_green", lang)
        "purple" -> Translations.getString("theme_purple", lang)
        "galactic" -> Translations.getString("theme_galactic", lang)
        else -> Translations.getString("theme_system", lang)
    }
}

private fun requestIgnoreBatteryOptimizations(context: Context) {
    val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
    } else {
        Intent(Settings.ACTION_SETTINGS)
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        context.startActivity(Intent(Settings.ACTION_SETTINGS))
    }
}

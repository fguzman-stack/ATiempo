package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.data.database.ReminderEntity
import com.example.ui.components.AmbientBackground
import com.example.ui.components.AnalogClock
import com.example.ui.components.SweetAlertDialog
import com.example.ui.translation.AppLanguage
import com.example.ui.translation.Translations
import com.example.ui.viewmodel.MainViewModel
import com.example.util.AlarmScheduler
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, viewModel: MainViewModel) {
    val currentLang by viewModel.language.collectAsState()
    val remindersList by viewModel.reminders.collectAsState()
    val completionsList by viewModel.completions.collectAsState()
    val globalStreak by viewModel.globalStreak.collectAsState()
    val totalCompletions by viewModel.totalCompletions.collectAsState()
    val showCelebration by viewModel.showCelebration.collectAsState()
    val showGalacticCelebration by viewModel.showGalacticCelebration.collectAsState()

    var activeFilter by remember { mutableStateOf("all") } // "all", "today", "week"
    var activeSort by remember { mutableStateOf("time") } // "time", "name", "pending"

    var showQuickAddSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    var pendingDeleteReminder by remember { mutableStateOf<ReminderEntity?>(null) }

    // Clock text state
    var currentTimeString by remember { mutableStateOf("") }
    var currentDateString by remember { mutableStateOf("") }

    LaunchedEffect(currentLang) {
        val locale = Translations.getLocale(currentLang)
        val timeFormat = SimpleDateFormat("HH:mm:ss", locale)
        val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", locale)
        while (true) {
            val now = Date()
            currentTimeString = timeFormat.format(now)
            currentDateString = dateFormat.format(now)
            delay(1000)
        }
    }

    // Current time snapshot for checkmark visibility, updated every 15s
    var currentCalendar by remember { mutableStateOf(Calendar.getInstance()) }
    LaunchedEffect(Unit) {
        while (true) {
            currentCalendar = Calendar.getInstance()
            delay(15_000)
        }
    }

    // Process filters and sorting
    val filteredReminders = remember(remindersList, activeFilter, activeSort) {
        var list = remindersList.filter { it.isActive }

        // Filter
        val now = Calendar.getInstance()
        list = when (activeFilter) {
            "today" -> list.filter { reminder ->
                if (!reminder.isActive) return@filter false
                if (reminder.recurrence == "ONCE" && reminder.specificDate != null) {
                    val remCal = Calendar.getInstance().apply { timeInMillis = reminder.specificDate }
                    return@filter now.get(Calendar.YEAR) == remCal.get(Calendar.YEAR) &&
                            now.get(Calendar.DAY_OF_YEAR) == remCal.get(Calendar.DAY_OF_YEAR)
                }
                if (reminder.recurrence == "DAILY") return@filter true
                if (reminder.recurrence == "WEEKLY") {
                    val todayDay = when (now.get(Calendar.DAY_OF_WEEK)) {
                        Calendar.MONDAY -> 1
                        Calendar.TUESDAY -> 2
                        Calendar.WEDNESDAY -> 3
                        Calendar.THURSDAY -> 4
                        Calendar.FRIDAY -> 5
                        Calendar.SATURDAY -> 6
                        Calendar.SUNDAY -> 7
                        else -> 1
                    }
                    return@filter reminder.recurrenceDays.split(",").contains(todayDay.toString())
                }
                if (reminder.recurrence == "MONTHLY") {
                    if (reminder.specificDate == null) return@filter false
                    val today = Calendar.getInstance()
                    val remCal = Calendar.getInstance().apply { timeInMillis = reminder.specificDate }
                    return@filter today.get(Calendar.DAY_OF_MONTH) == remCal.get(Calendar.DAY_OF_MONTH)
                }
                true
            }
            "week" -> list.filter { reminder ->
                // Within next 7 days
                if (!reminder.isActive) return@filter false
                val trigger = AlarmScheduler.calculateNextTriggerTime(reminder) ?: return@filter false
                val diffDays = (trigger - System.currentTimeMillis()) / (24 * 60 * 60 * 1000)
                diffDays in 0..7
            }
            else -> list
        }

        // Sort
        list = when (activeSort) {
            "name" -> list.sortedBy { it.name.lowercase() }
            "pending" -> {
                // Done first or pending first. "pendientes primero"
                list.sortedWith(compareBy<ReminderEntity> {
                    val last = it.lastCompleted
                    if (last == null) 0 else {
                        val lastCal = Calendar.getInstance().apply { timeInMillis = last }
                        val today = Calendar.getInstance()
                        if (today.get(Calendar.YEAR) == lastCal.get(Calendar.YEAR) &&
                            today.get(Calendar.DAY_OF_YEAR) == lastCal.get(Calendar.DAY_OF_YEAR)
                        ) 1 else 0
                    }
                }.thenBy { it.timeHour * 60 + it.timeMinute })
            }
            else -> list.sortedBy { it.timeHour * 60 + it.timeMinute }
        }

        list
    }

    // Find the next/urgent highlight card reminder
    val nextUrgentReminder = remember(remindersList) {
        val active = remindersList.filter { it.isActive }
        active.mapNotNull { reminder ->
            val trigger = AlarmScheduler.calculateNextTriggerTime(reminder)
            if (trigger != null) reminder to trigger else null
        }.minByOrNull { it.second }?.first
    }

    AmbientBackground {
        Scaffold(
            containerColor = Color.Transparent, // show animated ambient background
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.triggerHapticFeedback()
                    navController.navigate("add_edit")
                },
                modifier = Modifier.testTag("add_reminder_fab")
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Intention")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Translations.getString("app_name", currentLang),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Row {
                    IconButton(
                        onClick = {
                            viewModel.triggerHapticFeedback()
                            navController.navigate("agenda")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "My Agenda",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.triggerHapticFeedback()
                            navController.navigate("journal")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = "Journal",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.triggerHapticFeedback()
                            navController.navigate("settings")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Clock Section
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AnalogClock(
                            onClick = {
                                viewModel.triggerHapticFeedback()
                                showQuickAddSheet = true
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentTimeString,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = currentDateString,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    }
                }

                // Wellness quote card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.35f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = Translations.getWellnessQuoteForToday(currentLang),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Filters & Sorting section
                item {
                    Column {
                        // Filter Chips Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                selected = activeFilter == "all",
                                onClick = { activeFilter = "all" },
                                label = { Text(Translations.getString("filter_all", currentLang)) }
                            )
                            FilterChip(
                                selected = activeFilter == "today",
                                onClick = { activeFilter = "today" },
                                label = { Text(Translations.getString("filter_today", currentLang)) }
                            )
                            FilterChip(
                                selected = activeFilter == "week",
                                onClick = { activeFilter = "week" },
                                label = { Text(Translations.getString("filter_week", currentLang)) }
                            )
                        }

                        // Sorting row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = Translations.getString("sort_order", currentLang),
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                            )
                            FilterChip(
                                selected = activeSort == "time",
                                onClick = { activeSort = "time" },
                                label = { Text(Translations.getString("sort_time", currentLang)) }
                            )
                            FilterChip(
                                selected = activeSort == "name",
                                onClick = { activeSort = "name" },
                                label = { Text(Translations.getString("sort_name", currentLang)) }
                            )
                            FilterChip(
                                selected = activeSort == "pending",
                                onClick = { activeSort = "pending" },
                                label = { Text(Translations.getString("sort_pending", currentLang)) }
                            )
                        }
                    }
                }

                // Featured next urgent card
                if (nextUrgentReminder != null) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = Translations.getString("next_reminder", currentLang),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = nextUrgentReminder.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                    Text(
                                        text = String.format("%02d:%02d • %s", nextUrgentReminder.timeHour, nextUrgentReminder.timeMinute, nextUrgentReminder.category),
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Alert",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }
                }

                // Reminders list
                if (filteredReminders.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val emptyComposition by rememberLottieComposition(LottieCompositionSpec.Asset("dashboard_vacio/lista.json"))
                            val emptyProgress by animateLottieCompositionAsState(
                                composition = emptyComposition,
                                iterations = LottieConstants.IterateForever,
                                speed = 0.7f
                            )
                            LottieAnimation(
                                composition = emptyComposition,
                                progress = { emptyProgress },
                                modifier = Modifier.size(160.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = Translations.getString("no_reminders", currentLang),
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    items(filteredReminders, key = { it.id }) { reminder ->
                        val hasCompletedToday = remember(reminder.lastCompleted) {
                            val last = reminder.lastCompleted
                            if (last == null) false else {
                                val lastCal = Calendar.getInstance().apply { timeInMillis = last }
                                val today = Calendar.getInstance()
                                today.get(Calendar.YEAR) == lastCal.get(Calendar.YEAR) &&
                                        today.get(Calendar.DAY_OF_YEAR) == lastCal.get(Calendar.DAY_OF_YEAR)
                            }
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("add_edit?id=${reminder.id}")
                                },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (hasCompletedToday) {
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                                } else {
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                                }
                            )
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .background(
                                                    getCategoryColor(reminder.category).copy(alpha = 0.25f),
                                                    CircleShape
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = getCategoryIcon(reminder.category),
                                                contentDescription = reminder.category,
                                                tint = getCategoryColor(reminder.category)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(
                                                text = reminder.name,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (hasCompletedToday) {
                                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                                } else {
                                                    MaterialTheme.colorScheme.onSurface
                                                }
                                            )
                                            Text(
                                                text = String.format("%02d:%02d • %s", reminder.timeHour, reminder.timeMinute, reminder.recurrence),
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                            )
                                            if (globalStreak > 0) {
                                                Text(
                                                    text = "🔥 ${Translations.getString("streak_label", currentLang)}: $globalStreak ${Translations.getString("streak_days", currentLang)}",
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        }
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        val now = currentCalendar
                                        val isCorrectDate = when {
                                            reminder.recurrence == "ONCE" && reminder.specificDate != null -> {
                                                val remCal = Calendar.getInstance().apply { timeInMillis = reminder.specificDate }
                                                now.get(Calendar.YEAR) == remCal.get(Calendar.YEAR) &&
                                                        now.get(Calendar.DAY_OF_YEAR) == remCal.get(Calendar.DAY_OF_YEAR)
                                            }
                                            reminder.recurrence == "WEEKLY" -> {
                                                val todayDay = when (now.get(Calendar.DAY_OF_WEEK)) {
                                                    Calendar.MONDAY -> 1; Calendar.TUESDAY -> 2
                                                    Calendar.WEDNESDAY -> 3; Calendar.THURSDAY -> 4
                                                    Calendar.FRIDAY -> 5; Calendar.SATURDAY -> 6
                                                    Calendar.SUNDAY -> 7; else -> 1
                                                }
                                                reminder.recurrenceDays.split(",").contains(todayDay.toString())
                                            }
                                            reminder.recurrence == "MONTHLY" && reminder.specificDate != null -> {
                                                val remCal = Calendar.getInstance().apply { timeInMillis = reminder.specificDate }
                                                now.get(Calendar.DAY_OF_MONTH) == remCal.get(Calendar.DAY_OF_MONTH)
                                            }
                                            else -> true
                                        }
                                        val isTimeForReminder = isCorrectDate && (
                                            now.get(Calendar.HOUR_OF_DAY) > reminder.timeHour ||
                                                (now.get(Calendar.HOUR_OF_DAY) == reminder.timeHour &&
                                                        now.get(Calendar.MINUTE) >= reminder.timeMinute)
                                        )
                                        if (!hasCompletedToday && isTimeForReminder) {
                                            IconButton(
                                                onClick = {
                                                    viewModel.triggerHapticFeedback()
                                                    viewModel.completeReminder(reminder)
                                                },
                                                modifier = Modifier.testTag("complete_reminder_button")
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = "Complete",
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        }
                                        IconButton(
                                            onClick = {
                                                viewModel.triggerHapticFeedback()
                                                pendingDeleteReminder = reminder
                                            },
                                            modifier = Modifier.testTag("delete_reminder_button")
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete",
                                                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }

    // Quick Add Bottom Sheet
    if (showQuickAddSheet) {
        var quickName by remember { mutableStateOf("") }
        var selectedCat by remember { mutableStateOf("Otros") }

        val dynamicFabColor by animateColorAsState(
            targetValue = if (quickName.isEmpty()) {
                MaterialTheme.colorScheme.primary
            } else {
                val hash = quickName.hashCode()
                val hue = (hash.absoluteValue % 360).toFloat()
                Color.hsv(hue, 0.45f, 0.85f)
            },
            label = "QuickAddFabColor"
        )

        ModalBottomSheet(
            onDismissRequest = { showQuickAddSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .padding(bottom = 32.dp)
            ) {
                Text(
                    text = Translations.getString("add_quick", currentLang),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = quickName,
                    onValueChange = { if (it.length <= 40) quickName = it },
                    label = { Text(Translations.getString("name_hint", currentLang)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = Translations.getString("category", currentLang),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Scrollable category chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Salud", "Trabajo", "Hogar", "Otros").forEach { cat ->
                        FilterChip(
                            selected = selectedCat == cat,
                            onClick = { selectedCat = cat },
                            label = { Text(Translations.getString("category_${cat.lowercase()}", currentLang)) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (quickName.isNotEmpty()) {
                            viewModel.triggerHapticFeedback()
                            val now = Calendar.getInstance()
                            val newReminder = ReminderEntity(
                                name = quickName,
                                category = selectedCat,
                                timeHour = now.get(Calendar.HOUR_OF_DAY),
                                timeMinute = now.get(Calendar.MINUTE) + 2, // 2 mins from now by default
                                recurrence = "DAILY",
                                recurrenceDays = "1,2,3,4,5,6,7",
                                specificDate = null,
                                notes = "",
                                vibrate = true
                            )
                            viewModel.insertReminder(newReminder)
                            showQuickAddSheet = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = dynamicFabColor
                    ),
                    enabled = quickName.isNotEmpty()
                ) {
                    Text(
                        text = Translations.getString("save", currentLang),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // Delete reminder confirmation
    pendingDeleteReminder?.let { reminder ->
        SweetAlertDialog(
            icon = Icons.Filled.Warning,
            iconTint = Color(0xFFE53935),
            iconBackground = Color(0xFFFFEBEE),
            title = Translations.getString("delete_reminder_title", currentLang),
            description = Translations.getFormattedString("delete_reminder_desc", currentLang, reminder.name),
            confirmText = Translations.getString("delete", currentLang),
            onConfirm = {
                viewModel.triggerHapticFeedback()
                viewModel.deleteReminder(reminder.id)
                pendingDeleteReminder = null
            },
            dismissText = Translations.getString("cancel", currentLang),
            onDismiss = { pendingDeleteReminder = null }
        )
    }

    // Celebration premium unlocked Dialog
    if (showCelebration) {
        SweetAlertDialog(
            icon = Icons.Default.Star,
            iconTint = Color(0xFFFFC107),
            iconBackground = Color(0xFFFFF8E1),
            title = Translations.getString("congratulations", currentLang),
            description = Translations.getString("premium_unlocked_dialog", currentLang),
            confirmText = Translations.getString("ok", currentLang),
            onConfirm = {
                viewModel.triggerHapticFeedback()
                viewModel.dismissCelebration()
            },
            dismissText = "",
            onDismiss = { viewModel.dismissCelebration() }
        )
    }

    // Celebration galactic unlocked Dialog
    if (showGalacticCelebration) {
        SweetAlertDialog(
            icon = Icons.Default.Star,
            iconTint = Color(0xFFE8E0FF),
            iconBackground = Color(0xFF6C63FF),
            title = Translations.getString("congratulations", currentLang),
            description = Translations.getString("galactic_unlocked_dialog", currentLang),
            confirmText = Translations.getString("ok", currentLang),
            onConfirm = {
                viewModel.triggerHapticFeedback()
                viewModel.setTheme("galactic")
                viewModel.dismissCelebration()
            },
            dismissText = "",
            onDismiss = { viewModel.dismissCelebration() }
        )
    }
    }
}

// Global visual helpers for categories
fun getCategoryIcon(category: String): ImageVector {
    return when (category) {
        "Salud" -> Icons.Default.Favorite
        "Trabajo" -> Icons.Default.Work
        "Estudio" -> Icons.Default.School
        "Hogar" -> Icons.Default.Home
        "Finanzas" -> Icons.Default.AttachMoney
        "Social" -> Icons.Default.People
        else -> Icons.Default.Star
    }
}

fun getCategoryColor(category: String): Color {
    return when (category) {
        "Salud" -> Color(0xFFE57373)
        "Trabajo" -> Color(0xFF4FC3F7)
        "Estudio" -> Color(0xFF9575CD)
        "Hogar" -> Color(0xFFFFB74D)
        "Finanzas" -> Color(0xFF81C784)
        "Social" -> Color(0xFFBA68C8)
        else -> Color(0xFF90A4AE)
    }
}

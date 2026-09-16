package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.data.database.ReminderEntity
import com.example.ui.components.AmbientBackground
import com.example.ui.translation.AppLanguage
import com.example.ui.translation.Translations
import com.example.ui.viewmodel.MainViewModel
import com.example.util.AlarmScheduler
import java.text.DateFormatSymbols
import java.util.Calendar
import java.util.Locale
import kotlinx.coroutines.delay

@Composable
fun MiAgendaScreen(navController: NavController, viewModel: MainViewModel) {
    val currentLang by viewModel.language.collectAsState()
    val remindersList by viewModel.reminders.collectAsState()

    var selectedYear by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    var selectedMonth by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH)) } // 0-11
    var selectedDay by remember { mutableStateOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) }

    var selectedCategoryFilter by remember { mutableStateOf("Todos") }

    LaunchedEffect(Unit) {
        while (true) {
            delay(15_000)
        }
    }

    // Navigation and Calendar calculation
    val calendarDays = remember(selectedYear, selectedMonth) {
        val cal = Calendar.getInstance().apply {
            set(Calendar.YEAR, selectedYear)
            set(Calendar.MONTH, selectedMonth)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) // Sun=1, Mon=2...
        val maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        // Monday start mapping
        val startOffset = when (firstDayOfWeek) {
            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            Calendar.SATURDAY -> 5
            Calendar.SUNDAY -> 6
            else -> 0
        }

        val days = mutableListOf<Int?>()
        for (i in 0 until startOffset) {
            days.add(null) // Empty days at the start of week grid
        }
        for (i in 1..maxDays) {
            days.add(i)
        }
        days
    }

    // Filter reminders by category
    val filteredReminders = remember(remindersList, selectedCategoryFilter) {
        if (selectedCategoryFilter == "Todos") {
            remindersList
        } else {
            remindersList.filter { it.category == selectedCategoryFilter }
        }
    }

    // Determine indicators for a given day in the calendar grid
    fun getIndicatorsForDay(day: Int): DayStatus {
        val dateCal = Calendar.getInstance().apply {
            set(Calendar.YEAR, selectedYear)
            set(Calendar.MONTH, selectedMonth)
            set(Calendar.DAY_OF_MONTH, day)
        }
        val dayOfWeek = when (dateCal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            Calendar.SUNDAY -> 7
            else -> 1
        }

        val dayReminders = filteredReminders.filter { reminder ->
            if (!reminder.isActive) return@filter false
            when (reminder.recurrence) {
                "ONCE" -> {
                    if (reminder.specificDate == null) return@filter false
                    val remCal = Calendar.getInstance().apply { timeInMillis = reminder.specificDate }
                    selectedYear == remCal.get(Calendar.YEAR) &&
                            selectedMonth == remCal.get(Calendar.MONTH) &&
                            day == remCal.get(Calendar.DAY_OF_MONTH)
                }
                "DAILY" -> true
                "WEEKLY" -> reminder.recurrenceDays.split(",").contains(dayOfWeek.toString())
                "MONTHLY" -> {
                    if (reminder.specificDate == null) return@filter false
                    val remCal = Calendar.getInstance().apply { timeInMillis = reminder.specificDate }
                    day == remCal.get(Calendar.DAY_OF_MONTH)
                }
                else -> true
            }
        }

        if (dayReminders.isEmpty()) return DayStatus(hasActive = false, hasUrgent = false, hasPending = false)

        var hasActive = false
        var hasUrgent = false
        var hasPending = false

        val nowMs = System.currentTimeMillis()
        val thirtyMinsMs = 30 * 60 * 1000

        for (rem in dayReminders) {
            hasActive = true
            // check urgent (next 30 mins)
            val trigger = AlarmScheduler.calculateNextTriggerTime(rem)
            if (trigger != null && trigger - nowMs in 0..thirtyMinsMs) {
                hasUrgent = true
            }

            // check pending (active and not completed today/that day)
            val lastComp = rem.lastCompleted
            val completedThatDay = if (lastComp != null) {
                val lastCal = Calendar.getInstance().apply { timeInMillis = lastComp }
                selectedYear == lastCal.get(Calendar.YEAR) &&
                        selectedMonth == lastCal.get(Calendar.MONTH) &&
                        day == lastCal.get(Calendar.DAY_OF_MONTH)
            } else {
                false
            }
            if (!completedThatDay) {
                hasPending = true
            }
        }

        return DayStatus(hasActive = hasActive, hasUrgent = hasUrgent, hasPending = hasPending)
    }

    // Reminders of the selected touched day
    val selectedDayReminders = remember(selectedDay, filteredReminders, selectedYear, selectedMonth) {
        val dateCal = Calendar.getInstance().apply {
            set(Calendar.YEAR, selectedYear)
            set(Calendar.MONTH, selectedMonth)
            set(Calendar.DAY_OF_MONTH, selectedDay)
        }
        val dayOfWeek = when (dateCal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            Calendar.SUNDAY -> 7
            else -> 1
        }

        filteredReminders.filter { reminder ->
            if (!reminder.isActive) return@filter false
            when (reminder.recurrence) {
                "ONCE" -> {
                    if (reminder.specificDate == null) return@filter false
                    val remCal = Calendar.getInstance().apply { timeInMillis = reminder.specificDate }
                    selectedYear == remCal.get(Calendar.YEAR) &&
                            selectedMonth == remCal.get(Calendar.MONTH) &&
                            selectedDay == remCal.get(Calendar.DAY_OF_MONTH)
                }
                "DAILY" -> true
                "WEEKLY" -> reminder.recurrenceDays.split(",").contains(dayOfWeek.toString())
                "MONTHLY" -> {
                    if (reminder.specificDate == null) return@filter false
                    val remCal = Calendar.getInstance().apply { timeInMillis = reminder.specificDate }
                    selectedDay == remCal.get(Calendar.DAY_OF_MONTH)
                }
                else -> true
            }
        }
    }

    AmbientBackground {
        Scaffold(
            containerColor = Color.Transparent
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            viewModel.triggerHapticFeedback()
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = Translations.getString("agenda", currentLang),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                // Category selection row
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val categories = listOf("Todos", "Salud", "Trabajo", "Estudio", "Hogar", "Finanzas", "Social", "Otros")
                    items(categories) { cat ->
                        FilterChip(
                            selected = selectedCategoryFilter == cat,
                            onClick = { selectedCategoryFilter = cat },
                            label = { Text(if (cat == "Todos") Translations.getString("filter_all", currentLang) else Translations.getString("category_${cat.lowercase()}", currentLang)) }
                        )
                    }
                }

                // Calendar Navigation Controls
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            viewModel.triggerHapticFeedback()
                            if (selectedMonth == 0) {
                                selectedMonth = 11
                                selectedYear -= 1
                            } else {
                                selectedMonth -= 1
                            }
                        }
                    ) {
                        Icon(Icons.Default.ChevronLeft, "Previous Month")
                    }

                    Text(
                        text = "${getMonthName(selectedMonth, currentLang).uppercase()} $selectedYear",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    IconButton(
                        onClick = {
                            viewModel.triggerHapticFeedback()
                            if (selectedMonth == 11) {
                                selectedMonth = 0
                                selectedYear += 1
                            } else {
                                selectedMonth += 1
                            }
                        }
                    ) {
                        Icon(Icons.Default.ChevronRight, "Next Month")
                    }
                }

                // Calendar Day Initials Headers (Mon - Sun)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val daysHeaders = when (currentLang) {
                        AppLanguage.ES -> listOf("L", "M", "M", "J", "V", "S", "D")
                        AppLanguage.FR -> listOf("L", "M", "M", "J", "V", "S", "D")
                        else -> listOf("M", "T", "W", "T", "F", "S", "S")
                    }
                    daysHeaders.forEach {
                        Text(
                            text = it,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    }
                }

                // Calendar Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                ) {
                    items(calendarDays.size) { index ->
                        val day = calendarDays[index]
                        if (day != null) {
                            val status = getIndicatorsForDay(day)
                            val isSelected = (day == selectedDay)
                            val isToday = (day == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) &&
                                    selectedMonth == Calendar.getInstance().get(Calendar.MONTH) &&
                                    selectedYear == Calendar.getInstance().get(Calendar.YEAR))

                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                        else if (isToday) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                        else Color.Transparent
                                    )
                                    .clickable {
                                        viewModel.triggerHapticFeedback()
                                        selectedDay = day
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = day.toString(),
                                        fontSize = 16.sp,
                                        fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onBackground
                                    )
                                    // Status dots
                                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                        if (status.hasActive) {
                                            val dotColor = when {
                                                status.hasUrgent -> Color.Red
                                                status.hasPending -> Color(0xFFFFA500)
                                                else -> Color.Green
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .size(4.dp)
                                                    .clip(CircleShape)
                                                    .background(dotColor)
                                            )
                                        }
                                    }
                                }
                            }
                        } else {
                            Spacer(modifier = Modifier.size(1.dp))
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))

                // Daily Reminders List
                Text(
                    text = "${Translations.getString("reminders", currentLang)} - $selectedDay ${getMonthName(selectedMonth, currentLang)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                if (selectedDayReminders.isEmpty()) {
                    Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                        Text(Translations.getString("no_reminders_day", currentLang), color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(selectedDayReminders) { reminder ->
                            ReminderAgendaItem(reminder, currentLang)
                        }
                        item {
                            Spacer(modifier = Modifier.height(60.dp))
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun ReminderAgendaItem(reminder: ReminderEntity, lang: AppLanguage) {
    val isCompletedToday = remember(reminder.lastCompleted) {
        if (reminder.lastCompleted == null) false
        else {
            val lastCal = Calendar.getInstance().apply { timeInMillis = reminder.lastCompleted }
            val now = Calendar.getInstance()
            lastCal.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                    lastCal.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(getAgendaCategoryColor(reminder.category).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Alarm,
                    contentDescription = null,
                    tint = getAgendaCategoryColor(reminder.category),
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(reminder.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(
                    "${reminder.timeHour}:${reminder.timeMinute.toString().padStart(2, '0')} - ${Translations.getString("category_${reminder.category.lowercase()}", lang)}",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            if (isCompletedToday) {
                Icon(Icons.Default.Check, contentDescription = "Done", tint = Color.Green)
            }
        }
    }
}

data class DayStatus(val hasActive: Boolean, val hasUrgent: Boolean, val hasPending: Boolean)

private fun getMonthName(month: Int, lang: AppLanguage): String {
    val dfs = DateFormatSymbols(when (lang) { AppLanguage.ES -> Locale("es"); AppLanguage.FR -> Locale("fr"); else -> Locale.ENGLISH })
    return dfs.months[month]
}

private fun getAgendaCategoryColor(category: String): Color {
    return when (category) {
        "Salud" -> Color(0xFFE57373)
        "Trabajo" -> Color(0xFF64B5F6)
        "Estudio" -> Color(0xFF81C784)
        "Hogar" -> Color(0xFFFFB74D)
        "Finanzas" -> Color(0xFF9575CD)
        "Social" -> Color(0xFFF06292)
        else -> Color(0xFF90A4AE)
    }
}

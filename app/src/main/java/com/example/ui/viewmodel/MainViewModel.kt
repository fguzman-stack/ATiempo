package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.AppDatabase
import com.example.data.database.CompletionEntity
import com.example.data.database.JournalEntity
import com.example.data.database.ReminderEntity
import com.example.data.preferences.PreferenceManager
import com.example.data.repository.ReminderRepository
import com.example.ui.translation.AppLanguage
import com.example.util.AlarmScheduler
import com.example.widget.ReminderWidgetProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.math.max

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = ReminderRepository(db.reminderDao(), db.completionDao(), db.journalDao())
    val prefs = PreferenceManager(application)

    // Language & Theme State
    private val _language = MutableStateFlow(prefs.selectedLanguage)
    val language: StateFlow<AppLanguage> = _language.asStateFlow()

    private val _theme = MutableStateFlow(prefs.selectedTheme)
    val theme: StateFlow<String> = _theme.asStateFlow()

    private val _hapticEnabled = MutableStateFlow(prefs.isHapticEnabled)
    val hapticEnabled: StateFlow<Boolean> = _hapticEnabled.asStateFlow()

    // Total completions count
    private val _totalCompletions = MutableStateFlow(prefs.totalCompletions)
    val totalCompletions: StateFlow<Int> = _totalCompletions.asStateFlow()

    // Flag to trigger premium celebration dialog
    private val _showCelebration = MutableStateFlow(false)
    val showCelebration: StateFlow<Boolean> = _showCelebration.asStateFlow()

    private val _showGalacticCelebration = MutableStateFlow(false)
    val showGalacticCelebration: StateFlow<Boolean> = _showGalacticCelebration.asStateFlow()

    // Reminders & Completions
    val reminders: StateFlow<List<ReminderEntity>> = repository.allReminders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val completions: StateFlow<List<CompletionEntity>> = repository.allCompletions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val journals: StateFlow<List<JournalEntity>> = repository.allJournals
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Calculated Analytics
    val bestHour: StateFlow<Int?> = completions.map { list ->
        if (list.isEmpty()) return@map null
        val calendar = Calendar.getInstance()
        list.filter { it.status == "COMPLETED" }
            .map {
                calendar.timeInMillis = it.completedAt
                calendar.get(Calendar.HOUR_OF_DAY)
            }
            .groupBy { it }
            .maxByOrNull { it.value.size }?.key
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val globalStreak: StateFlow<Int> = combine(completions, reminders) { completionsList, remindersList ->
        calculateGlobalStreak(completionsList, remindersList.filter { it.isActive })
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val maxGlobalStreak: StateFlow<Int> = completions.map { list ->
        calculateMaxGlobalStreak(list.filter { it.status == "COMPLETED" })
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)


    init {
        viewModelScope.launch {
            prefs.observeTotalCompletions().collect { _totalCompletions.value = it }
        }
        // Sync widgets upon load
        updateWidgets()
    }

    // Setters
    fun setLanguage(lang: AppLanguage) {
        prefs.selectedLanguage = lang
        _language.value = lang
    }

    fun setTheme(themeStr: String) {
        prefs.selectedTheme = themeStr
        _theme.value = prefs.selectedTheme
    }

    fun setHapticEnabled(enabled: Boolean) {
        prefs.isHapticEnabled = enabled
        _hapticEnabled.value = enabled
    }

    fun dismissCelebration() {
        _showCelebration.value = false
        _showGalacticCelebration.value = false
    }

    // Actions
    fun triggerHapticFeedback() {
        if (!_hapticEnabled.value) return
        val context = getApplication<Application>()
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vm?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
        vibrator?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                it.vibrate(50)
            }
        }
    }

    fun insertReminder(reminder: ReminderEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = repository.insertReminder(reminder)
            val insertedReminder = reminder.copy(id = id.toInt())
            AlarmScheduler.schedule(getApplication(), insertedReminder)
            updateWidgets()
        }
    }

    fun updateReminder(reminder: ReminderEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateReminder(reminder)
            AlarmScheduler.schedule(getApplication(), reminder)
            updateWidgets()
        }
    }

    fun deleteReminder(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteReminderById(id)
            AlarmScheduler.cancel(getApplication(), id)
            updateWidgets()
        }
    }

    fun toggleReminderActive(reminder: ReminderEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = reminder.copy(isActive = !reminder.isActive)
            repository.updateReminder(updated)
            if (updated.isActive) {
                scheduleReminder(updated)
            } else {
                cancelReminder(updated.id)
            }
            updateWidgets()
        }
    }

    private fun scheduleReminder(reminder: ReminderEntity) {
        AlarmScheduler.schedule(getApplication(), reminder)
    }

    private fun cancelReminder(reminderId: Int) {
        AlarmScheduler.cancel(getApplication(), reminderId)
    }

    fun completeReminder(reminder: ReminderEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val previousTotal = prefs.totalCompletions
            val updated = com.example.data.CompletionRecorder.complete(getApplication(), reminder.id)
                ?: return@launch
            val isOnce = updated.recurrence == "ONCE"
            val newTotal = prefs.totalCompletions
            _totalCompletions.value = newTotal

            if (previousTotal < 50 && newTotal >= 50) {
                _showCelebration.value = true
            }

            if (previousTotal < 100 && newTotal >= 100) {
                _showGalacticCelebration.value = true
            }

            AlarmScheduler.cancelFollowUpChecks(getApplication(), reminder.id)
            if (!isOnce) {
                AlarmScheduler.schedule(getApplication(), updated)
            }
            triggerHapticFeedback()
            updateWidgets()
        }
    }

    fun addJournalEntry(content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val now = System.currentTimeMillis()
            val cal = Calendar.getInstance()
            val dateLabel = String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
            val entry = JournalEntity(content = content.trim(), createdAt = now, dateLabel = dateLabel)
            repository.insertJournal(entry)
        }
    }

    fun updateJournalEntry(entry: JournalEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateJournal(entry)
        }
    }

    fun deleteJournalEntry(entry: JournalEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteJournal(entry)
        }
    }

    fun clearAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            // Cancel all alarms
            val list = reminders.value
            for (rem in list) {
                AlarmScheduler.cancel(getApplication(), rem.id)
            }
            repository.clearAllData()
            prefs.clearAll()

            // Restore defaults
            _language.value = AppLanguage.ES
            _theme.value = "system"
            _hapticEnabled.value = true
            _totalCompletions.value = 0
            _showCelebration.value = false
            _showGalacticCelebration.value = false
            updateWidgets()
        }
    }

    fun todayJournalEntry(): JournalEntity? {
        val cal = Calendar.getInstance()
        val today = String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
        return journals.value.find { it.dateLabel == today }
    }

    private fun updateWidgets() {
        ReminderWidgetProvider.updateAllWidgets(getApplication())
    }

    private fun calculateMaxGlobalStreak(completions: List<CompletionEntity>): Int {
        if (completions.isEmpty()) return 0
        val cal = Calendar.getInstance()
        val sortedDates = completions.map {
            cal.timeInMillis = it.completedAt
            String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
        }.distinct().sorted()

        if (sortedDates.isEmpty()) return 0

        var maxStreak = 1
        var currentStreak = 1

        for (i in 1 until sortedDates.size) {
            val calPrev = Calendar.getInstance().apply {
                val parts = sortedDates[i - 1].split("-")
                set(parts[0].toInt(), parts[1].toInt() - 1, parts[2].toInt())
            }
            val calCurr = Calendar.getInstance().apply {
                val parts = sortedDates[i].split("-")
                set(parts[0].toInt(), parts[1].toInt() - 1, parts[2].toInt())
            }
            calPrev.add(Calendar.DAY_OF_YEAR, 1)
            if (calPrev.get(Calendar.YEAR) == calCurr.get(Calendar.YEAR) &&
                calPrev.get(Calendar.DAY_OF_YEAR) == calCurr.get(Calendar.DAY_OF_YEAR)) {
                currentStreak++
                maxStreak = maxOf(maxStreak, currentStreak)
            } else {
                currentStreak = 1
            }
        }

        return maxStreak
    }

    private fun formatDate(cal: Calendar): String {
        return String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
    }

    private fun reminderFiresOnDate(reminder: ReminderEntity, cal: Calendar): Boolean {
        return when (reminder.recurrence) {
            "DAILY" -> true
            "ONCE" -> {
                if (reminder.specificDate == null) false
                else {
                    val specCal = Calendar.getInstance().apply { timeInMillis = reminder.specificDate!! }
                    specCal.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                    specCal.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)
                }
            }
            "WEEKLY" -> {
                val weekDay = cal.get(Calendar.DAY_OF_WEEK)
                val mapped = when (weekDay) {
                    Calendar.MONDAY -> 1; Calendar.TUESDAY -> 2; Calendar.WEDNESDAY -> 3
                    Calendar.THURSDAY -> 4; Calendar.FRIDAY -> 5; Calendar.SATURDAY -> 6
                    Calendar.SUNDAY -> 7; else -> 0
                }
                reminder.recurrenceDays.split(",").any { it.toIntOrNull() == mapped }
            }
            "MONTHLY" -> {
                if (reminder.specificDate == null) false
                else {
                    val specCal = Calendar.getInstance().apply { timeInMillis = reminder.specificDate!! }
                    specCal.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)
                }
            }
            else -> false
        }
    }

    private var lastCompletionsSize = -1

    private fun calculateGlobalStreak(allCompletions: List<CompletionEntity>, activeReminders: List<ReminderEntity>): Int {
        if (allCompletions.isEmpty() || activeReminders.isEmpty()) {
            prefs.currentStreakSnapshot = 0
            return 0
        }

        val cal = Calendar.getInstance()
        val todayStr = formatDate(cal)

        // Usar Snapshot si no hubo nuevas completaciones y seguimos en el mismo día
        if (lastCompletionsSize == allCompletions.size && prefs.lastStreakCalcDate == todayStr) {
            return prefs.currentStreakSnapshot
        }

        val completedDays = allCompletions
            .filter { it.status == "COMPLETED" }
            .map { cal.timeInMillis = it.completedAt; formatDate(cal) }
            .toSet()

        val overdueDays = allCompletions
            .filter { it.status == "OVERDUE" }
            .map { cal.timeInMillis = it.completedAt; formatDate(cal) }
            .toSet()

        var streak = 0
        val checkCal = Calendar.getInstance()
        var daysChecked = 0

        while (true) {
            daysChecked++
            if (daysChecked > 730) break

            val dateStr = formatDate(checkCal)

            if (!activeReminders.any { reminderFiresOnDate(it, checkCal) }) {
                checkCal.add(Calendar.DAY_OF_YEAR, -1)
                continue
            }

            if (completedDays.contains(dateStr)) {
                streak++
                checkCal.add(Calendar.DAY_OF_YEAR, -1)
            } else if (overdueDays.contains(dateStr)) {
                break
            } else {
                if (streak == 0) {
                    checkCal.add(Calendar.DAY_OF_YEAR, -1)
                    continue
                }
                break
            }
        }

        lastCompletionsSize = allCompletions.size
        prefs.lastStreakCalcDate = todayStr
        prefs.currentStreakSnapshot = streak
        return streak
    }


}

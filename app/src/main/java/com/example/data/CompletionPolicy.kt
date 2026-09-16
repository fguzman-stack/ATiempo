package com.example.data

import com.example.data.database.ReminderEntity
import java.util.Calendar

object CompletionPolicy {
    fun canComplete(reminder: ReminderEntity, now: Long): Boolean {
        if (!reminder.isActive) return false
        val today = Calendar.getInstance().apply { timeInMillis = now }
        fun sameDay(timestamp: Long): Boolean {
            val other = Calendar.getInstance().apply { timeInMillis = timestamp }
            return today.get(Calendar.YEAR) == other.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == other.get(Calendar.DAY_OF_YEAR)
        }
        if (reminder.lastCompleted?.let { it > now || sameDay(it) } == true) return false
        val scheduledToday = when (reminder.recurrence) {
            "DAILY" -> true
            "ONCE" -> reminder.specificDate?.let(::sameDay) == true
            "WEEKLY" -> {
                val weekday = (today.get(Calendar.DAY_OF_WEEK) + 5) % 7 + 1
                reminder.recurrenceDays.split(",").any { it.toIntOrNull() == weekday }
            }
            "MONTHLY" -> reminder.specificDate?.let {
                Calendar.getInstance().apply { timeInMillis = it }.get(Calendar.DAY_OF_MONTH) ==
                    today.get(Calendar.DAY_OF_MONTH)
            } == true
            else -> false
        }
        val minutes = today.get(Calendar.HOUR_OF_DAY) * 60 + today.get(Calendar.MINUTE)
        return scheduledToday && minutes >= reminder.timeHour * 60 + reminder.timeMinute
    }
}

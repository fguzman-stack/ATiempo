package com.example

import com.example.data.CompletionPolicy
import com.example.data.database.ReminderEntity
import org.junit.Assert.*
import org.junit.Test
import java.util.Calendar

class CompletionPolicyTest {
    private val now = Calendar.getInstance().apply { set(2026, 8, 15, 12, 0, 0) }.timeInMillis
    private val reminder = ReminderEntity(name = "Test", category = "Otros", timeHour = 11,
        timeMinute = 0, recurrence = "DAILY", recurrenceDays = "", specificDate = null,
        notes = "", vibrate = false)

    @Test fun rejectsEarlyInactiveAndDuplicateCompletions() {
        assertTrue(CompletionPolicy.canComplete(reminder, now))
        assertFalse(CompletionPolicy.canComplete(reminder.copy(timeHour = 13), now))
        assertFalse(CompletionPolicy.canComplete(reminder.copy(isActive = false), now))
        assertFalse(CompletionPolicy.canComplete(reminder.copy(lastCompleted = now - 1000), now))
        assertTrue(CompletionPolicy.canComplete(reminder.copy(lastCompleted = now - 86400000), now))
    }

    @Test fun respectsRecurrenceDates() {
        assertFalse(CompletionPolicy.canComplete(reminder.copy(recurrence = "WEEKLY", recurrenceDays = "1"), now))
        assertTrue(CompletionPolicy.canComplete(reminder.copy(recurrence = "WEEKLY", recurrenceDays = "2"), now))
        assertFalse(CompletionPolicy.canComplete(reminder.copy(recurrence = "ONCE", specificDate = now + 86400000), now))
        assertTrue(CompletionPolicy.canComplete(reminder.copy(recurrence = "ONCE", specificDate = now), now))
    }
}

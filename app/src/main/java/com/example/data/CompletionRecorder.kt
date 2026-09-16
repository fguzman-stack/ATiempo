package com.example.data

import android.content.Context
import androidx.room.withTransaction
import com.example.data.database.AppDatabase
import com.example.data.database.CompletionEntity
import com.example.data.database.ReminderEntity
import com.example.data.preferences.PreferenceManager
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/** Shares duplicate protection between the UI and notification actions. */
object CompletionRecorder {
    private val mutex = Mutex()

    suspend fun complete(context: Context, id: Int): ReminderEntity? = mutex.withLock {
        val db = AppDatabase.getDatabase(context)
        val updated = db.withTransaction {
            val reminder = db.reminderDao().getReminderById(id) ?: return@withTransaction null
            val now = System.currentTimeMillis()
            if (!CompletionPolicy.canComplete(reminder, now)) return@withTransaction null
            val result = reminder.copy(lastCompleted = now,
                isActive = reminder.recurrence != "ONCE")
            db.reminderDao().updateReminder(result)
            db.completionDao().insertCompletion(CompletionEntity(
                reminderId = id, completedAt = now, status = "COMPLETED"))
            result
        }
        if (updated != null) PreferenceManager(context).incrementTotalCompletions()
        updated
    }
}

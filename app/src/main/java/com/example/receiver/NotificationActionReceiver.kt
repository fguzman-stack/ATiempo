package com.example.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.data.database.AppDatabase
import com.example.data.database.CompletionEntity
import com.example.data.preferences.PreferenceManager
import com.example.util.AlarmScheduler
import com.example.widget.ReminderWidgetProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        val reminderId = intent.getIntExtra("reminder_id", -1)
        val name = intent.getStringExtra("reminder_name") ?: "Recordatorio"
        val category = intent.getStringExtra("reminder_category") ?: "Otros"
        val vibrate = intent.getBooleanExtra("vibrate", true)

        if (reminderId == -1) return

        Log.d("NotificationActionReceiver", "Action $action received for reminder $reminderId")

        val db = AppDatabase.getDatabase(context)
        val reminderDao = db.reminderDao()
        val completionDao = db.completionDao()
        val prefs = PreferenceManager(context)

        // Dismiss the notification
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(reminderId)

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
            val reminder = reminderDao.getReminderById(reminderId) ?: return@launch

            when (action) {
                "com.example.action.DONE" -> {
                    val updatedReminder = com.example.data.CompletionRecorder.complete(context, reminderId)
                        ?: return@launch

                    // Cancel follow-up checks (5, 20, 30 min)
                    AlarmScheduler.cancelFollowUpChecks(context, reminderId)

                    if (updatedReminder.isActive) AlarmScheduler.schedule(context, updatedReminder)
                }

                "com.example.action.DEACTIVATE" -> {
                    val updatedReminder = reminder.copy(isActive = false)
                    reminderDao.updateReminder(updatedReminder)

                    completionDao.insertCompletion(
                        CompletionEntity(
                            reminderId = reminderId,
                            completedAt = System.currentTimeMillis(),
                            status = "DEACTIVATED"
                        )
                    )

                    AlarmScheduler.cancel(context, reminderId)
                }
            }

            ReminderWidgetProvider.updateAllWidgets(context)
            } finally {
                pendingResult.finish()
            }
        }
    }

}

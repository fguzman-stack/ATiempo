package com.example.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.data.database.AppDatabase
import com.example.util.AlarmScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RebootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("RebootReceiver", "Device rebooted, rescheduling active reminders...")
            val db = AppDatabase.getDatabase(context)
            val reminderDao = db.reminderDao()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // Collect first emission of reminders list
                    val remindersList = reminderDao.getAllReminders().first()
                    for (reminder in remindersList) {
                        if (reminder.isActive) {
                            AlarmScheduler.schedule(context, reminder)
                        }
                    }
                    Log.d("RebootReceiver", "Successfully rescheduled active reminders.")
                } catch (e: Exception) {
                    Log.e("RebootReceiver", "Failed to reschedule reminders on boot", e)
                }
            }
        }
    }
}

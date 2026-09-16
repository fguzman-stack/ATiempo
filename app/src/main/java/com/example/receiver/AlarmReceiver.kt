package com.example.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.CATEGORY_REMINDER
import android.util.Log
import com.example.data.database.AppDatabase
import com.example.data.database.CompletionEntity
import com.example.data.preferences.PreferenceManager
import com.example.util.AlarmScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getIntExtra("reminder_id", -1)
        val reminderName = intent.getStringExtra("reminder_name") ?: "A Tiempo"
        val reminderCategory = intent.getStringExtra("reminder_category") ?: "Otros"
        val vibrate = intent.getBooleanExtra("reminder_vibrate", true)
        val checkType = intent.getStringExtra("check_type") ?: "initial"

        if (reminderId == -1) return

        Log.d("AlarmReceiver", "=== ALARMA FIRED: $reminderName (check=$checkType) ===")

        if (vibrate) {
            triggerVibration(context)
        }

        when (checkType) {
            "initial" -> {
                showInitialNotification(context, reminderId, reminderName, reminderCategory, vibrate)
                AlarmScheduler.scheduleFollowUpChecks(context, reminderId, reminderName, reminderCategory, vibrate)
            }
            "gentle_5min" -> {
                showGentleNotification(context, reminderId, reminderName, 5)
            }
            "gentle_20min" -> {
                showGentleNotification(context, reminderId, reminderName, 20)
            }
            "overdue_30min" -> {
                handleOverdue(context, reminderId, reminderName, reminderCategory)
            }
        }
    }

    private fun triggerVibration(context: Context) {
        try {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vm?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }

            vibrator?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    it.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    it.vibrate(200)
                }
            }
        } catch (e: Exception) {
            Log.e("AlarmReceiver", "Error vibrando", e)
        }
    }

    private fun showInitialNotification(context: Context, id: Int, name: String, category: String, vibrate: Boolean) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "atiempo_notificaciones"
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        ensureChannel(notificationManager, channelId, soundUri, context)

        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

        val doneIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = "com.example.action.DONE"
            putExtra("reminder_id", id)
            putExtra("reminder_name", name)
            putExtra("reminder_category", category)
            putExtra("vibrate", vibrate)
        }
        val deactivateIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = "com.example.action.DEACTIVATE"
            putExtra("reminder_id", id)
            putExtra("reminder_name", name)
            putExtra("reminder_category", category)
            putExtra("vibrate", vibrate)
        }

        val donePendingIntent = PendingIntent.getBroadcast(context, id * 3 + 1, doneIntent, flags)
        val deactivatePendingIntent = PendingIntent.getBroadcast(context, id * 3 + 3, deactivateIntent, flags)

        val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        val contentPendingIntent = PendingIntent.getActivity(context, id, launchIntent!!, flags)

        val lang = getAppLanguage(context)
        val doneLabel = com.example.ui.translation.Translations.getString("done", lang)
        val deactivateLabel = com.example.ui.translation.Translations.getString("deactivate", lang)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(com.example.R.drawable.ic_notification)
            .setContentTitle(name)
            .setContentText(category)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(CATEGORY_REMINDER)
            .setAutoCancel(true)
            .setContentIntent(contentPendingIntent)
            .setSound(soundUri)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(false)
            .addAction(com.example.R.drawable.ic_notification, doneLabel, donePendingIntent)
            .addAction(com.example.R.drawable.ic_notification, deactivateLabel, deactivatePendingIntent)

        notificationManager.notify(id, builder.build())
        Log.d("AlarmReceiver", "Notificación inicial para $name")
    }

    private fun getAppLanguage(context: Context): com.example.ui.translation.AppLanguage {
        val prefs = com.example.data.preferences.PreferenceManager(context)
        return prefs.selectedLanguage
    }

    private fun showGentleNotification(context: Context, id: Int, name: String, minutes: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelQuiet = "atiempo_recordatorio_suave"
        val lang = getAppLanguage(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelQuiet,
                com.example.ui.translation.Translations.getString("notif_chan_gentle_name", lang),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = com.example.ui.translation.Translations.getString("notif_chan_gentle_desc", lang)
                enableVibration(false)
                setSound(null, null)
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            }
            notificationManager.createNotificationChannel(channel)
        }

        val key = if (minutes == 5) "gentle_5min" else "gentle_20min"
        val body = com.example.ui.translation.Translations.getString(key, lang)

        val builder = NotificationCompat.Builder(context, channelQuiet)
            .setSmallIcon(com.example.R.drawable.ic_notification)
            .setContentTitle(name)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(CATEGORY_REMINDER)
            .setAutoCancel(true)
            .setSilent(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        notificationManager.notify(id * 2 + minutes, builder.build())
        Log.d("AlarmReceiver", "Gentle ${minutes}min para $name")
    }

    private fun handleOverdue(context: Context, reminderId: Int, reminderName: String, reminderCategory: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "atiempo_notificaciones"
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        ensureChannel(notificationManager, channelId, soundUri, context)
        val lang = getAppLanguage(context)

        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(context)
            val reminderDao = db.reminderDao()
            val completionDao = db.completionDao()

            val reminder = reminderDao.getReminderById(reminderId)
            if (reminder == null) {
                Log.w("AlarmReceiver", "Reminder $reminderId not found for overdue")
                return@launch
            }

            // Skip if the user already completed recently (within the last 2 minutes)
            val now = System.currentTimeMillis()
            if (reminder.lastCompleted != null && now - reminder.lastCompleted < 120_000) {
                Log.d("AlarmReceiver", "Skipping overdue — user already completed $reminderName")
                return@launch
            }

            completionDao.insertCompletion(
                CompletionEntity(
                    reminderId = reminderId,
                    completedAt = now,
                    status = "OVERDUE"
                )
            )

            val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            val contentPendingIntent = PendingIntent.getActivity(context, reminderId, launchIntent!!,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(com.example.R.drawable.ic_notification)
                .setContentTitle(com.example.ui.translation.Translations.getString("overdue_title", lang).replace("%s", reminderName))
                .setContentText(com.example.ui.translation.Translations.getString("overdue_body", lang))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(CATEGORY_REMINDER)
                .setAutoCancel(true)
                .setContentIntent(contentPendingIntent)
                .setSound(soundUri)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

            notificationManager.notify(reminderId, builder.build())
            Log.d("AlarmReceiver", "Overdue marcado para $reminderName")
        }
    }

    private fun ensureChannel(notificationManager: NotificationManager, channelId: String, soundUri: android.net.Uri, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val lang = getAppLanguage(context)
            val channel = NotificationChannel(
                channelId,
                com.example.ui.translation.Translations.getString("notif_chan_main_name", lang),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = com.example.ui.translation.Translations.getString("notif_chan_main_desc", lang)
                enableVibration(true)
                enableLights(true)
                setSound(soundUri, AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build())
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}

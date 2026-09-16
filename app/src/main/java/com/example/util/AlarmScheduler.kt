package com.example.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.data.database.ReminderEntity
import com.example.receiver.AlarmReceiver
import java.util.*

object AlarmScheduler {
    private const val TAG = "AlarmScheduler"

    private const val OFFSET_5MIN = 10000
    private const val OFFSET_20MIN = 20000
    private const val OFFSET_30MIN = 30000

    fun schedule(context: Context, reminder: ReminderEntity) {
        if (!reminder.isActive) {
            cancel(context, reminder.id)
            return
        }

        val triggerTime = calculateNextTriggerTime(reminder) ?: run {
            Log.w(TAG, "No trigger time for reminder ${reminder.id} (${reminder.name})")
            return
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("reminder_id", reminder.id)
            putExtra("reminder_name", reminder.name)
            putExtra("reminder_category", reminder.category)
            putExtra("reminder_vibrate", reminder.vibrate)
            putExtra("check_type", "initial")
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        Log.d(TAG, "=== Scheduling reminder ${reminder.id} ===")
        Log.d(TAG, "  triggerTime: ${Date(triggerTime)} (${triggerTime})")
        Log.d(TAG, "  now: ${Date(System.currentTimeMillis())}")
        Log.d(TAG, "  SDK: ${Build.VERSION.SDK_INT}")

        setAlarm(alarmManager, triggerTime, pendingIntent)
        Log.d(TAG, "=== Done scheduling reminder ${reminder.id} ===")
    }

    fun scheduleFollowUpChecks(context: Context, reminderId: Int, reminderName: String, reminderCategory: String, vibrate: Boolean) {
        val now = System.currentTimeMillis()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val fiveMin = now + 5 * 60 * 1000
        val twentyMin = now + 20 * 60 * 1000
        val thirtyMin = now + 30 * 60 * 1000

        val baseIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("reminder_id", reminderId)
            putExtra("reminder_name", reminderName)
            putExtra("reminder_category", reminderCategory)
            putExtra("reminder_vibrate", vibrate)
        }

        val intent5 = Intent(baseIntent).putExtra("check_type", "gentle_5min")
        val pi5 = PendingIntent.getBroadcast(context, reminderId + OFFSET_5MIN, intent5,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        setAlarm(alarmManager, fiveMin, pi5)
        Log.d(TAG, "  → gentle 5min check at ${Date(fiveMin)}")

        val intent20 = Intent(baseIntent).putExtra("check_type", "gentle_20min")
        val pi20 = PendingIntent.getBroadcast(context, reminderId + OFFSET_20MIN, intent20,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        setAlarm(alarmManager, twentyMin, pi20)
        Log.d(TAG, "  → gentle 20min check at ${Date(twentyMin)}")

        val intent30 = Intent(baseIntent).putExtra("check_type", "overdue_30min")
        val pi30 = PendingIntent.getBroadcast(context, reminderId + OFFSET_30MIN, intent30,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        setAlarm(alarmManager, thirtyMin, pi30)
        Log.d(TAG, "  → overdue 30min check at ${Date(thirtyMin)}")
    }

    fun cancelFollowUpChecks(context: Context, reminderId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        cancelPendingIntent(alarmManager, context, reminderId + OFFSET_5MIN, intent)
        cancelPendingIntent(alarmManager, context, reminderId + OFFSET_20MIN, intent)
        cancelPendingIntent(alarmManager, context, reminderId + OFFSET_30MIN, intent)
    }

    private fun cancelPendingIntent(alarmManager: AlarmManager, context: Context, requestCode: Int, intent: Intent) {
        val pi = PendingIntent.getBroadcast(context, requestCode, intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE)
        if (pi != null) {
            alarmManager.cancel(pi)
            pi.cancel()
        }
    }

    private fun setAlarm(alarmManager: AlarmManager, triggerTime: Long, pendingIntent: PendingIntent) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
                } else {
                    alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
                }
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            }
            else -> {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            }
        }
    }

    fun cancel(context: Context, reminderId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        cancelPendingIntent(alarmManager, context, reminderId, intent)
        cancelFollowUpChecks(context, reminderId)
    }

    fun calculateNextTriggerTime(reminder: ReminderEntity): Long? {
        val now = Calendar.getInstance()
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, reminder.timeHour)
            set(Calendar.MINUTE, reminder.timeMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        when (reminder.recurrence) {
            "ONCE" -> {
                if (reminder.specificDate != null) {
                    val specCal = Calendar.getInstance().apply {
                        timeInMillis = reminder.specificDate
                    }
                    calendar.set(Calendar.YEAR, specCal.get(Calendar.YEAR))
                    calendar.set(Calendar.MONTH, specCal.get(Calendar.MONTH))
                    calendar.set(Calendar.DAY_OF_MONTH, specCal.get(Calendar.DAY_OF_MONTH))
                }
                if (calendar.before(now)) return null
                return calendar.timeInMillis
            }
            "DAILY" -> {
                if (calendar.before(now)) {
                    calendar.add(Calendar.DAY_OF_YEAR, 1)
                }
                return calendar.timeInMillis
            }
            "WEEKLY" -> {
                val daysList = reminder.recurrenceDays.split(",")
                    .filter { it.isNotEmpty() }
                    .mapNotNull { it.toIntOrNull() }
                if (daysList.isEmpty()) return null

                val mappedSelectedDays = daysList.map { day ->
                    when (day) {
                        7 -> Calendar.SUNDAY
                        1 -> Calendar.MONDAY
                        2 -> Calendar.TUESDAY
                        3 -> Calendar.WEDNESDAY
                        4 -> Calendar.THURSDAY
                        5 -> Calendar.FRIDAY
                        6 -> Calendar.SATURDAY
                        else -> Calendar.MONDAY
                    }
                }

                var minTrigger: Long? = null
                for (mappedDay in mappedSelectedDays) {
                    val tempCal = Calendar.getInstance().apply {
                        timeInMillis = calendar.timeInMillis
                    }
                    val todayDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK)
                    var daysDiff = mappedDay - todayDayOfWeek
                    if (daysDiff < 0 || (daysDiff == 0 && tempCal.before(now))) {
                        daysDiff += 7
                    }
                    tempCal.add(Calendar.DAY_OF_YEAR, daysDiff)
                    val trigger = tempCal.timeInMillis
                    if (minTrigger == null || trigger < minTrigger) {
                        minTrigger = trigger
                    }
                }
                return minTrigger
            }
            "MONTHLY" -> {
                if (reminder.specificDate != null) {
                    val specCal = Calendar.getInstance().apply {
                        timeInMillis = reminder.specificDate
                    }
                    calendar.set(Calendar.DAY_OF_MONTH, specCal.get(Calendar.DAY_OF_MONTH))
                }
                if (calendar.before(now)) {
                    calendar.add(Calendar.MONTH, 1)
                }
                return calendar.timeInMillis
            }
            else -> return null
        }
    }
}

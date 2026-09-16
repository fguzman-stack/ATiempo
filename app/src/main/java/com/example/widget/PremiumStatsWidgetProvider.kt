package com.example.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.widget.RemoteViews
import com.example.R
import com.example.data.database.AppDatabase
import com.example.ui.translation.AppLanguage
import com.example.ui.translation.Translations
import com.example.util.AlarmScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar

class PremiumStatsWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        updateWidgets(context, appWidgetManager, appWidgetIds)
    }

    companion object {
        fun updateAllWidgets(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisWidget = ComponentName(context, PremiumStatsWidgetProvider::class.java)
            val allIds = appWidgetManager.getAppWidgetIds(thisWidget)
            updateWidgets(context, appWidgetManager, allIds)
        }

        private fun updateWidgets(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
            val db = AppDatabase.getDatabase(context)
            val prefs: SharedPreferences = context.getSharedPreferences("atiempo_prefs", Context.MODE_PRIVATE)
            val langCode = prefs.getString("selected_language", "es") ?: "es"
            val lang = AppLanguage.values().find { it.code == langCode } ?: AppLanguage.ES
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val reminders = db.reminderDao().getAllReminders().first()
                    val activeReminders = reminders.filter { it.isActive }
                    
                    val nowMs = System.currentTimeMillis()
                    val thirtyMinutesMs = 30 * 60 * 1000

                    var activeCount = activeReminders.size
                    var urgentCount = 0
                    var pendingCount = 0

                    val nowCal = Calendar.getInstance()

                    for (reminder in activeReminders) {
                        val trigger = AlarmScheduler.calculateNextTriggerTime(reminder)
                        if (trigger != null) {
                            val diff = trigger - nowMs
                            if (diff in 0..thirtyMinutesMs) {
                                urgentCount++
                            }
                        }

                        val lastComp = reminder.lastCompleted
                        val completedToday = if (lastComp != null) {
                            val lastCal = Calendar.getInstance().apply { timeInMillis = lastComp }
                            nowCal.get(Calendar.YEAR) == lastCal.get(Calendar.YEAR) &&
                                    nowCal.get(Calendar.DAY_OF_YEAR) == lastCal.get(Calendar.DAY_OF_YEAR)
                        } else {
                            false
                        }

                        if (!completedToday) {
                            pendingCount++
                        }
                    }

                    for (appWidgetId in appWidgetIds) {
                        val views = RemoteViews(context.packageName, R.layout.widget_premium_stats)
                        views.setTextViewText(R.id.widget_stats_title, Translations.getString("widget_title_stats", lang))
                        views.setTextViewText(R.id.widget_stats_active, activeCount.toString())
                        views.setTextViewText(R.id.widget_label_active, Translations.getString("widget_label_active", lang))
                        views.setTextViewText(R.id.widget_stats_urgent, urgentCount.toString())
                        views.setTextViewText(R.id.widget_label_urgent, Translations.getString("widget_label_urgent", lang))
                        views.setTextViewText(R.id.widget_stats_pending, pendingCount.toString())
                        views.setTextViewText(R.id.widget_label_pending, Translations.getString("widget_label_pending", lang))
                        appWidgetManager.updateAppWidget(appWidgetId, views)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

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

class ReminderWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        updateWidgets(context, appWidgetManager, appWidgetIds)
    }

    companion object {
        fun updateAllWidgets(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisWidget = ComponentName(context, ReminderWidgetProvider::class.java)
            val allIds = appWidgetManager.getAppWidgetIds(thisWidget)
            updateWidgets(context, appWidgetManager, allIds)

            // Cascade update to premium stats widget as well
            PremiumStatsWidgetProvider.updateAllWidgets(context)
        }

        private fun updateWidgets(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
            val db = AppDatabase.getDatabase(context)
            val prefs: SharedPreferences = context.getSharedPreferences("atiempo_prefs", Context.MODE_PRIVATE)
            val langCode = prefs.getString("selected_language", "es") ?: "es"
            val lang = AppLanguage.values().find { it.code == langCode } ?: AppLanguage.ES
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val reminders = db.reminderDao().getAllReminders().first()
                    val active = reminders.filter { it.isActive }
                    
                    val nextReminder = active.mapNotNull { reminder ->
                        val nextTrigger = AlarmScheduler.calculateNextTriggerTime(reminder)
                        if (nextTrigger != null) {
                            reminder to nextTrigger
                        } else {
                            null
                        }
                    }.minByOrNull { it.second }?.first

                    for (appWidgetId in appWidgetIds) {
                        val views = RemoteViews(context.packageName, R.layout.widget_reminder)
                        views.setTextViewText(R.id.widget_title, Translations.getString("widget_title_reminder", lang))
                        if (nextReminder != null) {
                            views.setTextViewText(R.id.widget_reminder_name, nextReminder.name)
                            val catTranslated = Translations.getString("category_${nextReminder.category.lowercase()}", lang)
                            val formattedTime = String.format("%02d:%02d • %s", nextReminder.timeHour, nextReminder.timeMinute, catTranslated)
                            views.setTextViewText(R.id.widget_reminder_time, formattedTime)
                        } else {
                            views.setTextViewText(R.id.widget_reminder_name, Translations.getString("widget_fallback_name", lang))
                            views.setTextViewText(R.id.widget_reminder_time, Translations.getString("widget_fallback_sub", lang))
                        }
                        appWidgetManager.updateAppWidget(appWidgetId, views)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

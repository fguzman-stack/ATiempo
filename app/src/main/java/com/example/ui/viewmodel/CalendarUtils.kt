package com.example.ui.viewmodel

import java.util.Calendar

object CalendarUtils {

    private const val MILLIS_PER_DAY = 24 * 60 * 60 * 1000L
    private const val MINUTES_PER_HOUR = 60
    private const val SECONDS_PER_MINUTE = 60
    private const val MILLIS_PER_SECOND = 1000L

    fun cleanCalendar(calendar: Calendar): Calendar {
        val cal = Calendar.getInstance()
        cal.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
            0, 0, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal
    }

    fun formatDay(calendar: Calendar): String {
        return String.format("%04d-%02d-%02d", 
            calendar.get(Calendar.YEAR), 
            calendar.get(Calendar.MONTH) + 1, 
            calendar.get(Calendar.DAY_OF_MONTH))
    }

    fun createDayCalendar(millis: Long): Calendar {
        val cal = Calendar.getInstance()
        cal.timeInMillis = millis
        return cal
    }

    fun createCalendarFromDateString(dateStr: String): Calendar {
        val cal = Calendar.getInstance()
        val parts = dateStr.split("-")
        cal.set(parts[0].toInt(), parts[1].toInt() - 1, parts[2].toInt())
        return cal
    }

    fun calculateDaysDifference(cal1: Calendar, cal2: Calendar): Int {
        val c1 = cleanCalendar(cal1)
        val c2 = cleanCalendar(cal2)
        val diff = c2.timeInMillis - c1.timeInMillis
        return (diff / MILLIS_PER_DAY).toInt()
    }

    fun getYesterdayTimestamp(): Long {
        return Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }.timeInMillis
    }

    fun calculateGlobalStreakFromDays(days: Set<String>): Int {
        var streak = 0
        val checkCal = Calendar.getInstance()

        // Check if there was completion today
        var currentDayStr = formatDay(checkCal)
        if (days.contains(currentDayStr)) {
            streak++
            checkCal.add(Calendar.DAY_OF_YEAR, -1)
        } else {
            // If not today, check if completed yesterday. If so, start counting from yesterday
            checkCal.add(Calendar.DAY_OF_YEAR, -1)
            val yesterdayStr = formatDay(checkCal)
            if (days.contains(yesterdayStr)) {
                streak++
                checkCal.add(Calendar.DAY_OF_YEAR, -1)
            } else {
                return 0
            }
        }

        while (true) {
            val dateStr = formatDay(checkCal)
            if (days.contains(dateStr)) {
                streak++
                checkCal.add(Calendar.DAY_OF_YEAR, -1)
            } else {
                break
            }
        }

        return streak
    }
}
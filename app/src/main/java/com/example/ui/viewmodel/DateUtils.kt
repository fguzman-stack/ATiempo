package com.example.ui.viewmodel

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {

    private const val DATE_PATTERN = "dd/MM/yyyy"
    private const val DATE_PATTERN_EU = "dd/MM/yyyy • HH:mm:ss"
    private const val DATETIME_PATTERN = "dd MMM, HH:mm"
    private const val DATETIME_PATTERN_EU = "dd/MM/yyyy • HH:mm:ss"
    private const val SHORT_TIME_PATTERN = "HH:mm:ss"
    private const val LONG_DATE_PATTERN = "EEEE, d MMMM yyyy"

    fun format(date: Date): String {
        val format = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
        return format.format(date)
    }

    fun format(date: Date, pattern: String): String {
        val format = SimpleDateFormat(pattern, Locale.getDefault())
        return format.format(date)
    }

    fun getDefaultPattern(): String = DATE_PATTERN
    fun getDisplayPattern(): String = DATE_PATTERN_EU
    fun getShortTimePattern(): String = SHORT_TIME_PATTERN
    fun getLongDatePattern(): String = LONG_DATE_PATTERN

    fun createDateFormatter(pattern: String): SimpleDateFormat {
        return SimpleDateFormat(pattern, Locale.getDefault())
    }

    fun formatCalendarDay(year: Int, month: Int, day: Int): String {
        return String.format("%04d-%02d-%02d", year, month + 1, day)
    }

    fun getTodayDateString(): String {
        val calendar = java.util.Calendar.getInstance()
        return formatCalendarDay(calendar.get(java.util.Calendar.YEAR), 
            calendar.get(java.util.Calendar.MONTH), 
            calendar.get(java.util.Calendar.DAY_OF_MONTH))
    }
}
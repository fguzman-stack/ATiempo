package com.example.ui.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar
import java.util.HashSet

class StreakCalculationTest {

    @Test
    fun `test_calculateGlobalStreakFromDays_emptySet_returnsZero`() {
        val days = HashSet<String>()
        val result = CalendarUtils.calculateGlobalStreakFromDays(days)
        assertEquals(0, result)
    }

    @Test
    fun `test_calculateGlobalStreakFromDays_singleDay_returnsOne`() {
        val today = CalendarUtils.formatDay(Calendar.getInstance())
        val days = HashSet<String>().apply { add(today) }
        val result = CalendarUtils.calculateGlobalStreakFromDays(days)
        assertEquals(1, result)
    }

    @Test
    fun `test_calculateGlobalStreakFromDays_consecutiveDays_returnsCorrectStreak`() {
        val today = Calendar.getInstance()
        val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
        val days = HashSet<String>().apply {
            add(CalendarUtils.formatDay(today))
            add(CalendarUtils.formatDay(yesterday))
        }
        val result = CalendarUtils.calculateGlobalStreakFromDays(days)
        assertEquals(2, result)
    }

    @Test
    fun `test_calculateGlobalStreakFromDays_brokenStreak_returnsSingleDayStreak`() {
        val today = Calendar.getInstance()
        val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
        val twoDaysAgo = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -3) }
        val days = HashSet<String>().apply {
            add(CalendarUtils.formatDay(today))
            add(CalendarUtils.formatDay(yesterday))
            add(CalendarUtils.formatDay(twoDaysAgo))
        }
        val result = CalendarUtils.calculateGlobalStreakFromDays(days)
        assertEquals(2, result)
    }

    @Test
    fun `test_calculateGlobalStreakFromDays_threeDayStreak_returnsThree`() {
        val today = Calendar.getInstance()
        val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
        val twoDaysAgo = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -2) }
        val days = HashSet<String>().apply {
            add(CalendarUtils.formatDay(today))
            add(CalendarUtils.formatDay(yesterday))
            add(CalendarUtils.formatDay(twoDaysAgo))
        }
        val result = CalendarUtils.calculateGlobalStreakFromDays(days)
        assertEquals(3, result)
    }

    @Test
    fun `test_calculateGlobalStreakFromDays_noTodayButYesterday_returnsOne`() {
        val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
        val days = HashSet<String>().apply { add(CalendarUtils.formatDay(yesterday)) }
        val result = CalendarUtils.calculateGlobalStreakFromDays(days)
        assertEquals(1, result)
    }

    @Test
    fun `test_calculateGlobalStreakFromDays_noYesterday_returnsZero`() {
        val twoDaysAgo = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -2) }
        val days = HashSet<String>().apply { add(CalendarUtils.formatDay(twoDaysAgo)) }
        val result = CalendarUtils.calculateGlobalStreakFromDays(days)
        assertEquals(0, result)
    }

    @Test
    fun `test_calculateDaysDifference_sameDay_returnsZero`() {
        val calendar = Calendar.getInstance()
        val result = CalendarUtils.calculateDaysDifference(calendar, calendar)
        assertEquals(0, result)
    }

    @Test
    fun `test_calculateDaysDifference_oneDayDifference_returnsOne`() {
        val calendar1 = Calendar.getInstance()
        val calendar2 = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }
        val result = CalendarUtils.calculateDaysDifference(calendar1, calendar2)
        assertEquals(1, result)
    }

    @Test
    fun `test_calculateDaysDifference_negativeDifference_returnsNegative`() {
        val calendar1 = Calendar.getInstance()
        val calendar2 = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
        val result = CalendarUtils.calculateDaysDifference(calendar1, calendar2)
        assertEquals(-1, result)
    }

    @Test
    fun `test_formatDay_correctFormat_returnsFormattedString`() {
        val calendar = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 15)
        }
        val result = CalendarUtils.formatDay(calendar)
        assertEquals("2024-01-15", result)
    }

    @Test
    fun `test_createDayCalendar_fromMillis_createsCorrectCalendar`() {
        val millis = 1705329600000L
        val result = CalendarUtils.createDayCalendar(millis)
        assertEquals(2024, result.get(Calendar.YEAR))
        assertEquals(Calendar.JANUARY, result.get(Calendar.MONTH))
        assertEquals(15, result.get(Calendar.DAY_OF_MONTH))
    }

    @Test
    fun `test_createCalendarFromDateString_createsCorrectCalendar`() {
        val result = CalendarUtils.createCalendarFromDateString("2024-01-15")
        assertEquals(2024, result.get(Calendar.YEAR))
        assertEquals(Calendar.JANUARY, result.get(Calendar.MONTH))
        assertEquals(15, result.get(Calendar.DAY_OF_MONTH))
    }

    @Test
    fun `test_getYesterdayTimestamp_returnsCorrectTimestamp`() {
        val before = System.currentTimeMillis()
        val result = CalendarUtils.getYesterdayTimestamp()
        val after = System.currentTimeMillis()
        
        assert(result in (before - 86400000)..(after - 86400000)) { "Result should be yesterday's timestamp" }
    }
}
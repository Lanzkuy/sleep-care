package com.lans.sleep_care.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

fun splitDatesByWeek(dates: List<String>): List<List<String>> {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val sortedDates = dates.sorted()

    val weeks = mutableListOf<MutableList<String>>()
    var currentWeek = mutableListOf<String>()
    var lastWeekNumber = -1

    for (dateStr in sortedDates) {
        val date = format.parse(dateStr)
        val calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
            val weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)

            if (weekOfYear != lastWeekNumber) {
                if (currentWeek.isNotEmpty()) {
                    weeks.add(currentWeek)
                }
                currentWeek = mutableListOf(dateStr)
                lastWeekNumber = weekOfYear
            } else {
                currentWeek.add(dateStr)
            }
        }
    }

    if (currentWeek.isNotEmpty()) {
        weeks.add(currentWeek)
    }

    return weeks
}

fun generateWeeklyRanges(startDate: String, endDate: String): List<Pair<String, String>> {
    val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    isoFormat.timeZone = TimeZone.getTimeZone("UTC")

    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val start = isoFormat.parse(startDate)
    val end = isoFormat.parse(endDate)

    val weeklyRanges = mutableListOf<Pair<String, String>>()
    val calendar = Calendar.getInstance()

    if (start != null && end != null) {
        calendar.time = start

        while (!calendar.time.after(end)) {
            val weekStart = calendar.time
            calendar.add(Calendar.DATE, 6)
            val weekEnd = if (calendar.time.after(end)) end else calendar.time

            weeklyRanges.add(Pair(outputFormat.format(weekStart), outputFormat.format(weekEnd)))

            calendar.add(Calendar.DATE, 1)
        }
    }

    return weeklyRanges
}

fun parseToDate(dateStr: String): String {
    if (dateStr.isEmpty()) return ""
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = inputFormat.parse(dateStr)
    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    return outputFormat.format(date!!)
}

fun parseToDayName(dateStr: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC")
    val date = format.parse(dateStr) ?: return ""
    val dayFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id"))
    return dayFormat.format(date)
}
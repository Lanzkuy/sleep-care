package com.lans.sleep_care.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

fun generateDateRange(startDate: String, endDate: String): List<String> {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val start = format.parse(startDate)
    val end = format.parse(endDate)

    val dates = mutableListOf<String>()
    val calendar = Calendar.getInstance()

    if (start != null && end != null) {
        calendar.time = start
        while (!calendar.time.after(end)) {
            dates.add(format.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
    }

    return dates
}

fun parseToDate(dateStr: String): String {
    if (dateStr.isEmpty()) return ""
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = inputFormat.parse(dateStr)
    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    return outputFormat.format(date!!)
}

fun parseToDayName(dateStr: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = format.parse(dateStr) ?: return ""
    val dayFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id"))
    return dayFormat.format(date)
}
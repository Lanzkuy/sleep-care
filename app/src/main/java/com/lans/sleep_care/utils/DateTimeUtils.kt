package com.lans.sleep_care.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

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
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val start = inputFormat.parse(startDate)
    val end = inputFormat.parse(endDate)

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
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = inputFormat.parse(dateStr) ?: return ""
    val dayFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id"))
    return dayFormat.format(date)
}

fun parseToTime(datetime: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    val date = inputFormat.parse(datetime)
    return outputFormat.format(date ?: Date())
}

fun formatToTime(input: String): String {
    val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val time = inputFormat.parse(input)
    return outputFormat.format(time!!)
}

fun formatChatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val messageDate = inputFormat.parse(dateString) ?: return ""
    val messageCalendar = Calendar.getInstance().apply { time = messageDate }

    val todayCalendar = Calendar.getInstance()
    val yesterdayCalendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }

    return when {
        isSameDay(messageCalendar, todayCalendar) -> "Today"
        isSameDay(messageCalendar, yesterdayCalendar) -> "Yesterday"
        daysBetween(messageCalendar, todayCalendar) < 7 -> {
            SimpleDateFormat("EEEE", Locale.getDefault()).format(messageDate) // e.g. Monday
        }

        else -> {
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(messageDate)
        }
    }
}

fun isSameDay(firstDate: Calendar, secondDate: Calendar): Boolean {
    return firstDate.get(Calendar.YEAR) == secondDate.get(Calendar.YEAR) &&
            firstDate.get(Calendar.DAY_OF_YEAR) == secondDate.get(Calendar.DAY_OF_YEAR)
}

fun daysBetween(startDate: Calendar, endDate: Calendar): Long {
    val start = Calendar.getInstance().apply {
        timeInMillis = startDate.timeInMillis
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    val end = Calendar.getInstance().apply {
        timeInMillis = endDate.timeInMillis
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    val diffMillis = end.timeInMillis - start.timeInMillis
    return TimeUnit.MILLISECONDS.toDays(diffMillis)
}

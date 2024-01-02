package com.example.ticketmachinemobile.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*


object DateUtil {
    fun generateDateAndDayLists(): Pair<List<String>, List<String>> {
        val currentDate = LocalDate.now()
        val dateList = mutableListOf<String>()
        val dayList = mutableListOf<String>()

        val formatter = DateTimeFormatter.ofPattern("M月d日", Locale.CHINA)

        for (i in 0 until 14) {
            val date = currentDate.plusDays(i.toLong())
            val formattedDate = date.format(formatter)
            val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.CHINA)

            dateList.add(formattedDate)
            dayList.add(dayOfWeek)
        }

        return dateList to dayList
    }

    fun main() {
        val (dateList, dayList) = generateDateAndDayLists()

        println("日期列表：$dateList")
        println("星期几列表：$dayList")
    }

    /**
     * 获取当前的日期并格式化为 yyyy-MM-dd
     */
    fun getTodayDateString(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA)
        return currentDate.format(formatter)
    }

    /**
     * 传入一个LocalTime，将其格式化为 Hh:mm
     */
    fun formatTimeHHMM(localTime: LocalTime): String {
        val formatter = DateTimeFormatter.ofPattern("Hh:mm", Locale.CHINA)
        return localTime.format(formatter)
    }

    /**
     * 传入一个 HH:mm:ss ，将其格式化为 HH:mm
     */
    fun formatTimeHHMM(time: String): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.CHINA)
        val localTime = LocalTime.parse(time, formatter)
        return formatTimeHHMM(localTime)
    }
}
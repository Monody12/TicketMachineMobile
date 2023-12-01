package com.example.ticketmachinemobile.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*


class DateUtil {
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
}
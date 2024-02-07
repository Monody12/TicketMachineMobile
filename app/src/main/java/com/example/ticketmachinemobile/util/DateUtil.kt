package com.example.ticketmachinemobile.util

import androidx.compose.runtime.remember
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*


object DateUtil {
    /**
     * 生成日期和星期列表
     * 依次返回： dateList、weekList、formatStringList
     */
    fun generateDateAndDayLists() : Triple<List<String>, List<String>, List<String>> {
        val dateList =  mutableListOf<String>()
        val weekList = mutableListOf<String>()
        // 格式为 yyyy-MM-dd
        val formatStringList = mutableListOf<String>()

        val currentDate =  LocalDate.now()
        val formatter =  DateTimeFormatter.ofPattern("M月d日")
        val locale = Locale("zh", "CN")

        for (i in 0 until 28) {
            val date = currentDate.plusDays(i.toLong())
            val formattedDate = date.format(formatter)
            val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, locale)

            dateList.add(formattedDate)
            weekList.add(dayOfWeek)
            formatStringList.add(DateUtil.formatDate(date))
        }
        return Triple(dateList, weekList, formatStringList)
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
        val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.CHINA)
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

    /**
     * 传入一个LocalDate，将其格式化为 yyyy-MM-dd
     */
    fun formatDate(localDate: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA)
        return localDate.format(formatter)
    }

    /**
     * 获取当前日期，将其格式化为 yyyy-MM-dd
     */
    fun getTodayDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA)
        return currentDate.format(formatter)
    }
}
package com.example.ticketmachinemobile.data

data class ShiftCheckType(
    val id: Int,
    val name: String
)

object ShiftCheckTypeList {
    val data = listOf(
        ShiftCheckType(1, "全部排班"),
        ShiftCheckType(2, "待检班次"),
        ShiftCheckType(3, "已检班次")
    )
}
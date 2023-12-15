package com.example.ticketmachinemobile.data

data class TicketType(
    val int: Int,
    val name: String,
){
    constructor(): this(0, "" )
}

fun getTicketTypeList(): List<TicketType> {
    return listOf(
        TicketType(1, "成人票"),
        TicketType(2, "儿童票"),
        TicketType(3, "15元优惠票"),
    )
}
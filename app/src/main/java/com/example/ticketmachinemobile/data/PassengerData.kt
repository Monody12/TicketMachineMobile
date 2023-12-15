package com.example.ticketmachinemobile.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Passenger(
    /**
     * 证件类型
     */
    var idType: String?,
    /**
     * 证件号码
     */
    var idNumber: String?,
    /**
     * 姓名
     */
    var name: String?,
    /**
     * 选中的票种
     */
    var ticketType: MutableState<TicketType>
) {
    constructor() : this("", "", "", mutableStateOf(TicketType()))
    constructor(idType: String?, idNumber: String?, name: String?) : this(idType, idNumber, name, mutableStateOf(TicketType()))
}


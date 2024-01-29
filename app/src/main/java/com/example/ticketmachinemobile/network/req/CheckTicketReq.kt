package com.example.ticketmachinemobile.network.req

data class CheckTicketReq(
    var orderNo : String?,
    var shiftId : Int?,
    var idCard: String?
)
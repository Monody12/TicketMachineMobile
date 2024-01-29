package com.example.ticketmachinemobile.network.resp

import com.example.ticketmachinemobile.data.Passenger
import com.google.gson.annotations.SerializedName

data class CheckShiftResp(
    val orderNo : String,
    val routeName : String,
    @SerializedName("staringTime")
    val startingTime : String,
    val passengerList: List<PassengerResp>
)

data class PassengerResp(
    val passengerName: String,
//    val idCard: String,
    val ticketId: Int,
    val id: Int,
//    val phone: String?,
    val orderNo: String,
    val cardType: Int,
    val ticketNum: Int,
    val ticketPrice: Int,
    val ticketCheckingStatus: Int,
    val shiftId: Int
)
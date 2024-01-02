package com.example.ticketmachinemobile

import com.google.gson.annotations.SerializedName

data class ShiftInfo(
    @SerializedName("id")
    var id: Int,
    @SerializedName("routeId")
    var routeId: Int,
    @SerializedName("name")
    var name: String?,
    @SerializedName("routeName")
    var routeName: String,
    @SerializedName("startDate")
    var startDate: String,
    @SerializedName("startTime")
    var startTime: String,
    @SerializedName("startEndTime")
    var startEndTime: String?,
    @SerializedName("soldSeatNum")
    var soldSeatNum: Int,
    @SerializedName("saleSeatNum")
    var saleSeatNum: Int,
    @SerializedName("checkTicket")
    var checkTicket: Int,
    @SerializedName("stationList")
    var stationList: List<Station>
){
    constructor() : this(0,0,"","", "", "", "", 0, 0, 0, emptyList())
}

data class Station(
    @SerializedName("stationName")
    var stationName: String,
    @SerializedName("arrivarTime")
    var arrivarTime: String,
    @SerializedName("price")
    var price: Int
)

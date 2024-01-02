package com.example.ticketmachinemobile.network.resp

import com.google.gson.annotations.SerializedName

data class ShiftInfo(
    /**
     * 班次id
     */
    @SerializedName("id")
    var id: Int,
    /**
     * 线路id
     */
    @SerializedName("routeId")
    var routeId: Int,
    /**
     * 班次名称
     */
    @SerializedName("name")
    var name: String?,
    /**
     * 线路名称
     */
    @SerializedName("routeName")
    var routeName: String,
    /**
     * 班次发车日期
     */
    @SerializedName("startDate")
    var startDate: String,
    /**
     * 班次发车时间
     */
    @SerializedName("startTime")
    var startTime: String,
    /**
     * 班次停发时间
     * 流水班用
     */
    @SerializedName("startEndTime")
    var startEndTime: String?,
    /**
     * 已售票
     */
    @SerializedName("soldSeatNum")
    var soldSeatNum: Int,
    /**
     * 余票
     */
    @SerializedName("saleSeatNum")
    var saleSeatNum : Int,
    /**
     * 已检票
     */
    @SerializedName("checkTicket")
    var checkTicket: Int,
    /**
     * 站点列表
     */
    @SerializedName("stationList")
    var stationList: List<Station>
){
    constructor() : this(0,0,"","", "", "", "", 0, 0, 0, emptyList())
}

data class Station(

    /**
     * 站点id
     */
    @SerializedName("id")
    var id: Int,
    /**
     * 站点名称
     */
    @SerializedName("stationName")
    var stationName: String,
    /**
     * 到达时间
     */
    @SerializedName("arrivalTime")
    var arrivalTime: String,
    /**
     * 票价
     */
    @SerializedName("price")
    var price: Int,
    /**
     * 上车或下车人数
     */
    @SerializedName("personCount")
    var personCount : Int = 0
)

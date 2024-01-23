package com.example.ticketmachinemobile.network.resp

import androidx.compose.runtime.saveable.Saver
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
     * 司机名称
     */
    @SerializedName("driverName")
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
    @SerializedName("stationId")
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
){
    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Station) {
            other.id == id
        } else {
            false
        }
    }

    constructor() : this(
        id = 0,
        stationName = "",
        arrivalTime = "",
        price = 0,
        personCount = 0
    )

    constructor(id: Int, stationName: String) : this(
        id = id,
        stationName = stationName,
        arrivalTime = "",
        price = 0,
        personCount = 0
    )
}

val StationSaver: Saver<Station, *> = Saver(
    save = { station ->
        // 将 Station 对象转换为可保存的形式，比如使用 Bundle 支持的类型
        listOf(station.id, station.stationName, station.arrivalTime, station.price, station.personCount)
    },
    restore = { list ->
        // 将保存的数据恢复为 Station 对象
        Station(
            id = list[0] as Int,
            stationName = list[1] as String,
            arrivalTime = list[2] as String,
            price = list[3] as Int,
            personCount = list[4] as Int
        )
    }
)

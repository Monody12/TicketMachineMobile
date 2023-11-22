package com.example.ticketmachinemobile.data

/**
 * 站点信息
 * 站点名称、上车人数、下车人数、到站时间
 */
data class Station(
    var stationName: String,
    var getOnCount: Int,
    var getOffCount: Int,
    var arriveTime: String
)

/**
 * 线路信息
 * 线路名称、线路站点列表
 */
data class LineListData (
    var lineName: String,
    var stationList: List<Station>
)

object LineListDataRepository {
    var lineListData: LineListData? = null

    fun getSimpleLineList(): LineListData {
        return LineListData(
            lineName = "1路",
            stationList = listOf(
                Station("站点1", 45, 0, "10:00"),
                Station("站点2", 0, 10, "10:10"),
                Station("站点3", 0, 15, "10:20"),
                Station("站点4", 0, 20, "10:30"),
            )
        )
    }
}

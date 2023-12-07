package com.example.ticketmachinemobile.data

/**
 * 站点信息
 * 站点名称、上车人数、下车人数、到站时间
 */
data class Station(
    var id : Int = 0,
    var stationName: String,
    var getOnCount: Int,
    var getOffCount: Int,
    var arriveTime: String
)

/**
 * 线路信息
 * 线路名称、线路站点列表
 */
data class LineData (
    var lineName: String,
    var stationList: List<Station>
)

object LineDataRepository {
    var lineData: LineData? = null

    fun getSimpleLine(): LineData {
        return LineData(
            lineName = "测试用线路名称",
            stationList = listOf(
                Station(id = 1, stationName = "站点1", getOnCount = 45, getOffCount = 0, arriveTime = "10:00"),
                Station(id = 2, stationName = "站点2", getOnCount = 0, getOffCount = 10, arriveTime = "10:10"),
                Station(id = 3, stationName = "站点3", getOnCount = 0, getOffCount = 15, arriveTime = "10:20"),
                Station(id = 4, stationName = "站点4", getOnCount = 0, getOffCount = 20, arriveTime = "10:30")
            )
        )
    }

    fun getSimpleLineList() :List<LineData>{
        val lineList = mutableListOf<LineData>()
        for (i in 1..4){
            lineList.add(getSimpleLine())
        }
        return lineList
    }
}

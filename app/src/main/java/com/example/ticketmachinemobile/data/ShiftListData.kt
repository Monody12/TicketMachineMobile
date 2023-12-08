package com.example.ticketmachinemobile.data

/**
 * 班次
 * 线路信息、发车时间、乘客数量、携童数量、已检票数量、班次状态
 */
data class ShiftData(
    var id: Int?,
    var lineData: LineData?,
    var departureTime: String?,
    var passengerCount: Int?,
    var childCount: Int?,
    var checkedCount: Int?,
    var shiftStatus: String?
){
    constructor() : this(
        id = null,
        lineData = null,
        departureTime = null,
        passengerCount = null,
        childCount = null,
        checkedCount = null,
        shiftStatus = null
    )
}

object ShiftRepository {
    var shiftDataList: List<ShiftData>? = null

    fun getSimpleShiftList(): List<ShiftData> {
        val shiftDataList = mutableListOf<ShiftData>()
        for (i in 1..10) {
            shiftDataList.add(
                this.getSimpleShift()
            )
        }
        return shiftDataList
    }

    fun getSimpleShift(): ShiftData {
        return ShiftData(
            id = 1,
            lineData = LineDataRepository.getSimpleLine(),
            departureTime = "10:00",
            passengerCount = 45,
            childCount = 2,
            checkedCount = 10,
            shiftStatus = "未发车"
        )
    }
}
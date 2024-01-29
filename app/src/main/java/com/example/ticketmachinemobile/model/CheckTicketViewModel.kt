package com.example.ticketmachinemobile.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ticketmachinemobile.network.ApiResponse
import com.example.ticketmachinemobile.network.resp.ShiftInfo
import com.example.ticketmachinemobile.network.RetrofitManger
import com.example.ticketmachinemobile.network.req.CheckTicketReq
import com.example.ticketmachinemobile.network.resp.CheckShiftResp
import com.example.ticketmachinemobile.network.resp.Station
import com.example.ticketmachinemobile.util.DateUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class CheckTicketViewModel : ViewModel() {

    private val api = RetrofitManger.getApiService()
    var shiftInfoList: List<ShiftInfo> = emptyList()

    /**
     * 查看的班次列表（用作展示筛选结果）
     */
    val showShiftInfoLiveData : MutableLiveData<List<ShiftInfo>> =
        MutableLiveData<List<ShiftInfo>>()
    /**
     * 出发站点筛选列表
     */
    var startStationList : MutableLiveData<List<Station>> = MutableLiveData()

    /**
     * 到达站点筛选列表
     */
    var endStationList : MutableLiveData<List<Station>> = MutableLiveData()

    /**
     * 默认出发站点
     */
    val defaultStartStation = mutableStateOf(Station(0, "出发站点"))

    /**
     * 默认到达站点
     */
    val defaultEndStation = mutableStateOf(Station(0, "到达站点"))

    /**
     * 当前选择的出发站点
     */
    var selectedStartStation = mutableStateOf(defaultStartStation.value)

    /**
     * 当前选择的到达站点
     */
    var selectedEndStation = mutableStateOf(defaultEndStation.value)

    /**
     * 请求检票结果
     */
    var checkTicketResult : MutableLiveData<ApiResponse<List<CheckShiftResp>>> = MutableLiveData()

    var apiError: MutableLiveData<Throwable> = MutableLiveData()

    /**
     * 获取检票信息
     */
    fun getCheckTicket(date: String, checkType: Int) {
        val exception = CoroutineExceptionHandler { coroutineContext, throwable ->
            apiError.postValue(throwable)
        }

        viewModelScope.launch(exception) {
            val response = api.getCheckTicket(date, checkType)
            if (response.code == 200) {
                val data: MutableList<ShiftInfo> = response.data!!
                shiftInfoList = data
                showShiftInfoLiveData.postValue(data)
                updateStationList(data)
            } else {
                apiError.postValue(Throwable(response.msg))
            }
        }

    }

    /**
     * 提交检票请求
     * 扫码检票（根据订单号）
     */
    fun submitCheckTicket(checkTicketReq: CheckTicketReq) {
        val exception = CoroutineExceptionHandler { coroutineContext, throwable ->
            apiError.postValue(throwable)
        }

        viewModelScope.launch(exception) {
            val response = api.postCheckOrder(checkTicketReq)
            checkTicketResult.postValue(response)
        }
    }

    /**
     * 更新出发和到达站点筛选列表
     */
    private fun updateStationList(data: MutableList<ShiftInfo>) {
       // 出发站点
        val startStationSet = HashSet<Station>()
        data.forEach {
            startStationSet.add(it.stationList[0])
        }
        startStationList.postValue(startStationSet.toList())
        // 到达站点
        val endStationSet = HashSet<Station>()
        data.forEach {
            it.stationList.forEachIndexed { index, station ->
                if (index != 0) {
                    endStationSet.add(station)
                }
            }
        }
        endStationList.postValue(endStationSet.toList())
    }

    /**
     * 筛选出发站点
     */
    fun onSelectedStartStation(station: Station) {
        val filterList = shiftInfoList.filter {
            it.stationList[0].id == station.id
        }
        showShiftInfoLiveData.postValue(filterList)
        // 重置到达站点筛选
        selectedEndStation.value = defaultEndStation.value
    }

    /**
     * 筛选到达站点
     */
    fun onSelectedEndStation(station: Station) {
        val filterList = mutableListOf<ShiftInfo>()
        shiftInfoList.forEach {
            it.stationList.forEachIndexed { index, stationItem ->
                if (index != 0 && stationItem.id == station.id) {
                    filterList.add(it)
                }
            }
        }
        showShiftInfoLiveData.postValue(filterList)
        // 重置出发站点筛选
        selectedStartStation.value = defaultStartStation.value
    }
}
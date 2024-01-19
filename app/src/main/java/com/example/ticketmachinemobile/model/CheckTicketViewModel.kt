package com.example.ticketmachinemobile.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ticketmachinemobile.network.resp.ShiftInfo
import com.example.ticketmachinemobile.network.RetrofitManger
import com.example.ticketmachinemobile.network.resp.Station
import com.example.ticketmachinemobile.util.DateUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class CheckTicketViewModel : ViewModel() {

    private val api = RetrofitManger.getApiService()
    val shiftInfoLiveData: MutableLiveData<MutableList<ShiftInfo>> =
        MutableLiveData<MutableList<ShiftInfo>>()


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
                shiftInfoLiveData.postValue(data)
                updateStationList(data)
            } else {
                apiError.postValue(Throwable(response.msg))
            }
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
}
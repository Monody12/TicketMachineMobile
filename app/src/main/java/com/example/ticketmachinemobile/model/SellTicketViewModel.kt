package com.example.ticketmachinemobile.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ticketmachinemobile.network.RetrofitManger
import com.example.ticketmachinemobile.network.resp.ShiftInfo
import com.example.ticketmachinemobile.util.DateUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class SellTicketViewModel : ViewModel() {

    private val api = RetrofitManger.getApiService()

    var apiError: MutableLiveData<Throwable> = MutableLiveData()

    /**
     * 班次列表
     */
    val shiftInfoLiveData : MutableLiveData<MutableList<ShiftInfo>> =
        MutableLiveData<MutableList<ShiftInfo>>()


    /**
     * 售票选中班次id
     */
    var shiftId = mutableStateOf(0)

    /**
     * 售票选中班次名称
     */
    var shiftName = mutableStateOf("")

    /**
     * 售票选中站点id
     */
    var stationId = mutableStateOf(0)

    /**
     * 售票选中站点名称
     */
    var stationName = mutableStateOf("")

    /**
     * 售票选择站点弹窗状态
     */
    var stationDialogShow = mutableStateOf(false)

    /**
     * 售票选中班次事件
     */
    fun onShiftSelected(id: Int) {
        shiftId.value = id
    }

    /**
     * 售票选中站点事件
     */
    fun onStationSelected(id: Int) {
        stationId.value = id
    }

    fun updateShiftClickEvent(id: Int?, name: String?) {
        shiftId.value = id ?: 0
        shiftName.value = name ?: ""
    }

    init {
        getSaleTicket(DateUtil.getTodayDate(), 1)
    }

    /**
     * 获取售票信息
     */
    fun getSaleTicket(date: String, checkType: Int) {
        val exception = CoroutineExceptionHandler { coroutineContext, throwable ->
            apiError.postValue(throwable)
        }

        viewModelScope.launch(exception) {
            val response = api.getSaleTicket(date, checkType)
            if (response.code == 200) {
                val data: MutableList<ShiftInfo> = response.data!!
                shiftInfoLiveData.postValue(data)
            } else {
                apiError.postValue(Throwable(response.msg))
            }
        }

    }

}
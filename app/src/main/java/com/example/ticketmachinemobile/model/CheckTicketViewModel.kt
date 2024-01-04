package com.example.ticketmachinemobile.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ticketmachinemobile.network.resp.ShiftInfo
import com.example.ticketmachinemobile.network.RetrofitManger
import com.example.ticketmachinemobile.util.DateUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class CheckTicketViewModel : ViewModel() {

    private val api = RetrofitManger.getApiService()
    val shiftInfoLiveData: MutableLiveData<MutableList<ShiftInfo>> =
        MutableLiveData<MutableList<ShiftInfo>>()


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
            } else {
                apiError.postValue(Throwable(response.msg))
            }
        }

    }

}
package com.example.ticketmachinemobile.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ticketmachinemobile.data.ShiftRepository

class SellTicketViewModel : ViewModel() {

    companion object{
        /**
         * 班次列表
         */
        var shiftList  = ShiftRepository.getSimpleShiftList()
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
    }



}
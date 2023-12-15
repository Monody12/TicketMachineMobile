package com.example.ticketmachinemobile.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ticketmachinemobile.data.Passenger

class SellTicketPayViewModel : ViewModel() {
    companion object {
        /**
         * 乘客列表
         */
        var passengerList = mutableStateListOf<Passenger>()

        /**
         * 添加乘客对话框显示状态
         */
        var addPassengerDialogShow = mutableStateOf(false)

        /**
         * 清空乘客列表
         */
        fun clearPassengerList() {
            passengerList.clear()
        }
        /**
         * 添加乘客
         * 如果类型和证件号码相同，则抛出异常
         */
        fun addPassenger(passenger: Passenger) {
            // TODO 调试时不开启乘客已存在判断
//            passengerList.forEach {
//                if (it.idType == passenger.idType && it.idNumber == passenger.idNumber) {
//                    throw Exception("乘客已存在")
//                }
//            }
            passengerList.add(passenger)
        }
        /**
         * 删除乘客
         */
        fun deletePassenger(passenger: Passenger) {
            passengerList.remove(passenger)
        }
    }
}
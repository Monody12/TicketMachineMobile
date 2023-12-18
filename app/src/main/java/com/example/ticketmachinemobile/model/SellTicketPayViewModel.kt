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
         * 手动输入的证件类型
         */
        var manualInputIdType = mutableStateOf("身份证")

        /**
         * 证件类型选择下拉框状态
         */
        var idTypeDropdownExpanded = mutableStateOf(false)

        /**
         * 手动输入的姓名
         */
        var manualInputName = mutableStateOf("")

        /**
         * 手动输入的证件号
         */
        var manualInputIdNumber = mutableStateOf("")

        /**
         * 是否为手动输入模式
         */
        var isManualInput = mutableStateOf(false)

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

        /**
         * 初始化页面数据
         * 清空乘客列表中的数据及手动输入的数据
         */
        fun init() {
            clearPassengerList()
            manualInputIdType.value = "身份证"
            manualInputName.value = ""
            manualInputIdNumber.value = ""
        }

        /**
         * 获取所有的证件类型
         */
        fun getIdTypeList(): List<String> {
            return listOf(
                "身份证",
                "护照",
                "回乡证",
                "港澳台居民居住证",
                "台湾居民来往大陆通行证",
                "外国人永久居留身份证",
                "其他"
            )
        }
    }
}
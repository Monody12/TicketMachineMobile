package com.example.ticketmachinemobile.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ticketmachinemobile.data.Passenger
import com.example.ticketmachinemobile.model.SellTicketPayViewModel
import com.example.ticketmachinemobile.model.SellTicketViewModel
import com.example.ticketmachinemobile.ticket.SellTicketPayScreen
import com.example.ticketmachinemobile.util.IDCardSDK
import com.example.ticketmachinemobile.util.ReadCardEvent
import com.zkteco.android.biometric.module.idcard.meta.IDCardInfo
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SellTicketPayActivity : ComponentActivity()  {

    private lateinit var idCard: IDCardSDK

    private var readCard : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = SellTicketPayViewModel.Companion
        setContent {
            SellTicketPayScreen(
                onAddPassengerDialogShowChange = { addPassengerDialogOnChange() }
            )
        }
        val handler: Handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
            }
        }
        idCard = IDCardSDK.getInstance()
        try {
            val initSDK = idCard.initSDK(this)
            Toast.makeText(this, "initSDK: $initSDK", Toast.LENGTH_SHORT).show()
            readCard = true
            // 注册事件总线
            EventBus.getDefault().register(this)
        }catch (e : Error){
            e.printStackTrace()
            Toast.makeText(this, "初始化SDK失败", Toast.LENGTH_SHORT).show()
        }
        // 初始化ViewModel中的数据
        viewModel.init()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (readCard){
            idCard.unInitSDK()
            // 注销事件总线
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReadCardEvent(readCardEvent: ReadCardEvent) {
        val cardInfo: IDCardInfo = readCardEvent.getCardInfo()
        if (cardInfo != null) {
            addPassenger(cardInfo.id, cardInfo.name)
            SellTicketPayViewModel.Companion.addPassengerDialogShow.value = false
            addPassengerDialogOnChange()
        }
    }

    private fun addPassenger(idCard: String?, peopleName: String?) {
        val passenger = Passenger()
        passenger.idType = "身份证"
        passenger.idNumber = idCard
        passenger.name = peopleName
        try {
            SellTicketPayViewModel.addPassenger(passenger)
            Toast.makeText(this, "添加成功：${passenger.toString()}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun addPassengerDialogOnChange(){
        if (readCard.not()){
            Toast.makeText(this, "当前设备无法使用身份证读卡器", Toast.LENGTH_SHORT).show()
            return
        }
        val stateValue = SellTicketPayViewModel.addPassengerDialogShow.value
        println(stateValue)
        if (stateValue) {
            Toast.makeText(this, "请放入身份证", Toast.LENGTH_SHORT).show()
            idCard.StartReadCard()
        } else {
            Toast.makeText(this, "请取出身份证", Toast.LENGTH_SHORT).show()
            idCard.StopReadCard()
            SellTicketPayViewModel.isManualInput.value = false
        }
    }

}


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
import com.huashi.otg.sdk.HSIDCardInfo
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SellTicketPayActivity : ComponentActivity()  {

    private lateinit var idCard: IDCardSDK

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
        val initSDK = idCard.initSDK(handler, this)
        Toast.makeText(this, "initSDK: $initSDK", Toast.LENGTH_SHORT).show()
        // 注册事件总线
        EventBus.getDefault().register(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        idCard.unInitSDK()
        // 注销事件总线
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReadCardEvent(readCardEvent: ReadCardEvent) {
        val cardInfo: HSIDCardInfo = readCardEvent.getCardInfo()
        if (cardInfo != null) {
            addPassenger(cardInfo.idCard, cardInfo.peopleName)
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
        val stateValue = SellTicketPayViewModel.addPassengerDialogShow.value
        println(stateValue)
        if (stateValue) {
            Toast.makeText(this, "请放入身份证", Toast.LENGTH_SHORT).show()
            idCard.StartReadCard()
        } else {
            Toast.makeText(this, "请取出身份证", Toast.LENGTH_SHORT).show()
            idCard.StopReadCard()
        }
    }

}


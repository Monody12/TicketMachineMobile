package com.example.ticketmachinemobile.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ticketmachinemobile.network.resp.ShiftInfo

class ScanQrCodeViewModel : ViewModel() {

    var selectShiftInfo = mutableStateOf<ShiftInfo?>(null)

}
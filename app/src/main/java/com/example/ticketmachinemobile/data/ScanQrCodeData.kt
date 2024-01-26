package com.example.ticketmachinemobile.data

import com.example.ticketmachinemobile.network.resp.ShiftInfo
import java.io.Serializable

data class ScanQrCodeData(
    val shiftInfo: ShiftInfo? = null,
    val mode:String
): Serializable
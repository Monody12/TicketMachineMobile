package com.example.ticketmachinemobile.data.response

data class BaseResp <T> (
    var code : Int,
    var message : String,
    var data : T
)
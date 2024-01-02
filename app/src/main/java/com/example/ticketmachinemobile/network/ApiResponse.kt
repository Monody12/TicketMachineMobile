package com.example.ticketmachinemobile.network

class ApiResponse<T> {
    var data: T? = null
    var code: Int = 0
    var msg: String = ""

    constructor(data: T?, code: Int, msg: String) {
        this.data = data
        this.code = code
        this.msg = msg
    }

    constructor() {
        this.data = null
        this.code = 0
        this.msg = ""
    }

    override fun toString(): String {
        return "ApiResponse(data=$data, code=$code, msg='$msg')"
    }
}
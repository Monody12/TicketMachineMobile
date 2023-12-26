package com.example.ticketmachinemobile.network

class ApiResponse<T> {
    var code: Int = 0
    var success: Boolean = false
    var data : T? = null

    constructor(code: Int, success: Boolean) {
        this.code = code
        this.success = success
    }

    constructor(code: Int, success: Boolean, data: T?) {
        this.code = code
        this.success = success
        this.data = data
    }

    override fun toString(): String {
        return "ApiResponse(code=$code, success=$success, data=$data)"
    }


}
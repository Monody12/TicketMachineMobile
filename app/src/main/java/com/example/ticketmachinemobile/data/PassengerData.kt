package com.example.ticketmachinemobile.data

data class Passenger(
    /**
     * 证件类型
     */
    var idType: String?,
    /**
     * 证件号码
     */
    var idNumber: String?,
    /**
     * 姓名
     */
    var name: String?
) {
    constructor() : this("", "", "")
}


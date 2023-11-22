package com.example.ticketmachinemobile.data

data class IncomeDetailData(
    val weChatIncome: String,
    val aliPayIncome: String,
    val cardIncome: String,
    val cashIncome: String,
   // 各收入人次
    val weChatIncomeCount: Int,
    val aliPayIncomeCount: Int,
    val cardIncomeCount: Int,
    val cashIncomeCount: Int,
)

object IncomeDetailDataRepository {
    var incomeDetailData: IncomeDetailData? = null

    fun getSimpleData(): IncomeDetailData {
        return IncomeDetailData(
           "3680.10",
           "1234.50",
           "123.50",
           "133.00",
           160,
           45,
           13,
           6)
    }

}

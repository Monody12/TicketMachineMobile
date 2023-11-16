package com.example.ticketmachinemobile.data

data class UserData(
    val username: String,
    val nickname: String,
    val token: String
)

object UserDataRepository {
    var userData: UserData? = null

    fun getSimpleUser(): UserData {
        return UserData("username", "nickname", "token")
    }

}
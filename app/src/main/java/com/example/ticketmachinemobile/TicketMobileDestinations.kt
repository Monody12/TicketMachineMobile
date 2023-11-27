/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ticketmachinemobile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Contract for information needed on every TicketMobile navigation destination
 */
interface TicketMobileDestination {
    val icon: ImageVector
    val route: String
}

/**
 * TicketMobile app navigation destinations
 */
object Overview : TicketMobileDestination {
    override val icon = Icons.Filled.PieChart
    override val route = "overview"
}

object CheckTicket : TicketMobileDestination {
    override val icon = Icons.Filled.ConfirmationNumber
    override val route = "check-ticket"
}

object SellTicket : TicketMobileDestination {
    override val icon = Icons.Filled.CarCrash
    override val route = "sell-ticket"
}

object ScanQrCode : TicketMobileDestination {
    override val icon = Icons.Filled.QrCodeScanner
    override val route = "scan-qr-code"
}

object SingleAccount : TicketMobileDestination {
    // Added for simplicity, this icon will not in fact be used, as SingleAccount isn't
    // part of the TicketMobileTabRow selection
    override val icon = Icons.Filled.Money
    override val route = "single_account"
    const val accountTypeArg = "account_type"
    val routeWithArgs = "${route}/{${accountTypeArg}}"
    val arguments = listOf(
        navArgument(accountTypeArg) {type = NavType.StringType}
    )
}

// Screens to be displayed in the top TicketMobileTabRow
val ticketMobileTabRowScreens = listOf(Overview, CheckTicket, SellTicket, ScanQrCode)

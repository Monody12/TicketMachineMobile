package com.example.ticketmachinemobile.ticket;

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.ticketmachinemobile.ui.theme.TicketMachineMobileTheme

@Composable
fun SellTicketScreen() {
    TicketMachineMobileTheme {
        Text(
            text = "Sell Ticket Screen Test",
            style = MaterialTheme.typography.h2
        )
    }
}

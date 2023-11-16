package com.example.ticketmachinemobile.ticket

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.ticketmachinemobile.ui.theme.TicketMachineMobileTheme

@Composable
fun CheckTicketScreen() {
    TicketMachineMobileTheme {
        Text(
            text = "Check Ticket Screen Test",
            // h2 标题
            style = MaterialTheme.typography.h2
        )
    }
}
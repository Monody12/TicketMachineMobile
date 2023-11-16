package com.example.ticketmachinemobile.overview

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.ticketmachinemobile.ui.theme.TicketMachineMobileTheme

@Composable
fun OverviewScreen() {
    TicketMachineMobileTheme {
        Text(
            text = "Overview Test",
            // h2 标题
            style = MaterialTheme.typography.h2
        )
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

@Composable
fun OverviewContent() {
    TODO("Not yet implemented")
}

@Composable
fun OverviewTopAppBar() {
    TODO("Not yet implemented")
}

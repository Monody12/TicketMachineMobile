package com.example.ticketmachinemobile

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding

import androidx.compose.material.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ticketmachinemobile.components.TicketMobileTabRow
import com.example.ticketmachinemobile.overview.OverviewScreen
import com.example.ticketmachinemobile.overview.navigateSingleTopTo
import com.example.ticketmachinemobile.scan.ScanQrCodeScreen
import com.example.ticketmachinemobile.ticket.CheckTicketScreen
import com.example.ticketmachinemobile.ticket.SellTicketScreen
import com.example.ticketmachinemobile.ui.theme.TicketMachineMobileTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicketMobileApp()
        }
    }

}

@Composable
fun TicketMobileApp(){
    TicketMachineMobileTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        // Fetch your currentDestination
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            ticketMobileTabRowScreens.find { it.route == currentDestination?.route } ?: Overview
        // A surface container using the 'background' color from the theme
        Scaffold(topBar = {
            TicketMobileTabRow(
                allScreens = ticketMobileTabRowScreens,
                // Pass the callback like this,
                // defining the navigation action when a tab is selected:
                onTabSelected = { newScreen ->
                    navController.navigateSingleTopTo(newScreen.route)
                },
                currentScreen = currentScreen
            )
        }) { innerPadding ->
            NavHost(
                navController = navController,
                // 设置默认启动页面
                startDestination = ScanQrCode.route,
                modifier = Modifier.padding(innerPadding))
            {
                composable(route = Overview.route){
                    OverviewScreen()
                }
                composable(route = CheckTicket.route){
                    CheckTicketScreen()
                }
                composable(route = ScanQrCode.route){
                    ScanQrCodeScreen()
                }
                composable(route = SellTicket.route){
                    SellTicketScreen()
                }

            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    // 获取当前系统日期
    val dateFormat: CharSequence = DateFormat.format("MM-dd EEEE", System.currentTimeMillis())

}

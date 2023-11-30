package com.example.ticketmachinemobile

import android.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.KeyEventDispatcher.Component
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
    private val REQUEST_CODE_SCAN = 0x0000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicketMobileApp()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) {
            val scanResult = data?.getStringExtra("SCAN_RESULT")
            // 处理扫码结果
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
        var scanResult = ""
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
                    ScanQrCodeScreen(scanResult)
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

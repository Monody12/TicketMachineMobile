package com.example.ticketmachinemobile.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.baidu.ocr.sdk.OCR
import com.baidu.ocr.sdk.OnResultListener
import com.baidu.ocr.sdk.exception.OCRError
import com.baidu.ocr.sdk.model.AccessToken
import com.example.ticketmachinemobile.CheckTicket
import com.example.ticketmachinemobile.Overview
import com.example.ticketmachinemobile.ScanQrCode
import com.example.ticketmachinemobile.SellTicket
import com.example.ticketmachinemobile.activity.ScanActivity.Companion.SCAN_RESULT
import com.example.ticketmachinemobile.components.TicketMobileTabRow
import com.example.ticketmachinemobile.constant.TicketConstant
import com.example.ticketmachinemobile.data.ScanQrCodeData
import com.example.ticketmachinemobile.model.CheckTicketViewModel
import com.example.ticketmachinemobile.model.SellTicketViewModel
import com.example.ticketmachinemobile.overview.OverviewScreen
import com.example.ticketmachinemobile.overview.navigateSingleTopTo
import com.example.ticketmachinemobile.scan.ScanQrCodeScreen
import com.example.ticketmachinemobile.ticket.CheckTicketScreen
import com.example.ticketmachinemobile.ticket.SellTicketScreen
import com.example.ticketmachinemobile.ticketMobileTabRowScreens
import com.example.ticketmachinemobile.ui.theme.TicketMachineMobileTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val CAMERA_REQ_CODE = 111
        const val DEFINED_CODE = 222
        const val BITMAP_CODE = 333
        const val MULTIPROCESSOR_SYN_CODE = 444
        const val MULTIPROCESSOR_ASYN_CODE = 555
        const val GENERATE_CODE = 666
        const val DECODE = 1
        const val GENERATE = 2
        const val REQUEST_CODE_SCAN_ONE = 0X01
        const val REQUEST_CODE_DEFINE = 0X0111
        const val REQUEST_CODE_SCAN_MULTI = 0X011
        const val DECODE_MODE = "decode_mode"
        const val RESULT = "SCAN_RESULT"
    }

    private val REQUEST_CODE_SCAN = 0x0000

    private lateinit var checkTicketViewModel: CheckTicketViewModel

    private lateinit var sellTicketViewModel: SellTicketViewModel

    private lateinit var alertDialog: AlertDialog.Builder

    private var hasGotToken = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alertDialog = AlertDialog.Builder(this)
        setContent {
            TicketMobileApp()
        }
        // 初始化百度ocr
        initAccessToken()
        // 初始化检票数据
//        checkTicketViewModel = ViewModelProvider(this)[CheckTicketViewModel::class.java]
//        val checkTicketData = checkTicketViewModel.showShiftInfoLiveData
//        if (checkTicketData.value.isNullOrEmpty()) {
//            checkTicketViewModel.getCheckTicket("2023-12-27", 1)
//        }
        // 初始化售票数据
//        sellTicketViewModel = ViewModelProvider(this)[SellTicketViewModel::class.java]
//        val sellTicketData = sellTicketViewModel.shiftInfoLiveData
//        if (sellTicketData.value.isNullOrEmpty()) {
//            sellTicketViewModel.getSaleTicket("2023-12-27", 1)
//        }
    }


    /**
     * 以license文件方式初始化
     */
    private fun initAccessToken() {
        OCR.getInstance(applicationContext).initAccessToken(object : OnResultListener<AccessToken> {
            override fun onResult(accessToken: AccessToken) {
                val token = accessToken.accessToken
                hasGotToken = true
            }

            override fun onError(error: OCRError) {
                error.printStackTrace()
                error.message?.let { alertText("licence方式获取token失败", it) }
            }
        }, applicationContext)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) {
            val scanResult = data?.getStringExtra(SCAN_RESULT)
            // 处理扫码结果
//            Toast.makeText(this, "扫码结果：${scanResult}", Toast.LENGTH_SHORT).show()

        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initAccessToken()
        } else {
            Toast.makeText(
                applicationContext,
                "需要android.permission.READ_PHONE_STATE",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun alertText(title: String, message: String) {
        runOnUiThread {
            alertDialog.setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show()
        }
    }
}

// 定义一个 CompositionLocal 来存储 NavController
val LocalNavController = compositionLocalOf<NavController> {
    error("NavController not provided")
}

@Composable
fun TicketMobileApp() {
    TicketMachineMobileTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        // Fetch your currentDestination
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            ticketMobileTabRowScreens.find { it.route == currentDestination?.route } ?: Overview
        val noArgScan = Gson().toJson(
            ScanQrCodeData(
                shiftInfo = null,
                mode = TicketConstant.CHECK_TICKET
            )
        )
        // 使用 CompositionLocalProvider 提供 NavController
        CompositionLocalProvider(LocalNavController provides navController) {
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
                    startDestination = Overview.route,
                    modifier = Modifier.padding(innerPadding)
                )
                {
                    composable(route = Overview.route) {
                        OverviewScreen()
                    }
                    composable(route = CheckTicket.route) {
                        CheckTicketScreen()
                    }
                    composable(route = "${ScanQrCode.route}?scanDataJson={scanDataJson}", arguments = listOf(
                        navArgument("scanDataJson") {
                            defaultValue = noArgScan
                        }
                    )) {
                        val scanDataJson = it.arguments?.getString("scanDataJson")
                        ScanQrCodeScreen(scanDataJson ?: noArgScan)
                    }

                    composable(route = SellTicket.route) {
                        SellTicketScreen()
                    }

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

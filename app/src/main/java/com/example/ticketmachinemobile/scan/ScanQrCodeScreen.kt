package com.example.ticketmachinemobile.scan

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ticketmachinemobile.activity.ScanActivity
import com.example.ticketmachinemobile.activity.MainActivity.Companion.BITMAP_CODE
import com.example.ticketmachinemobile.activity.MainActivity.Companion.DECODE_MODE
import com.example.ticketmachinemobile.activity.ScanActivity.Companion.SCAN_RESULT
import com.example.ticketmachinemobile.constant.TicketConstant
import com.example.ticketmachinemobile.data.ScanQrCodeData
import com.example.ticketmachinemobile.model.CheckTicketViewModel
import com.example.ticketmachinemobile.model.ScanQrCodeViewModel
import com.example.ticketmachinemobile.network.req.CheckTicketReq
import com.google.gson.Gson
import com.huawei.hms.ml.scan.HmsScan


/**
 * 扫码页面
 */
@Composable
fun ScanQrCodeScreen(scanDataJson: String) {
    val scanData: ScanQrCodeData = Gson().fromJson(scanDataJson, ScanQrCodeData::class.java)
    // 获取当前的 Context
    val context = LocalContext.current
    var scannedCode by remember { mutableStateOf("") }
    val checkTicketViewModel : CheckTicketViewModel = viewModel()
    var checkTicketResult = checkTicketViewModel.checkTicketResult.observeAsState()

    var currentMode by rememberSaveable { mutableStateOf(scanData.mode) }
    var autoExecute by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 扫码区域
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Column {
                // 选中班次
                if (scanData.shiftInfo != null) {
                    val shiftInfo = scanData.shiftInfo
                    Text(
                        text = "选中班次: \n线路名称：${shiftInfo.routeName}\n出发时间：${shiftInfo.startTime}",
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                // 显示当前模式：检票、退票
                Text(
                    text = "模式: $currentMode",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                // 显示检票结果
                Text(
                    text = "检票结果: ${checkTicketResult.value}",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                // 切换模式
//                Button(
//                    onClick = {
//                        currentMode = changeMode(currentMode)
//                    },
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    Text(text = "切换")
//                }
                // 设置自动执行
//                Button(
//                    onClick = {
//                        autoExecute = !autoExecute
//                    },
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    Text(text = "${if (autoExecute) "关闭" else "开启"}自动执行")
//                }

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 显示扫描结果
        Text(
            text = "扫描结果: $scannedCode",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(16.dp))


        // 开始扫码按钮
//        val scanLauncher = rememberLauncherForActivityResult(
//            contract = ActivityResultContracts.StartActivityForResult(),
//        ){
//            // 处理扫码结果
//            scannedCode = it.data?.getStringExtra(ScanActivity.SCAN_RESULT) ?: ""
//        }
        // 开始扫码按钮
        val scanLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // 处理扫码结果
                val results = result.data?.getParcelableArrayExtra(SCAN_RESULT)
                scannedCode = (results?.get(0) as HmsScan).originalValue
                // 处理扫码结果
                if (scannedCode != null && scannedCode.isNotEmpty()) {
                    Toast.makeText(context, "扫码结果：${scannedCode}", Toast.LENGTH_SHORT).show()
                    checkTicketViewModel.submitCheckTicket(CheckTicketReq(
                        orderNo = scannedCode,
                        shiftId = scanData.shiftInfo?.id,
                        idCard = null
                    ))
                }
            }
        }
        Button(
            onClick = {
                // 启动一个扫码界面
                val intent = Intent(context, ScanActivity::class.java)
                intent.putExtra(DECODE_MODE, BITMAP_CODE)
                scanLauncher.launch(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = "开始扫码")
        }
    }
}

fun changeMode(currentMode: String): String {
    val modeList = listOf("检票", "通用检票", "退票", "开票")
    val index = modeList.indexOf(currentMode)
    return modeList[(index + 1) % modeList.size]
}

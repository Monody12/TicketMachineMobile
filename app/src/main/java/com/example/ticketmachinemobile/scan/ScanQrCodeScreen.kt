package com.example.ticketmachinemobile.scan

import android.content.Intent
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
import com.example.ticketmachinemobile.activity.ScanActivity
import com.example.ticketmachinemobile.constant.TicketConstant


/**
 * 扫码页面
 */
@Composable
fun ScanQrCodeScreen(mode :String = TicketConstant.CHECK_TICKET) {
    // 获取当前的 Context
    val context = LocalContext.current
    var scannedCode by remember { mutableStateOf("") }

    var currentMode by rememberSaveable { mutableStateOf(mode) }
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
                // 显示当前模式：检票、退票
                Text(
                    text = "模式: $currentMode",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                // 切换模式
                Button(
                    onClick = {
                        currentMode = changeMode(currentMode)
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "切换")
                }
                // 设置自动执行
                Button(
                    onClick = {
                        autoExecute = !autoExecute
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "${if (autoExecute) "关闭" else "开启"}自动执行")
                }

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
        val scanLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ){
            // 处理扫码结果
            scannedCode = it.data?.getStringExtra("SCAN_RESULT") ?: ""
        }
        Button(
            onClick = {
                // 启动一个扫码界面
                val intent = Intent(context, ScanActivity::class.java)
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

fun changeMode(currentMode :String) :String{
    val modeList = listOf("检票", "退票", "开票")
    val index = modeList.indexOf(currentMode)
    return modeList[(index + 1) % modeList.size]
}

package com.example.ticketmachinemobile.scan

import android.provider.Settings.Global.getString
import androidx.compose.foundation.border
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityOptionsCompat
import com.example.ticketmachinemobile.R
import kotlinx.coroutines.delay

/**
 * 扫码页面
 */
@Composable
fun ScanQrCodeScreen(onScanResult: (String) -> Unit) {
    var scannedCode by remember { mutableStateOf("") }

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
                .border(2.dp, Color.Gray)
        ) {
            // 此处可以添加扫码框和相应的样式

            // 模拟扫描结果，实际应用中应替换为实际的扫码逻辑
            LaunchedEffect(Unit) {
                delay(3000)
                scannedCode = "MockQrCode123"
                onScanResult(scannedCode)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 显示扫描结果
        Text(
            text = "Scanned Code: $scannedCode",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 开始扫码按钮
        Button(
            onClick = {
                // 此处可以添加启动扫码逻辑
                // 实际应用中，您需要调用相应的扫码库来执行扫码操作
                      // 启动一个扫码界面

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = "Start Scan")
        }
    }
}
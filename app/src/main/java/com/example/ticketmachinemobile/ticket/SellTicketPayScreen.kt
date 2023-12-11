package com.example.ticketmachinemobile.ticket

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ticketmachinemobile.MyApplication
import com.example.ticketmachinemobile.activity.SellTicketPayActivity
import com.example.ticketmachinemobile.data.Passenger
import com.example.ticketmachinemobile.model.SellTicketViewModel
import com.example.ticketmachinemobile.ui.theme.TicketMachineMobileTheme

@Composable
fun SellTicketPayScreen() {
    TicketMachineMobileTheme {
        Column {
            SellTicketPayTopBar()
            SellTicketPayScreenContent()
            AddPassengerDialog()
        }
    }
}

@Composable
fun SellTicketPayTopBar() {
    val context = LocalContext.current
    // 顶部应用栏
    TopAppBar(
        modifier = Modifier,
        title = { Text("这是标题") },
        navigationIcon = {
            IconButton(onClick = {
                // 结束当前页面
                (context as SellTicketPayActivity).finish()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "返回",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "分享",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "设置",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        backgroundColor = MaterialTheme.colors.secondaryVariant,
    )
}

@Composable
fun SellTicketPayScreenContent() {
    val viewModel = SellTicketViewModel.Companion
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary)
    ) {
        Column(
            modifier = Modifier.padding(7.dp)
        ) {
            Text(text = "线路：${viewModel.shiftName.value}")
            Text(text = "到达站点：${viewModel.stationName.value}")
            PassengerList()
        }
    }
}

/**
 * 乘客列表
 */
@Composable
fun PassengerList() {
    val passengerList  = mutableListOf<Passenger>()
    Surface(
        modifier = Modifier
            .padding(7.dp)
            .background(color = MaterialTheme.colors.secondaryVariant)
    ) {
        Column(

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "乘客列表")
                Text(text = "${passengerList.size}人")
            }
            Text(text = "stationDialogShow.value: ${SellTicketViewModel.Companion.stationDialogShow.value}")
            Button(onClick = {
                // 打开添加乘客对话框
                SellTicketViewModel.Companion.stationDialogShow.value = SellTicketViewModel.Companion.stationDialogShow.value.not()
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "添加乘客")
            }
        }
    }
}

@Composable
fun AddPassengerDialog() {
    val showDialog = SellTicketViewModel.Companion.stationDialogShow
    if (showDialog.value == true) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text(text = "添加乘客信息")
            },
            text = {
                Column(
                ) {

                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false

                    }
                ) {
                    Text(text = "摄像头识别")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                    }
                ) {
                    Text(text = "手动输入")
                }
            }
        )
    }else{

    }

}

@Preview(showBackground = true)
@Composable
fun SellTicketPayScreenPreview() {
    SellTicketPayScreen()
}
package com.example.ticketmachinemobile.ticket

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ticketmachinemobile.MyApplication
import com.example.ticketmachinemobile.R
import com.example.ticketmachinemobile.activity.SellTicketPayActivity
import com.example.ticketmachinemobile.data.Passenger
import com.example.ticketmachinemobile.data.TicketType
import com.example.ticketmachinemobile.data.getTicketTypeList
import com.example.ticketmachinemobile.model.SellTicketPayViewModel
import com.example.ticketmachinemobile.model.SellTicketViewModel
import com.example.ticketmachinemobile.ui.theme.TicketMachineMobileTheme

@Composable
fun SellTicketPayScreen(onAddPassengerDialogShowChange: () -> Unit) {
    TicketMachineMobileTheme {
        Column {
            SellTicketPayTopBar()
            SellTicketPayScreenContent(onAddPassengerDialogShowChange)
            AddPassengerDialog(onAddPassengerDialogShowChange)
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
fun SellTicketPayScreenContent(onAddPassengerDialogShowChange: () -> Unit) {
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
            PassengerList(onAddPassengerDialogShowChange)
        }
    }
}

/**
 * 乘客列表
 */
@Composable
fun PassengerList(onAddPassengerDialogShowChange: () -> Unit) {
    val viewModel = SellTicketPayViewModel.Companion
    val passengerList: SnapshotStateList<Passenger> = viewModel.passengerList
    val ticketTypeList = getTicketTypeList()
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

            // LazyColumn动态渲染乘客列表 viewModel.passengerList
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                items(passengerList.size) { index ->
                    val passenger = passengerList[index]
                    val selectedTicketType = passenger.ticketType
                    PassengerInfo(passenger, ticketTypeList, selectedTicketType)
                }
            }

            Button(onClick = {
                // 打开添加乘客对话框
                SellTicketPayViewModel.Companion.addPassengerDialogShow.value = true
                onAddPassengerDialogShowChange()
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "添加乘客")
            }
        }
    }
}

@Composable
fun AddPassengerDialog(onAddPassengerDialogShowChange: () -> Unit) {
    val viewModel = SellTicketPayViewModel.Companion
    val showDialog = viewModel.addPassengerDialogShow
    if (showDialog.value == true) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
                onAddPassengerDialogShowChange()
            },
            title = {
                Text(text = "添加乘客信息")
            },
            text = {
                Image(
                    painter = painterResource(id = R.drawable.idcard_scan),
                    contentDescription = "请将身份证放在扫描区域内",
                    modifier = Modifier.fillMaxWidth()
                )
                // 水平居中
                Text(
                    text = "请将身份证放在扫描区域内",
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        SellTicketPayViewModel.addPassenger(
                            Passenger(
                                idType = "身份证",
                                idNumber = "123456789012345678",
                                name = "张三"
                            )
                        )
                    }
                ) {
                    Text(text = "导入测试数据")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        onAddPassengerDialogShowChange()
                    }
                ) {
                    Text(text = "手动输入")
                }
            }
        )
    } else {

    }

}

@Composable
fun PassengerInfo(
    passenger: Passenger,
    ticketTypeList: List<TicketType>,
    selectedTicketType: MutableState<TicketType>
) {
    Row {
        Column {
            Row {
                Text(text = "${passenger.name}", modifier = Modifier.padding(end = 10.dp))
                Text(text = "票种: ${selectedTicketType.value.name}")
                val expandedState = remember { mutableStateOf(false) }
                IconButton(
                    onClick = { expandedState.value = true },
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }

                TicketTypeSelect(
                    ticketTypeList = ticketTypeList,
                    selectedTicketType = selectedTicketType,
                    expandedState = expandedState
                )
            }
            Text(text = "${passenger.idNumber}")
        }
        // 编辑IconButton
//        IconButton(onClick = { /*TODO*/ }) {
//            Icon(
//                imageVector = Icons.Default.Edit,
//                contentDescription = "",
//                tint = MaterialTheme.colors.onPrimary
//            )
//        }
        // 删除IconButton
        IconButton(onClick = { SellTicketPayViewModel.deletePassenger(passenger) }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "",
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
fun TicketTypeSelect(
    ticketTypeList: List<TicketType>,
    selectedTicketType: MutableState<TicketType>,
    expandedState: MutableState<Boolean>
) {
    // 下拉选单，选择票类型
    DropdownMenu(
        expanded = expandedState.value,
        onDismissRequest = { expandedState.value = false }
    ) {
        ticketTypeList.forEach { option ->
            DropdownMenuItem(onClick = {
                selectedTicketType.value = option
                expandedState.value = false
            }) {
                Text(option.name)
            }
        }
    }
}

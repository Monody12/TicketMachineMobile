package com.example.ticketmachinemobile.ticket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
    val viewModel : SellTicketViewModel = viewModel()
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
    val viewModel : SellTicketPayViewModel = viewModel()
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

            // LazyColumn动态渲染乘客列表 viewModel.passengerList
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp)
            ) {
                items(passengerList.size) { index ->
                    val passenger = passengerList[index]
                    val selectedTicketType = passenger.ticketType
                    PassengerInfo(passenger, ticketTypeList, selectedTicketType)
                }
            }

            Button(onClick = {
                // 打开添加乘客对话框
                viewModel.addPassengerDialogShow.value = true
                onAddPassengerDialogShowChange()
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "添加乘客")
            }
        }
    }
}

@Composable
fun AddPassengerDialog(onAddPassengerDialogShowChange: () -> Unit) {
    val viewModel : SellTicketPayViewModel= viewModel()
    val showDialog = viewModel.addPassengerDialogShow
    val isManualInput = viewModel.isManualInput
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
                if (isManualInput.value.not()){
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
                }
                // 手动输入
                else {
                    val idType = viewModel.manualInputIdType
                    val name = viewModel.manualInputName
                    val idNumber = viewModel.manualInputIdNumber

                    Column {
                        // 证件类型

                        // 姓名
                        TextField(
                            value = name.value,
                            onValueChange = {
                                name.value = it
                            },
                            singleLine = true,
                            label = {
                                Text("姓名")
                            }
                        )
                        // 证件号
                        TextField(
                            value = idNumber.value,
                            onValueChange = {
                                idNumber.value = it
                            },
                            singleLine = true,
                            label = {
                                Text("证件号")
                            }
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        viewModel.addPassenger(
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
                        if (isManualInput.value) {
                            // 手动输入
                            val idType = viewModel.manualInputIdType
                            val name = viewModel.manualInputName
                            val idNumber = viewModel.manualInputIdNumber
                            viewModel.addPassenger(
                                Passenger(
                                    idType = idType.value,
                                    idNumber = idNumber.value,
                                    name = name.value
                                )
                            )
                            showDialog.value = false
                        } else {
                            isManualInput.value = true
                        }
                    }
                ) {
                    val text = if (isManualInput.value) "确认" else "手动输入"
                    Text(text = text)
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
    val viewModel : SellTicketPayViewModel = viewModel()
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
        IconButton(onClick = { viewModel.deletePassenger(passenger) }) {
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

@Composable
fun IdTypeSelect() {
    val viewModel :SellTicketPayViewModel = viewModel()
    // Sample list of items for the dropdown
    val items = viewModel.getIdTypeList()

    // State to hold the selected item
    val selectedItem = viewModel.manualInputIdType

    // DropdownMenu state
    val dropdownMenuState = viewModel.idTypeDropdownExpanded

//    TextField(
//        value = selectedItem.value,
//        onValueChange = { selectedItem.value = it },
//        label = { Text("Select an item") },
//        keyboardOptions = KeyboardOptions.Default.copy(
//            imeAction = ImeAction.Done
//        ),
//        trailingIcon = {
//            // Icon for the dropdown
//            Icon(
//                imageVector = Icons.Default.ArrowDropDown,
//                contentDescription = "Select an item",
//                tint = MaterialTheme.colors.onPrimary,
//                modifier = Modifier
//                    .size(24.dp)
//                    .clickable { dropdownMenuState.value = true }
//
//            )
//        },
//        onTrailingIconClick = {
//            // Show the dropdown menu when the trailing icon is clicked
//            dropdownMenuState.value = true
//        }
//    )

    // DropdownMenu to display the items
    DropdownMenu(
        expanded = dropdownMenuState.value,
        onDismissRequest = { dropdownMenuState.value = false }
    ) {
        items.forEach { item ->
            DropdownMenuItem(onClick = {
                // Set the selected item and close the dropdown
                selectedItem.value = item
                dropdownMenuState.value = false
            }) {
                Text(text = item)
            }
        }
    }
}

@Preview
@Composable
fun SellTicketPayScreenPreview(){
    SellTicketPayScreen(
        onAddPassengerDialogShowChange = { addPassengerDialogOnChange() }
    )
}

fun addPassengerDialogOnChange() {
    TODO("Not yet implemented")
}

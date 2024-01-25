package com.example.ticketmachinemobile.ticket

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ticketmachinemobile.components.TicketMobileSelection
import com.example.ticketmachinemobile.data.ShiftCheckTypeList
import com.example.ticketmachinemobile.model.CheckTicketViewModel
import com.example.ticketmachinemobile.network.ApiResponse
import com.example.ticketmachinemobile.network.resp.Station
import com.example.ticketmachinemobile.network.resp.StationSaver
import com.example.ticketmachinemobile.ui.theme.TicketMachineMobileTheme
import com.example.ticketmachinemobile.util.DateUtil.getTodayDate

@Composable
fun CheckTicketScreen() {
    val viewModel: CheckTicketViewModel = viewModel()
    val shiftListData by viewModel.showShiftInfoLiveData.observeAsState()
    // 界面启动时初始化
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getCheckTicket(getTodayDate(), 1)
    })
    Log.i("CheckTicketScreen", "shiftListData: ${shiftListData.toString()}")
    TicketMachineMobileTheme {
        Column {
            FilterBox()
            shiftListData?.let {
                ShiftList(
                    null,
                    null,
                    shiftInfoList = it,
                    { CheckButtonRow() },
                )
            }
        }
    }
}

@Composable
fun FilterBox() {
    val context = LocalContext.current
    val viewModel: CheckTicketViewModel = viewModel()
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "检票",
                style = MaterialTheme.typography.h6,
            )
            IconButton(onClick = {
                viewModel.getCheckTicket(getTodayDate(), 1)
                viewModel.selectedStartStation.value = viewModel.defaultStartStation.value
                viewModel.selectedEndStation.value = viewModel.defaultEndStation.value
            }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "刷新",
                    tint = Color.White
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TabbedLayout()
        }
        StationSelection()
    }
}

/**
 * 站点选择下拉框
 */
@Composable
fun StationSelection() {
    val viewModel: CheckTicketViewModel = viewModel()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 起始站点
        var startStation by rememberSaveable(stateSaver = StationSaver) { viewModel.selectedStartStation }
        val startStationList = viewModel.startStationList.observeAsState()
        TicketMobileSelection<Station>(
            options = startStationList.value?: emptyList(),
            selectedOption = startStation,
            onOptionSelected = {
                startStation = it
                viewModel.onSelectedStartStation(it)
            },
            showContent = { it.stationName },
            expanded = false,
            modifier = Modifier.weight(1f) // 使用weight属性
        )
        // 结束站点
        var endStation by rememberSaveable(stateSaver = StationSaver) { viewModel.selectedEndStation }
        val endStationList = viewModel.endStationList.observeAsState()
        TicketMobileSelection<Station>(
            options = endStationList.value?: emptyList(),
            selectedOption = endStation,
            onOptionSelected = {
                endStation = it
                viewModel.onSelectedEndStation(it)
            },
            showContent = { it.stationName },
            expanded = false,
            modifier = Modifier.weight(1f) // 使用weight属性
        )
    }
}

@Composable
fun TabbedLayout() {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabs = ShiftCheckTypeList.data

    val viewModel: CheckTicketViewModel = viewModel()

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(3.dp),
                    color = Color.White
                )
            }
        ) {
            tabs.forEachIndexed { index, checkType ->
                Tab(
                    text = { Text(checkType.name) },
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        // 执行相应的数据拉取流程
                        viewModel.getCheckTicket(getTodayDate(), checkType.id)
                    }
                )
            }
        }
    }
}

private fun checkTicketListCallBack(response: ApiResponse<*>, context: Context) {
    if (response.code == 200) {
        Toast.makeText(context, "请求成功：${response.data}", Toast.LENGTH_LONG).show()
    } else {
        Toast.makeText(context, "请求失败", Toast.LENGTH_LONG).show()
    }
}
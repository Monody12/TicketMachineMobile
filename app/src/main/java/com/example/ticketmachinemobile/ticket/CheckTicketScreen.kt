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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ticketmachinemobile.components.TicketMobileSelection
import com.example.ticketmachinemobile.model.CheckTicketViewModel
import com.example.ticketmachinemobile.network.ApiResponse
import com.example.ticketmachinemobile.network.resp.ShiftInfo
import com.example.ticketmachinemobile.ui.theme.TicketMachineMobileTheme

@Composable
fun CheckTicketScreen() {
    val viewModel: CheckTicketViewModel = viewModel()
    val shiftListData by viewModel.shiftInfoLiveData.observeAsState()
    if (shiftListData.isNullOrEmpty()){
        viewModel.getCheckTicket("2023-12-27",1)
    }
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
    val viewModel : CheckTicketViewModel = viewModel()
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
                viewModel.getCheckTicket("2023-12-27",1)
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 起始站点
        var startStation by rememberSaveable { mutableStateOf("出发站点") }
        TicketMobileSelection(
            options = listOf("站点1", "站点2", "站点3"),
            selectedOption = startStation,
            onOptionSelected = { startStation = it },
            expanded = false,
            modifier = Modifier.weight(1f) // 使用weight属性
        )
        // 结束站点
        var endStation by rememberSaveable { mutableStateOf("到达站点") }
        TicketMobileSelection(
            options = listOf("站点4", "站点5", "站点6"),
            selectedOption = endStation,
            onOptionSelected = { endStation = it },
            expanded = false,
            modifier = Modifier.weight(1f) // 使用weight属性
        )
    }
}

@Composable
fun TabbedLayout() {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Tab 1", "Tab 2", "Tab 3")

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
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        // 执行相应的数据拉取流程
//                        fetchDataForTab(selectedTabIndex)
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
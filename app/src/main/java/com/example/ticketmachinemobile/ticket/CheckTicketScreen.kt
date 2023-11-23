package com.example.ticketmachinemobile.ticket

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ticketmachinemobile.components.TicketMobileSelection
import com.example.ticketmachinemobile.data.ShiftData
import com.example.ticketmachinemobile.data.ShiftRepository
import com.example.ticketmachinemobile.ui.theme.TicketMachineMobileTheme

@Composable
fun CheckTicketScreen() {
    TicketMachineMobileTheme {
        Column {
            FilterBox()
            ShiftList()
        }
    }
}

@Composable
fun FilterBox() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "筛选条件",
                style = MaterialTheme.typography.h6,
            )
            IconButton(onClick = { }) {
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

/**
 * 班次显示布局
 */
@Composable
fun ShiftLayout(shiftData: ShiftData) {
    val textWhiteAndCenter =
        MaterialTheme.typography.body1.copy(color = Color.White).copy(textAlign = TextAlign.Center)
    val textWhiteAndCenterSmall =
        MaterialTheme.typography.body2.copy(color = Color.White).copy(textAlign = TextAlign.Center)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .border(1.dp, Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 线路名称
            Text(
                text = shiftData.lineData?.lineName ?: "",
                style = textWhiteAndCenter,
                modifier = Modifier.weight(1f)
            )
            // 发车时间
            Text(
                text = shiftData.departureTime ?: "",
                style = textWhiteAndCenter,
                modifier = Modifier.weight(1f)
            )

        }
        // 站点信息
        shiftData.lineData?.stationList?.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 站点名称
                Text(
                    text = it.stationName,
                    style = textWhiteAndCenter,
                    modifier = Modifier.weight(1f)
                )
                // 上车人数
                if (it.getOnCount > 0)
                    Text(
                        text = it.getOnCount.toString() + "上",
                        style = textWhiteAndCenter,
                        modifier = Modifier.weight(1f)
                    )
                // 下车人数
                if (it.getOffCount > 0)
                    Text(
                        text = it.getOffCount.toString() + "下",
                        style = textWhiteAndCenter,
                        modifier = Modifier.weight(1f)
                    )
                // 到站时间
                Text(
                    text = it.arriveTime,
                    style = textWhiteAndCenter,
                    modifier = Modifier.weight(1f)
                )

            }
        }
        // 乘客数量、携童数量、已检票数量
        Row {
            // 乘客数量
            Text(
                text = "共${shiftData.passengerCount}乘客 ${shiftData.childCount}携童",
                style = textWhiteAndCenter,
                modifier = Modifier.weight(1f)
            )
            // 已检票数量
            Text(
                text = "已检票${shiftData.checkedCount}人",
                style = textWhiteAndCenter,
                modifier = Modifier.weight(1f)
            )
        }
        // 扫码检票、身份证检票、订单列表 按钮
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            // 向右对齐
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 扫码检票
            Button(onClick = { /*TODO*/ }) {
                Text(
                    text = "扫码检票",
                    style = textWhiteAndCenterSmall,

                )
            }
            // 身份证检票
           Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(horizontal = 2.dp)) {
               Text(
                   text = "身份证检票",
                   style = textWhiteAndCenterSmall,
               )
           }
            // 订单列表
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(end = 6.dp)) {
                Text(
                    text = "订单列表",
                    style = textWhiteAndCenterSmall,
                )
            }
        }
    }
}

/**
 * 班次列表
 */
@Composable
fun ShiftList() {
    val shiftDataList = ShiftRepository.getSimpleShiftList()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp)
    ) {
        items(shiftDataList.size) { index ->
            ShiftLayout(shiftDataList[index])
        }
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


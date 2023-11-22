package com.example.ticketmachinemobile.ticket

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ticketmachinemobile.components.TicketMobileSelection
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
        Row (
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
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 起始站点
        var startStation by rememberSaveable { mutableStateOf("站点1") }
        TicketMobileSelection(
            options = listOf("站点1", "站点2", "站点3"),
            selectedOption = startStation,
            onOptionSelected = { startStation = it },
            expanded = false,
            modifier = Modifier.weight(1f) // 使用weight属性
        )
        // 结束站点
        var endStation by rememberSaveable { mutableStateOf("站点4") }
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
 * 班次列表
 */
@Composable
fun ShiftList() {

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

        // 根据选中的标签渲染对应的内容
        when (selectedTabIndex) {
            0 -> TabContent("Content for Tab 1")
            1 -> TabContent("Content for Tab 2")
            2 -> TabContent("Content for Tab 3")
        }
    }
}

@Composable
fun TabContent(content: String) {
    Text(
        text = content,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}



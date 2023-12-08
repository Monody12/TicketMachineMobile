package com.example.ticketmachinemobile.ticket;

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ticketmachinemobile.MyApplication
import com.example.ticketmachinemobile.components.TicketMobileSelection
import com.example.ticketmachinemobile.model.SellTicketViewModel
import com.example.ticketmachinemobile.ui.theme.TicketMachineMobileTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun SellTicketScreen() {
    val viewModel  = SellTicketViewModel.Companion
    TicketMachineMobileTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp)
        ) {
            DateSelectionScreen()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                // 售票信息
                ShiftList(
                    showDialog = viewModel.stationDialogShow,
                    updateShiftClickEvent = SellTicketViewModel::updateShiftClickEvent,
                    { SellTicketText(22, null) })
                StationDialogSelection(showDialog = viewModel.stationDialogShow,viewModel = viewModel)
                StationFilterBottomBar(    
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                )
            }

        }
    }
}

@Composable
fun DateSelectionScreen() {
    val dateList = remember { mutableStateOf(listOf<String>()) }
    val weekList = remember { mutableStateOf(listOf<String>()) }
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("M月d日")
    val locale = Locale("zh", "CN") // 使用中文语言环境

    for (i in 0 until 10) {
        val date = currentDate.plusDays(i.toLong())
        val formattedDate = date.format(formatter)
        val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, locale)

        dateList.value += formattedDate
        weekList.value += dayOfWeek
    }

    Row {
        // 最近日期选择器
        LazyRow(
            modifier = Modifier
                .weight(7f)
        ) {
            items(dateList.value.size) { index ->
                DateItem(weekList.value[index], dateList.value[index])
            }
        }
        // 当月及下月日期选择器（一个日期icon按钮）
        IconButton(
            onClick = {
                // Handle date icon button click
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Date icon button"
            )
        }
    }

}

@Composable
fun DateItem(week: String, date: String) {
    Button(
        onClick = {
            // Handle date item click
        },
        modifier = Modifier
            .padding(3.dp)
            .clip(MaterialTheme.shapes.medium)
            .defaultMinSize(2.dp, 2.dp),
        contentPadding = PaddingValues(5.dp)
    ) {
        Column {
            Text(text = week)
            Text(text = date)
        }
    }
}

@Composable
fun SellTicketText(sellCount: Int?, remainCount: Int?) {
    val text = "已售${sellCount}，余票${remainCount ?: "充足"}"
    Text(text = text, modifier = Modifier.padding(top = 3.dp))
}


/**
 * 站点筛选底部按钮兰
 */
@Composable
fun StationFilterBottomBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(1f) // 设置底部按钮的zIndex
    ) {
        // 起始站点
        var startStation by remember { mutableStateOf("出发站点") }
        TicketMobileSelection(
            options = listOf("站点1", "站点2", "站点3"),
            selectedOption = startStation,
            onOptionSelected = { startStation = it },
            expanded = false,
            modifier = Modifier.weight(1f)
        )
        // 结束站点
        var endStation by remember { mutableStateOf("到达站点") }
        TicketMobileSelection(
            options = listOf("站点4", "站点5很长的站点名称啊 啊啊啊啊啊", "站点6"),
            selectedOption = endStation,
            onOptionSelected = { endStation = it },
            expanded = false,
            modifier = Modifier.weight(1f)
        )
        // 结束站点
//        var endStation by remember { mutableStateOf("到达站点") }
        TicketMobileSelection(
            options = listOf("站点4", "站点5", "站点6"),
            selectedOption = endStation,
            onOptionSelected = { endStation = it },
            expanded = false,
            modifier = Modifier.weight(1f)
        )
    }
}
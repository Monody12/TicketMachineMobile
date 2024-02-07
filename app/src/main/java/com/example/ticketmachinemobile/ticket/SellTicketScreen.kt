package com.example.ticketmachinemobile.ticket

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ticketmachinemobile.components.TicketMobileSelection
import com.example.ticketmachinemobile.model.SellTicketViewModel
import com.example.ticketmachinemobile.ui.theme.TicketMachineMobileTheme
import com.example.ticketmachinemobile.util.DateUtil
import com.example.ticketmachinemobile.util.DateUtil.getTodayDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun SellTicketScreen() {
    val viewModel: SellTicketViewModel = viewModel()
    val shiftInfoList = viewModel.shiftInfoLiveData.observeAsState()
    // 界面启动时初始化
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getSaleTicket(getTodayDate(), 1)
    })

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
            ) {
                // 售票信息
                ShiftList(
                    showDialog = viewModel.stationDialogShow,
                    updateShiftClickEvent = viewModel::updateShiftClickEvent,
                    shiftInfoList = shiftInfoList.value ?: emptyList(),
                    { SellTicketText(22, null) }
                )
                StationDialogSelection(
                    showDialog = viewModel.stationDialogShow
                )
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
    val (dateList, weekList, formatStringList) = remember {
        DateUtil.generateDateAndDayLists()
    }
    // 创建LazyListState
    val listState = rememberLazyListState()
    Row {
        // 最近日期选择器
        LazyRow(
            state = listState, // 将LazyListState应用到LazyRow
            modifier = Modifier
                .weight(7f)
        ) {
            items(
                count = dateList.size,
                key = { index -> dateList[index] },
                itemContent = {
                    index ->
                    DateItem(weekList[index], dateList[index], formatStringList[index])
                }
            )
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
fun DateItem(week: String, date: String, formatString: String) {
    val viewModel: SellTicketViewModel = viewModel()
    val context = LocalContext.current
    Button(
        onClick = {
            viewModel.getSaleTicket(formatString, 1)
            Toast.makeText(context, formatString, Toast.LENGTH_SHORT).show()
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

@Preview
@Composable
fun SellTicketScreenPreview() {
    SellTicketScreen()
}
package com.example.ticketmachinemobile.ticket;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ticketmachinemobile.ui.theme.TicketMachineMobileTheme
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SellTicketScreen() {
    TicketMachineMobileTheme {
        Column {
            DateSelectionScreen()
            // 售票信息
            ShiftList({ SellTicketText(22, null) })
            CheckButtonRow()
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

@Composable
fun BottomSheetExample() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("主页", "我喜欢的", "设置")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("主页")
                },
                navigationIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation {
                items.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text("Content")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun PreviewDateSelectionScreen() {
    TicketMachineMobileTheme {
        CheckButtonRow()
    }
}
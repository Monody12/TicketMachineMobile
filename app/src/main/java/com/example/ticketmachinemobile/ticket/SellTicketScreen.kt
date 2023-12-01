package com.example.ticketmachinemobile.ticket;

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ticketmachinemobile.ui.theme.TicketMachineMobileTheme

@Composable
fun SellTicketScreen() {
    TicketMachineMobileTheme {
        Column {
            DateSelectionScreen()
        }
    }
}

@Composable
fun DateSelectionScreen() {
    var selectedDate by remember { mutableStateOf("2023-12-01") }
    // 获取当前系统时间的日期，并生成10天的日期列表，包含日期和星期


    Row {
        // 最近日期选择器
        LazyRow(
            modifier = Modifier
                .weight(7f)
                .height(120.dp)
        ) {
            items(10) { index ->
                DateItem(date = "12-0${index + 1}")
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
fun DateItem(week: String,date: String) {
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

@Preview(showBackground = true)
@Composable
fun PreviewDateSelectionScreen() {
    TicketMachineMobileTheme {
        DateSelectionScreen()
    }
}
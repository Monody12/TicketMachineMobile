package com.example.ticketmachinemobile.ticket

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.ticketmachinemobile.data.ShiftData
import com.example.ticketmachinemobile.data.ShiftRepository

/**
 * 班次显示布局
 */
@Composable
fun ShiftLayout(shiftData: ShiftData,vararg content: @Composable () -> Unit) {
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
            horizontalArrangement = Arrangement.SpaceBetween,
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 站点名称
                Text(
                    text = it.stationName,
                    style = textWhiteAndCenter,
                )
                // 上车人数
                if (it.getOnCount > 0)
                    Text(
                        text = it.getOnCount.toString() + "上",
                        style = textWhiteAndCenter,
                    )
                // 下车人数
                if (it.getOffCount > 0)
                    Text(
                        text = it.getOffCount.toString() + "下",
                        style = textWhiteAndCenter,
                    )
                // 到站时间
                Text(
                    text = it.arriveTime,
                    style = textWhiteAndCenter,
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
        content.forEach { composable ->
            composable()
        }

    }
}

/**
 * 班次列表
 */
@Composable
fun ShiftList(vararg content: @Composable () -> Unit) {
    val shiftDataList = ShiftRepository.getSimpleShiftList()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp)
    ) {
        items(shiftDataList.size) { index ->
            ShiftLayout(shiftDataList[index], *content)
        }
    }
}

/**
 * 检票按钮 Row
 */
@Composable
fun CheckButtonRow() {
    // 扫码检票、身份证检票、订单列表 按钮
    Box(modifier = Modifier.zIndex(1f)){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
            ,
            // 向右对齐
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 扫码检票
            Button(onClick = { /*TODO*/ }) {
                Text(
                    text = "扫码检票",
                    style = MaterialTheme.typography.body2.copy(color = Color.White).copy(textAlign = TextAlign.Center),

                    )
            }
            // 身份证检票
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(horizontal = 2.dp)) {
                Text(
                    text = "身份证检票",
                    style = MaterialTheme.typography.body2.copy(color = Color.White).copy(textAlign = TextAlign.Center),
                )
            }
            // 订单列表
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(end = 6.dp)) {
                Text(
                    text = "订单列表",
                    style = MaterialTheme.typography.body2.copy(color = Color.White).copy(textAlign = TextAlign.Center),
                )
            }
        }
    }
}
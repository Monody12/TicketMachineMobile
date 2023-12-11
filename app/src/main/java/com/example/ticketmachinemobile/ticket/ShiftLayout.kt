package com.example.ticketmachinemobile.ticket

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.ticketmachinemobile.activity.SellTicketPayActivity
import com.example.ticketmachinemobile.data.ShiftData
import com.example.ticketmachinemobile.data.ShiftRepository
import com.example.ticketmachinemobile.model.SellTicketViewModel

private val TAG = "ShiftLayout"

/**
 * 班次显示布局
 */
@Composable
fun ShiftLayout(
    shiftData: ShiftData,
    clickState: MutableState<Boolean>?,
    updateShiftClickEvent: ((Int?, String?) -> Unit)?,
    vararg content: @Composable () -> Unit
) {
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
        // 如果传入可点击的状态，则布局ui为可点击
        val clickableModifier = if (clickState != null) {
            Modifier.clickable {
                clickState.value = true
                if (updateShiftClickEvent != null) {
                    updateShiftClickEvent(shiftData.id, shiftData.lineData?.lineName)
                }
            }
        } else {
            Modifier
        }
        // 站点信息
        shiftData.lineData?.stationList?.forEach {
            Row(
                modifier = clickableModifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
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
fun ShiftList(
    showDialog: MutableState<Boolean> ?,
    updateShiftClickEvent: ((Int?, String?) -> Unit)?,
    vararg content: @Composable () -> Unit
) {
    val shiftDataList = ShiftRepository.getSimpleShiftList()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 2.dp)
            .zIndex(0f) // 设置LazyColumn的zIndex
    ) {
        items(shiftDataList.size) { index ->
            ShiftLayout(shiftDataList[index], showDialog, updateShiftClickEvent,*content)
        }
    }
}

/**
 * 检票按钮 Row
 */
@Composable
fun CheckButtonRow(modifier: Modifier = Modifier) {
    // 扫码检票、身份证检票、订单列表 按钮
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .zIndex(1f), // 设置底部按钮的zIndex
        // 向右对齐
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 扫码检票
        Button(onClick = { /*TODO*/ }) {
            Text(
                text = "扫码检票",
                style = MaterialTheme.typography.body2.copy(color = Color.White)
                    .copy(textAlign = TextAlign.Center),
            )
        }
        // 身份证检票
        Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(horizontal = 2.dp)) {
            Text(
                text = "身份证检票",
                style = MaterialTheme.typography.body2.copy(color = Color.White)
                    .copy(textAlign = TextAlign.Center),
            )
        }
        // 订单列表
        Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(end = 6.dp)) {
            Text(
                text = "订单列表",
                style = MaterialTheme.typography.body2.copy(color = Color.White)
                    .copy(textAlign = TextAlign.Center),
            )
        }
    }
}

/**
 * 选择下车地点
 */
@Composable
fun StationDialogSelection(
    showDialog: MutableState<Boolean>,
//    stationList: List<Station> = LineDataRepository.getSimpleLine().stationList,

    viewModel: SellTicketViewModel.Companion
) {
    val context = LocalContext.current
    // 站点列表。从viewModel中获取用户点击了哪个班次，然后获取班次对应的线路，然后获取该线路的站点列表
    val stationList = viewModel.shiftList.find { it.id == viewModel.shiftId.value }?.lineData?.stationList
        ?: listOf()
    if (showDialog.value)
        AlertDialog(
            onDismissRequest = {
                // 关闭 AlertDialog
                showDialog.value = false
            },
            title = {
                Text(text = "选择下车地点")
            },
            text = {
                // 列表中的选项（除掉第一个）
                Column(
                ) {
                    stationList.forEach {
                        Text(
                            text = it.stationName,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .padding(bottom = 13.dp)
                                .fillMaxWidth()
                                .clickable {
                                    showDialog.value = false
                                    viewModel.stationId.value = it.id
                                    viewModel.stationName.value = it.stationName
                                    // 启动售票支付Activity
                                    val intent = Intent(context, SellTicketPayActivity::class.java)
                                    context.startActivity(intent)
                                }
                        )
                    }
                }
            },
            confirmButton = {

            },
            dismissButton = {
                Button(
                    onClick = {
                        // 关闭 AlertDialog，不执行任何操作
                        showDialog.value = false
                    }
                ) {
                    Text(text = "取消")
                }
            }
        )
}
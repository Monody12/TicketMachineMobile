package com.example.ticketmachinemobile.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.ticketmachinemobile.ui.theme.TicketMachineMobileTheme

@Composable
fun OverviewScreen() {
    TicketMachineMobileTheme {
        Column {
            OverviewMain()
            OverviewIncomeDetail()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

@Composable
fun OverviewMain(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .border(2.dp, Color.White)
    ) {
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            // Row 垂直水平居中
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "用户名",
                    style = MaterialTheme.typography.h6
                )
                Text(text = "11月24日")
            }
//            Spacer(Modifier.padding(vertical = 16.dp))
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "今日需检乘客",
                    )
                    Text(
                        text = "66",
                        style = MaterialTheme.typography.h4,
                        // 居中
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                // 刷新Icon
                Row {
                    Text(text = "刷新")
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "刷新",
                            tint = Color.White
                        )
                    }
                }
            }
            // 开始检票按钮
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .background(Color.White)
            ) {
                Text(
                    text = "开始检票",
                    style = MaterialTheme.typography.h5
                )
            }
            // 开始售票按钮
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .background(Color.White)
            ) {
                Text(
                    text = "开始售票",
                    style = MaterialTheme.typography.h5
                )
            }
            // 扫码退票按钮
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .background(Color.White)
            ) {
                Text(
                    text = "扫码退票",
                    style = MaterialTheme.typography.h5
                )
            }
            // 扫码开票按钮
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .background(Color.White)
            ) {
                Text(
                    text = "扫码开票",
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }
}

@Composable
fun OverviewIncomeDetail() {
    var selectedOption by rememberSaveable { mutableStateOf("Option 1") }
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(16.dp)
            .border(2.dp, Color.White)
    ) {
        Column(

        ){
            // head
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    text = "实收明细"
                )

                Box {
                    // ClickableText is used as a button to trigger the dropdown
                    ClickableText(
                        text = AnnotatedString(selectedOption),
                        onClick = {
                            expanded = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.small)
                            .padding(8.dp),
                        style = MaterialTheme.typography.body1.copy(color = Color.White)
                    )

                    // 下拉选单，选择查询日期
                    DropdownMenu(
                        expanded = expanded, // set to true to show the dropdown initially
                        onDismissRequest = { /* Handle dismiss if needed */ },

                        ) {
                        DropdownMenuItem(onClick = {
                            selectedOption = "Option 1"
                            expanded = false
                        }) {
                            Text("Option 1")
                        }
                        DropdownMenuItem(onClick = {
                            selectedOption = "Option 2"
                            expanded = false
                        }) {
                            Text("Option 2")
                        }
                        DropdownMenuItem(onClick = {
                            selectedOption = "Option 3"
                            expanded = false
                        }) {
                            Text("Option 3")
                        }
                    }
                }

            }
            // detail
            IncomeDetail()

        }
    }
}

@Composable
fun IncomeDetail() {
    // 各支付方式具体收入
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 表格内容
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Text(text = "微信", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text(text = "支付宝", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text(text = "银行卡", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text(text = "现金", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Text(text = "1630元", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text(text = "690元", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text(text = "190元", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text(text = "180元", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Text(text = "100人次", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text(text = "80人次", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text(text = "11人次", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text(text = "9人次", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }
        }
    }
}

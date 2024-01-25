package com.example.ticketmachinemobile.overview

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.ticketmachinemobile.CheckTicket
import com.example.ticketmachinemobile.SellTicket
import com.example.ticketmachinemobile.activity.IDCardActivity
import com.example.ticketmachinemobile.activity.LocalNavController
import com.example.ticketmachinemobile.components.TicketMobileSelection
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
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(16.dp)
//            .border(2.dp, Color.White)
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
                IconButton(onClick = {
                    val intent =Intent(context, IDCardActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "刷新",
                        tint = Color.White
                    )
                }
            }
            // 获取NavController用于按钮导航
            val navController = LocalNavController.current
            // 开始检票按钮
            Button(
                onClick = { navController.navigate(CheckTicket.route) },
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
                onClick = { navController.navigate(SellTicket.route) },
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
    var selectedOption by rememberSaveable { mutableStateOf("今日") }
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(16.dp)
//            .border(2.dp, Color.White)
    ) {
        Column(

        ) {
            // head
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    text = "实收明细",
                )

                TicketMobileSelection(
                    options = listOf("今日", "昨日", "近一周"),
                    selectedOption = selectedOption,
                    onOptionSelected = {
                        selectedOption = it
                    },
                    expanded =  expanded
                )

            }
            // detail
            IncomeDetail()

        }
    }
}

@Composable
fun IncomeDetail() {
    val textModifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 5.dp)

    // 各支付方式具体收入
    Column(
        modifier = Modifier
            .fillMaxSize()
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
                Text(text = "微信", modifier = textModifier, textAlign = TextAlign.Center)
                Text(text = "支付宝", modifier = textModifier, textAlign = TextAlign.Center)
                Text(text = "银行卡", modifier = textModifier, textAlign = TextAlign.Center)
                Text(text = "现金", modifier = textModifier, textAlign = TextAlign.Center)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Text(text = "1630元", modifier = textModifier, textAlign = TextAlign.Center)
                Text(text = "690元", modifier = textModifier, textAlign = TextAlign.Center)
                Text(text = "190元", modifier = textModifier, textAlign = TextAlign.Center)
                Text(text = "180元", modifier = textModifier, textAlign = TextAlign.Center)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Text(text = "100人次", modifier = textModifier, textAlign = TextAlign.Center)
                Text(text = "80人次", modifier = textModifier, textAlign = TextAlign.Center)
                Text(text = "11人次", modifier = textModifier, textAlign = TextAlign.Center)
                Text(text = "9人次", modifier = textModifier, textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview
@Composable
fun OverviewScreenPreview() {
    TicketMachineMobileTheme {
        Column {
            OverviewMain()
            OverviewIncomeDetail()
        }
    }
}
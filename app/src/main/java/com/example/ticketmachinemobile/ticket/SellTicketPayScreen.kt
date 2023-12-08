package com.example.ticketmachinemobile.ticket

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.ticketmachinemobile.activity.SellTicketPayActivity
import com.example.ticketmachinemobile.ui.theme.TicketMachineMobileTheme

@Composable
fun SellTicketPayScreen () {
    TicketMachineMobileTheme {
        SellTicketPayTopBar()
    }
}

@Composable
fun SellTicketPayTopBar(){
    val context = LocalContext.current
    // 顶部应用栏
    TopAppBar(
        modifier = Modifier,
        title = { Text("这是标题") },
        navigationIcon = {
            IconButton(onClick = {
                // 结束当前页面
                (context as SellTicketPayActivity).finish()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "返回",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "分享",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "设置",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
    )
}

@Preview(showBackground = true)
@Composable
fun SellTicketPayScreenPreview() {
    SellTicketPayScreen()
}
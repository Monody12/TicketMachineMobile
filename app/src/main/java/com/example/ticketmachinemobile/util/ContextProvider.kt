package com.example.ticketmachinemobile.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

object ContextProvider {
    @Composable
    fun provideContext(): Context {
        // 使用remember确保只在首次调用时计算一次，以防止在组件重组时多次计算
        return LocalContext.current
    }
}

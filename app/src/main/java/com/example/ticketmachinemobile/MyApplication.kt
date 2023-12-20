package com.example.ticketmachinemobile

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.ticketmachinemobile.model.SellTicketViewModel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication(override val viewModelStore: ViewModelStore) : Application(), ViewModelStoreOwner {

    private lateinit var mAppViewModelStore: ViewModelStore
    private var mFactory: ViewModelProvider.Factory? = null

    val sellTicketViewModel: SellTicketViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this)
            .create(SellTicketViewModel::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        // 创建ViewModelStore
        mAppViewModelStore = ViewModelStore()

    }
}
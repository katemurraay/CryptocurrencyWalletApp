package com.example.cryptocurrencywalletapp

import android.app.Application
import com.example.cryptocurrencywalletapp.data.remote.WalletDatabase
import com.example.cryptocurrencywalletapp.data.remote.WalletDatabase_Impl
import com.example.cryptocurrencywalletapp.data.repository.WalletRepositoryImpl
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class CoinApplication : Application() {

private val applicationScope = CoroutineScope(SupervisorJob())

    val walletRepository by lazy {

            val database = WalletDatabase.getDatabase(getApplicationContext()).getWalletDao()
            WalletRepositoryImpl(
                database,
                applicationScope
            )
}
}
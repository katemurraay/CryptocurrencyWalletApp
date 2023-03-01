package com.example.cryptocurrencywalletapp

import android.app.Application
import com.example.cryptocurrencywalletapp.data.local.CryptoDatabase
import com.example.cryptocurrencywalletapp.data.repository.WalletRepositoryImpl
import com.example.cryptocurrencywalletapp.domain.model.User
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class CryptoApplication : Application(){
    companion object{
        @JvmField
        var user: User? = null

        @JvmStatic
        fun getGlobalUser(): User? {
            return user
        }


    }
}

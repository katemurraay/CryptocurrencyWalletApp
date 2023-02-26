package com.example.cryptocurrencywalletapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.cryptocurrencywalletapp.data.remote.dto.WalletDTO

interface WalletRepository {

    suspend fun getAllWallets(): List<WalletDTO>

    suspend fun insertWallet(walletDTO: WalletDTO)

    suspend fun deleteWallet(walletDTO: WalletDTO)

    suspend fun updateWallet(id: Int?, title: String?, coins: List<String>)
}
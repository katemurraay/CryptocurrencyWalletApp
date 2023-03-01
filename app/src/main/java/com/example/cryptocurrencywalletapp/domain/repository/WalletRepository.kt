package com.example.cryptocurrencywalletapp.domain.repository

import com.example.cryptocurrencywalletapp.domain.model.Wallet
import com.example.cryptocurrencywalletapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WalletRepository {

    fun getAllWallets(): Flow<Resource<List<Wallet>>>

    suspend fun insertWallet(wallet: Wallet)

    suspend fun deleteWallet(wallet: Wallet)

    suspend fun updateWallet(id: Int?, title: String?, coins: List<String>)
}
package com.example.cryptocurrencywalletapp.domain.repository

import com.example.cryptocurrencywalletapp.data.local.WalletEntity

interface WalletRepository {

    suspend fun getAllWallets(): List<WalletEntity>

    suspend fun insertWallet(walletEntity: WalletEntity)

    suspend fun deleteWallet(walletEntity: WalletEntity)

    suspend fun updateWallet(id: Int?, title: String?, coins: List<String>)
}
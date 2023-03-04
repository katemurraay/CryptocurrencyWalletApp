package com.example.cryptocurrencywalletapp.domain.repository

import com.example.cryptocurrencywalletapp.domain.model.Coin
import com.example.cryptocurrencywalletapp.domain.model.Wallet
import com.example.cryptocurrencywalletapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WalletRepository {

    fun getAllWallets(): Flow<Resource<List<Wallet>>>
    fun insertWallet(wallet: Wallet): Flow<Resource<Wallet>>

    fun deleteWallet(wallet: Wallet): Flow<Resource<List<Wallet>>>

    fun updateWallet(id: Long?, title: String?, userId: Long): Flow<Resource<Wallet>>
    fun addCoinToWallet(coinSymbol: String, walletId: Long): Flow<Resource<Wallet>>
    fun removeCoinFromWallet(coinSymbol: String, walletId: Long): Flow<Resource<Wallet>>
}
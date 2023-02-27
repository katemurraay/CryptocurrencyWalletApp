package com.example.cryptocurrencywalletapp.data.repository

import com.example.cryptocurrencywalletapp.data.local.WalletDAO
import com.example.cryptocurrencywalletapp.data.local.WalletEntity
import com.example.cryptocurrencywalletapp.domain.model.Wallet
import com.example.cryptocurrencywalletapp.domain.repository.WalletRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val walletDAO: WalletDAO, private val scope: CoroutineScope,) :WalletRepository{



    override suspend fun getAllWallets(): List<WalletEntity> {

        return walletDAO.getWallets()
    }

    override suspend fun insertWallet(walletEntity: WalletEntity) {
      walletDAO.insertWallet(walletEntity)
    }

    override suspend fun deleteWallet(walletEntity: WalletEntity) {
        walletDAO.deleteWallet(walletEntity)
    }

    override suspend fun updateWallet(id: Int?, title: String?, coins: List<String>) {
       walletDAO.updateWallet(id, title, coins)
    }


}
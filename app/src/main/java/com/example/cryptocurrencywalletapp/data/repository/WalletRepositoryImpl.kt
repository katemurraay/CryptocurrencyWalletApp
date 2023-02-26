package com.example.cryptocurrencywalletapp.data.repository

import androidx.lifecycle.LiveData
import com.example.cryptocurrencywalletapp.data.remote.WalletDAO
import com.example.cryptocurrencywalletapp.data.remote.dto.CoinDTO
import com.example.cryptocurrencywalletapp.data.remote.dto.WalletDTO
import com.example.cryptocurrencywalletapp.domain.model.Coin
import com.example.cryptocurrencywalletapp.domain.model.Wallet
import com.example.cryptocurrencywalletapp.domain.repository.WalletRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val
                           walletDAO: WalletDAO, private val scope: CoroutineScope,) :WalletRepository{

    fun WalletDTO.toWallet(): Wallet {
        return Wallet(
            id = id,
            title = title,
            coins = coins?.split(",")
       )
    }

    override suspend fun getAllWallets(): List<WalletDTO> {

        return walletDAO.getWallets()
    }

    override suspend fun insertWallet(walletDTO: WalletDTO) {
      walletDAO.insertWallet(walletDTO)
    }

    override suspend fun deleteWallet(walletDTO: WalletDTO) {
        walletDAO.deleteWallet(walletDTO)
    }

    override suspend fun updateWallet(id: Int?, title: String?, coins: List<String>) {
       walletDAO.updateWallet(id, title, coins)
    }


}
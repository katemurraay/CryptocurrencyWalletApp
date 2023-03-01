package com.example.cryptocurrencywalletapp.data.repository

import com.example.cryptocurrencywalletapp.data.local.wallet.WalletDAO
import com.example.cryptocurrencywalletapp.data.mapper.toWallet
import com.example.cryptocurrencywalletapp.data.mapper.toWalletEntity
import com.example.cryptocurrencywalletapp.domain.model.Wallet
import com.example.cryptocurrencywalletapp.domain.repository.WalletRepository
import com.example.cryptocurrencywalletapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val walletDAO: WalletDAO
) :WalletRepository{



    override fun getAllWallets(): Flow<Resource<List<Wallet>>> {
    return flow {
        try {
            emit(Resource.Loading(false))

        } catch (e: IOException){
            e.printStackTrace()
            emit(Resource.Error(e.toString()))

        }
    }

    }


    override suspend fun insertWallet(wallet: Wallet) {
        val walletEntity = wallet.toWalletEntity()
        walletDAO.insertWallet(walletEntity)
    }

    override suspend fun deleteWallet(wallet: Wallet) {
        val walletEntity = wallet.toWalletEntity()
        walletDAO.deleteWallet(walletEntity)
    }

    override suspend fun updateWallet(id: Int?, title: String?, coins: List<String>) {
       walletDAO.updateWallet(id, title)
    }


}
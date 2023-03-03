package com.example.cryptocurrencywalletapp.data.repository

import androidx.room.withTransaction
import com.example.cryptocurrencywalletapp.CryptoApplication
import com.example.cryptocurrencywalletapp.data.local.CryptoDatabase
import com.example.cryptocurrencywalletapp.data.local.wallet.WalletCoinCrossRefEntity
import com.example.cryptocurrencywalletapp.data.local.wallet.WalletDAO
import com.example.cryptocurrencywalletapp.data.mapper.toWallet
import com.example.cryptocurrencywalletapp.data.mapper.toWalletEntity
import com.example.cryptocurrencywalletapp.domain.model.Coin
import com.example.cryptocurrencywalletapp.domain.model.Wallet
import com.example.cryptocurrencywalletapp.domain.repository.WalletRepository
import com.example.cryptocurrencywalletapp.presentation.coinList.CoinListState
import com.example.cryptocurrencywalletapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val db: CryptoDatabase
) :WalletRepository{


    private val dao = db.getWalletDao()
    private val coinDAO = db.getCoinDao()
    private val walletWithCoinsDao = db.getWalletWithCoinsDao()

    override fun getAllWallets(): Flow<Resource<List<Wallet>>> {
    return flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Loading(false))
            val userId = CryptoApplication.user!!.userId
            val userWallets = dao.getUsersWithWallets(userId!!)
            if (userWallets.wallets.isNotEmpty()) {
                val wallets = userWallets.wallets.map { it.toWallet() }
                emit(Resource.Success(data = wallets))
            } else {
                emit(Resource.Success(data = emptyList<Wallet>()))
            }
        } catch (e: IOException){
            e.printStackTrace()
            emit(Resource.Error(e.toString()))

        }
    }

    }


    //TO DO


    override fun insertWallet(wallet: Wallet): Flow<Resource<List<Wallet>>>{
        return flow{
            emit(Resource.Loading())
            try {

                db.withTransaction {
                    val walletEntity = wallet.toWalletEntity()
                    dao.insertWallet(walletEntity)

                }
                val userWallets = dao.getUsersWithWallets(userId = CryptoApplication.user?.userId!!)
                val data = userWallets.wallets.map {
                    it.toWallet()
                }
                emit(Resource.Success(data))
            } catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error(e.toString()))
            }
        }
    }

    override fun deleteWallet(wallet: Wallet):  Flow<Resource<List<Wallet>>>{
        return flow{
            emit(Resource.Loading())
            val coins = wallet.coins
            try {
                db.withTransaction {
                    val walletEntity = wallet.toWalletEntity()
                    dao.deleteWallet(walletEntity)
                    coins!!.map {
                        val coinId = coinDAO.getCoinsBySymbol(it.symbol).coinId
                        val walletCoinCrossRefEntity =
                            WalletCoinCrossRefEntity(walletEntity.walletId!!, coinId!!)
                        walletWithCoinsDao.deleteWalletWithCoins(walletCoinCrossRefEntity)
                    }
                }
                val userWallets = dao.getUsersWithWallets(userId = CryptoApplication.user?.userId!!)
                val data = userWallets.wallets.map {
                    it.toWallet()
                }
                emit(Resource.Success(data))
            } catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error(e.toString()))
            }
        }

    }



  override fun updateWallet(id: Long?, title: String?): Flow<Resource<List<Wallet>>>{
     return flow {
         emit(Resource.Loading())
         try{
             dao.updateWallet(id, title)
             val userWallets = dao.getUsersWithWallets(userId = CryptoApplication.user?.userId!!)
             val data = userWallets.wallets.map {
                 it.toWallet()
             }
             emit(Resource.Success(data))
         } catch(e: IOException){
             e.printStackTrace()
             emit(Resource.Error(e.toString()))
         }
     }
    }

    override fun addCoinToWallet(coin: Coin, walledId: Long): Flow<Resource<Wallet>>{
        return flow{
            try{
                db.withTransaction {
                    val coinEntity = coinDAO.getCoinsBySymbol(coin.symbol)
                    val walletCoinCrossRefEntity =
                        WalletCoinCrossRefEntity(walledId, coinEntity.coinId!!)
                    walletWithCoinsDao.insertWalletWithCoins(walletCoinCrossRefEntity)
                }
                val userWallets = dao.getUsersWithWallets(userId = CryptoApplication.user?.userId!!)
                val wallets = userWallets.wallets.filter { it.wallet.walletId == walledId }
                val data = wallets[0].toWallet()
                emit(Resource.Success(data))
            } catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error(e.toString()))
            }
        }
        }

    override fun removeCoinFromWallet(coin: Coin, walledId: Long): Flow<Resource<Wallet>>{
        return flow{
            try{
                db.withTransaction {
                    val coinEntity = coinDAO.getCoinsBySymbol(coin.symbol)
                    val walletCoinCrossRefEntity =
                        WalletCoinCrossRefEntity(walledId, coinEntity.coinId!!)
                    walletWithCoinsDao.deleteWalletWithCoins(walletCoinCrossRefEntity)
                }
                val userWallets = dao.getUsersWithWallets(userId = CryptoApplication.user?.userId!!)
                val wallets = userWallets.wallets.filter { it.wallet.walletId == walledId }
                val data = wallets[0].toWallet()
                emit(Resource.Success(data))
            } catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error(e.toString()))
            }
        }
    }
    }

package com.example.cryptocurrencywalletapp.data.remote

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cryptocurrencywalletapp.data.remote.dto.WalletDTO
import com.example.cryptocurrencywalletapp.domain.model.Coin

@Dao
interface WalletDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(walletDTO: WalletDTO)

    @Delete
    suspend fun deleteWallet(walletDTO: WalletDTO)

    @Query("SELECT * FROM wallet_table ORDER BY id ASC")
    suspend fun getWallets(): List<WalletDTO>

    @Query("UPDATE wallet_table SET title = :title, coins = :coins WHERE id = :id")
    suspend fun updateWallet(id: Int?, title: String?, coins: List<String>)
}
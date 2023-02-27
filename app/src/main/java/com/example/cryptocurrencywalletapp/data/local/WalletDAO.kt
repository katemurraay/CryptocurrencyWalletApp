package com.example.cryptocurrencywalletapp.data.local

import androidx.room.*

@Dao
interface WalletDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(walletEntity: WalletEntity)

    @Delete
    suspend fun deleteWallet(walletEntity: WalletEntity)

    @Query("SELECT * FROM wallet_table ORDER BY id ASC")
    suspend fun getWallets(): List<WalletEntity>

    @Query("UPDATE wallet_table SET title = :title, coins = :coins WHERE id = :id")
    suspend fun updateWallet(id: Int?, title: String?, coins: List<String>)
}
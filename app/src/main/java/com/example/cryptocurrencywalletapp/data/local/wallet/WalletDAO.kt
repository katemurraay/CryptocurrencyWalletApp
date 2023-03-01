package com.example.cryptocurrencywalletapp.data.local.wallet

import androidx.room.*

@Dao
interface WalletDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(walletEntity: WalletEntity)

    @Delete
    suspend fun deleteWallet(walletEntity: WalletEntity)
    @Transaction
    @Query("SELECT * FROM user_table")
    suspend fun getUsersWithWallets(): List<UserWithWalletandCoinsEntity>


    @Query("UPDATE wallet_table SET title = :title WHERE walletId = :id")
    suspend fun updateWallet(id: Int?, title: String?)


}
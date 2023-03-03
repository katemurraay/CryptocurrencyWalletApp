package com.example.cryptocurrencywalletapp.data.local.wallet

import androidx.room.*
import com.example.cryptocurrencywalletapp.domain.model.Wallet

@Dao
interface WalletDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(walletEntity: WalletEntity)

    @Delete
    suspend fun deleteWallet(walletEntity: WalletEntity)
    @Transaction
    @Query("SELECT * FROM user_table where userId = :userId")
    suspend fun getUsersWithWallets(userId: Long): UserWithWalletAndCoinsRelationship

    @Query("SELECT * FROM wallet_table WHERE title = :title")
    suspend fun getWalletById(title: String?): WalletEntity
    @Query("UPDATE wallet_table SET title = :title WHERE walletId = :id")
    suspend fun updateWallet(id: Long?, title: String?)


}
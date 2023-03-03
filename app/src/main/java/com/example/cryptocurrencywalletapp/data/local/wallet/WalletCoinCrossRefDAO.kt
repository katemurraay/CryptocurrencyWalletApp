package com.example.cryptocurrencywalletapp.data.local.wallet

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface WalletCoinCrossRefDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWalletWithCoins(walletCoinCrossRefEntity: WalletCoinCrossRefEntity)

    @Delete
    suspend fun deleteWalletWithCoins(walletCoinCrossRefEntity: WalletCoinCrossRefEntity)

}
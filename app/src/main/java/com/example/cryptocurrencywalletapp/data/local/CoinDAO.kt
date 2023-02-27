package com.example.cryptocurrencywalletapp.data.local

import androidx.room.*
@Dao
interface CoinDAO {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertCoin(coinEntity: CoinEntity)

        @Query("SELECT * FROM coin_table ORDER BY USD DESC")
        suspend fun getCoins(): List<CoinEntity>

        @Query("DELETE FROM coin_table")
        suspend fun clearCoinListings()

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertCoinListings(coins: List<CoinEntity>)


}
package com.example.cryptocurrencywalletapp.data.local.coin

import androidx.room.*
import com.example.cryptocurrencywalletapp.data.local.coin.CoinEntity

@Dao
interface CoinDAO {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertCoin(coinEntity: CoinEntity)

        @Query("SELECT * FROM coin_table ORDER BY USD DESC")
        suspend fun getCoins(): List<CoinEntity>

        @Query("SELECT * FROM coin_table WHERE symbol = :symbol")
        suspend fun getCoinsBySymbol(symbol:String): CoinEntity
        @Query("DELETE FROM coin_table")
        suspend fun clearCoinListings()

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertCoinListings(coins: List<CoinEntity>)



}
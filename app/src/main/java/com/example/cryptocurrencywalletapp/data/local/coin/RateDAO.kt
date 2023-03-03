package com.example.cryptocurrencywalletapp.data.local.coin

import androidx.room.*

@Dao
interface RateDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rateEntity: List<RateEntity>)

    @Query("DELETE FROM rate_table WHERE coinExchangeId = :id")
    suspend fun clearCoinRates(id: Long)
    @Transaction

    @Query("SELECT * FROM rate_table, coin_table WHERE rate_table.coinExchangeId = :id AND coin_table.coinId = :id")
    suspend fun getCoinRates(id: Long): CoinExchangeRelationship?
}
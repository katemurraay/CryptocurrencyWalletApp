package com.example.cryptocurrencywalletapp.data.remote

import com.example.cryptocurrencywalletapp.data.remote.dto.CoinDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinAPI {
    @GET("v1/assets")
    suspend fun getCoins():  List<CoinDTO>

    @GET("v1/coins/{coinId}")
    suspend fun getCoinById(@Path("coinId") coinId: String)
}
package com.example.cryptocurrencywalletapp.data.remote

import com.example.cryptocurrencywalletapp.data.remote.dto.CoinDTO
import com.example.cryptocurrencywalletapp.data.remote.dto.CoinExchangeDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinAPI {
    @GET("v1/assets")
    suspend fun getCoins():  MutableList<CoinDTO>

    @GET("v1/exchangerate/{coinId}?invert=false")
    suspend fun getCoinExchangeInfo(@Path("coinId") coinOne: String): CoinExchangeDTO
}
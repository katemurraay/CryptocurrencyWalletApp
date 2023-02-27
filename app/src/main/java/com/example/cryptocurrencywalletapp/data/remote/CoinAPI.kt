package com.example.cryptocurrencywalletapp.data.remote

import android.content.Context
import com.example.cryptocurrencywalletapp.data.remote.dto.CoinDTO
import com.example.cryptocurrencywalletapp.data.remote.dto.CoinExchangeDTO
import com.example.cryptocurrencywalletapp.domain.model.Coin
import com.example.cryptocurrencywalletapp.domain.repository.CoinRepository
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject


interface CoinAPI {
    @GET("v1/assets")
    suspend fun getCoins():  MutableList<CoinDTO>

    @GET("v1/exchangerate/{coinId}?invert=false")
    suspend fun getCoinExchangeInfo(@Path("coinId") coinOne: String): CoinExchangeDTO
}


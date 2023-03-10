package com.example.cryptocurrencywalletapp.domain.repository

import com.example.cryptocurrencywalletapp.data.remote.dto.CoinDTO
import com.example.cryptocurrencywalletapp.data.remote.dto.CoinExchangeDTO
import com.example.cryptocurrencywalletapp.domain.model.Coin
import com.example.cryptocurrencywalletapp.domain.model.CoinExchange
import com.example.cryptocurrencywalletapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    fun getAllCoins(): Flow<Resource<List<Coin>>>

     fun getCoinExchange(coin: Coin): Flow<Resource<CoinExchange>>
}
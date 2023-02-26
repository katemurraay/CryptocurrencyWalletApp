package com.example.cryptocurrencywalletapp.domain.repository

import com.example.cryptocurrencywalletapp.data.remote.dto.CoinDTO

interface CoinRepository {

    suspend fun getCoins(): List<CoinDTO>
}
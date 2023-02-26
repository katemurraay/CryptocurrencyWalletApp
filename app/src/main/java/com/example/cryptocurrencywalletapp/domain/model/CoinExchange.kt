package com.example.cryptocurrencywalletapp.domain.model

import com.example.cryptocurrencywalletapp.data.remote.dto.Rate

data class CoinExchange( val id: String,
                         val rates: List<Rate>)

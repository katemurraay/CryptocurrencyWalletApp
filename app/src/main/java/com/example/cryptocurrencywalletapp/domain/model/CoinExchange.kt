package com.example.cryptocurrencywalletapp.domain.model


data class CoinExchange( val id: String,
                         val coin: Coin,
                         val rates: List<Rate>)

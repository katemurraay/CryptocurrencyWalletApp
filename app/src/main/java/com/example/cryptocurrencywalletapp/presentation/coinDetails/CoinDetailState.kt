package com.example.cryptocurrencywalletapp.presentation.coinDetails

import com.example.cryptocurrencywalletapp.domain.model.Coin
import com.example.cryptocurrencywalletapp.domain.model.CoinExchange

sealed class CoinDetailState {
    object Loading : CoinDetailState()
    data class Success(val coinExchange: CoinExchange) : CoinDetailState()
    data class Error(val error: String) : CoinDetailState()
}

package com.example.cryptocurrencywalletapp.presentation.coinList

import com.example.cryptocurrencywalletapp.domain.model.Coin
sealed class CoinListState {
    object Loading : CoinListState()
    data class Success(val coinList: List<Coin>) : CoinListState()
    data class Error(val error: String) : CoinListState()
}

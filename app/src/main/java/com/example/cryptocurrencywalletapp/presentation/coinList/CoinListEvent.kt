package com.example.cryptocurrencywalletapp.presentation.coinList

sealed class CoinListEvent{
    object Refresh: CoinListEvent()
    data class OnSearchQueryChange(val query: String): CoinListEvent()
}

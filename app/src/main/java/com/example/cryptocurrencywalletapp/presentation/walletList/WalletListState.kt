package com.example.cryptocurrencywalletapp.presentation.walletList

import com.example.cryptocurrencywalletapp.domain.model.Wallet


sealed class WalletListState {
    object Loading : WalletListState ()
    data class Success(val coinList: List<Wallet>) : WalletListState()
    data class Error(val error: String) : WalletListState()
}
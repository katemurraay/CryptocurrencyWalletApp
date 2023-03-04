package com.example.cryptocurrencywalletapp.presentation.coinDetails

import com.example.cryptocurrencywalletapp.domain.model.Wallet

sealed class AddCoinToWalletState {

        object Loading : AddCoinToWalletState()
        data class Success(val wallet: Wallet?) : AddCoinToWalletState()
        data class Error(val error: String) : AddCoinToWalletState()

}
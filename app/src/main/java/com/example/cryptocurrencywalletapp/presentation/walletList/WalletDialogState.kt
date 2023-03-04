package com.example.cryptocurrencywalletapp.presentation.walletList

import com.example.cryptocurrencywalletapp.domain.model.Wallet

sealed class WalletDialogState {
         object Loading : WalletDialogState()
        data class Success(val wallet: Wallet?) : WalletDialogState()
        data class Error(val error: String) : WalletDialogState()
}
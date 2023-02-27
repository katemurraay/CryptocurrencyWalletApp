package com.example.cryptocurrencywalletapp.data.mapper

import com.example.cryptocurrencywalletapp.data.local.WalletEntity
import com.example.cryptocurrencywalletapp.domain.model.Wallet

fun WalletEntity.toWallet(): Wallet {
    return Wallet(
        id = id,
        title = title,
        coins = coins?.split(",")
    )
}

fun Wallet.toWalletEntity(): WalletEntity{
    return WalletEntity(
        title = title,
        coins = coins?.joinToString(separator = ",")
    )
}
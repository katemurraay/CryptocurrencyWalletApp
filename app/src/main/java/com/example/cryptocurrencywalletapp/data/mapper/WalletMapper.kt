package com.example.cryptocurrencywalletapp.data.mapper

import com.example.cryptocurrencywalletapp.data.local.wallet.WalletEntity
import com.example.cryptocurrencywalletapp.domain.model.Wallet

fun WalletEntity.toWallet(): Wallet {
    return Wallet(
        id =walletId,
        title = title,
        userCreatorId = userCreatorId
    )
}

fun Wallet.toWalletEntity(): WalletEntity {
    return WalletEntity(
        title = title,
        userCreatorId = userCreatorId
    )
}
package com.example.cryptocurrencywalletapp.data.mapper

import com.example.cryptocurrencywalletapp.data.local.wallet.WalletEntity
import com.example.cryptocurrencywalletapp.data.local.wallet.WalletWithCoinsRelationship
import com.example.cryptocurrencywalletapp.domain.model.Wallet


fun WalletWithCoinsRelationship.toWallet(): Wallet{
    return Wallet(
        id = this.wallet.walletId,
        title = this.wallet.title,
        userCreatorId = this.wallet.userCreatorId,
        coins = this.coins.map { it.toCoin() }
    )
}
fun Wallet.toWalletEntity(): WalletEntity {
    return WalletEntity(
        title = title,
        userCreatorId = userCreatorId
    )
}
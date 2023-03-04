package com.example.cryptocurrencywalletapp.data.mapper

import androidx.compose.ui.Modifier
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
    fun WalletEntity.toWallet(userId: Long): Wallet {
        return Wallet(
            id = walletId,
            title = title,
            userCreatorId = userId,
            coins = emptyList()
        )
    }

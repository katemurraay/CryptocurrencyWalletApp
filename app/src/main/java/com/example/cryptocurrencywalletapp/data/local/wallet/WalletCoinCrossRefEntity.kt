package com.example.cryptocurrencywalletapp.data.local.wallet

import androidx.room.Entity

@Entity(primaryKeys = ["walletId", "coinId"])
data class WalletCoinCrossRefEntity(
    val walletId: Long,
    val coinId: Long
)
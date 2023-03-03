package com.example.cryptocurrencywalletapp.data.local.wallet

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.cryptocurrencywalletapp.data.local.coin.CoinEntity

data class WalletWithCoinsRelationship(
    @Embedded val wallet: WalletEntity,
    @Relation(
      parentColumn = "walletId",
      entityColumn = "coinId",
      associateBy = Junction(WalletCoinCrossRefEntity::class)
  )
  val coins: List<CoinEntity>)

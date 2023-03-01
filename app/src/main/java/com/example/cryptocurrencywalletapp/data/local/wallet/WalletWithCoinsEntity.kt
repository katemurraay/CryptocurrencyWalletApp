package com.example.cryptocurrencywalletapp.data.local.wallet

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.example.cryptocurrencywalletapp.data.local.coin.CoinEntity
import com.example.cryptocurrencywalletapp.data.local.wallet.WalletCoinCrossRefEntity
import com.example.cryptocurrencywalletapp.data.local.wallet.WalletEntity
import com.example.cryptocurrencywalletapp.domain.model.Coin

data class WalletWithCoinsEntity(
    @Embedded val wallet: WalletEntity,
    @Relation(
      parentColumn = "walletId",
      entityColumn = "coinId",
      associateBy = Junction(WalletCoinCrossRefEntity::class)
  )
  val coins: List<CoinEntity>)

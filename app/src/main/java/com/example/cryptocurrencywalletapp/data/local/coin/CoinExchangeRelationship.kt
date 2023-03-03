package com.example.cryptocurrencywalletapp.data.local.coin

import androidx.room.Embedded
import androidx.room.Relation

data class CoinExchangeRelationship(
    @Embedded val coin: CoinEntity,
    @Relation(
        parentColumn = "coinId",
        entityColumn = "coinExchangeId"
    )
    val rates: List<RateEntity>)


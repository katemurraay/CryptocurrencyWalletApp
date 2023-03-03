package com.example.cryptocurrencywalletapp.data.local.coin

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = CoinEntity::class,
        parentColumns = arrayOf("coinId"),
        childColumns = arrayOf("coinExchangeId"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )], tableName = "rate_table"
)
data class RateEntity(
    @PrimaryKey(autoGenerate = true) val rateId:  Long? = null,
    val coinExchangeId: Long,
    val name: String,
    val rate: Double,
    val time: String)

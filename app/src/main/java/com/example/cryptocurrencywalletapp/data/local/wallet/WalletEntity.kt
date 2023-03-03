package com.example.cryptocurrencywalletapp.data.local.wallet

import androidx.room.*
import com.example.cryptocurrencywalletapp.data.local.coin.CoinEntity
import com.example.cryptocurrencywalletapp.data.local.user.UserEntity


@Entity(
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = arrayOf("userId"),
        childColumns = arrayOf("user_creator_id"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )], tableName = "wallet_table")

data class WalletEntity(
    @PrimaryKey(autoGenerate = true) val walletId: Long? = null ,
    @ColumnInfo(name = "user_creator_id") val userCreatorId: Long,
    @ColumnInfo(name = "title") val title: String?,
)


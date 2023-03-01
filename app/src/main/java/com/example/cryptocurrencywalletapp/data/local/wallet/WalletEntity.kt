package com.example.cryptocurrencywalletapp.data.local.wallet

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "wallet_table")
data class WalletEntity(
    @PrimaryKey(autoGenerate = true) val walletId: Long? = null ,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "user_creator_id") val userCreatorId: Long,
)


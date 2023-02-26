package com.example.cryptocurrencywalletapp.data.remote.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallet_table")
data class WalletDTO(
@PrimaryKey(autoGenerate = true) val id: Int?,
@ColumnInfo(name = "title") val title: String?,
@ColumnInfo(name="coins") val coins: String?)

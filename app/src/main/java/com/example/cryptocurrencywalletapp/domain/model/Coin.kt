package com.example.cryptocurrencywalletapp.domain.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coin(val symbol: String,
                val isCrypto: Int,
                val name: String,
                val price: Double,


                ):Parcelable


package com.example.cryptocurrencywalletapp.domain.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Coin( val id: String,
                    val isCrypto: Int,
                    val name: String,
                    val price: Double,


):Parcelable


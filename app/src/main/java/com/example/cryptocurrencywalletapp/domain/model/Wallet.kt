package com.example.cryptocurrencywalletapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wallet(
     val id: Long?,
     val userCreatorId: Long,
     val title: String?,
     val coins: List<Coin>?): Parcelable{

     override fun toString(): String {
          return title!!
     }
     }

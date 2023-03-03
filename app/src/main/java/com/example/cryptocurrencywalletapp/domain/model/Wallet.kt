package com.example.cryptocurrencywalletapp.domain.model


data class Wallet(
     val id: Long?,
     val userCreatorId: Long,
     val title: String?,
     val coins: List<Coin>?)

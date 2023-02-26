package com.example.cryptocurrencywalletapp.data.remote.dto

data class Rate(
    val asset_id_quote: String,
    val rate: Double,
    val time: String
)
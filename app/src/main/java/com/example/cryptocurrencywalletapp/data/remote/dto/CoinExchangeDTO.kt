package com.example.cryptocurrencywalletapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoinExchangeDTO(
    val time: String,
    @SerializedName("asset_id_base")
    val assetIdBase: String,
    @SerializedName("asset_id_quote")
    val assetIdQuote: String,
    val rate: Double
)
package com.example.cryptocurrencywalletapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoinExchangeDTO(
    @SerializedName("asset_id_base")
    val assetIdBase: String,
    val rates: List<Rate>
)
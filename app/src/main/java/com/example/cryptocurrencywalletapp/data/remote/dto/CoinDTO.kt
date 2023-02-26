package com.example.cryptocurrencywalletapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoinDTO(
    @SerializedName("asset_id")
    val assetId: String,
    @SerializedName("data_end")
    val dataEnd: String,
    @SerializedName("data_orderbook_end")
    val dataOrderbookEnd: String,
    @SerializedName("data_orderbook_start")
    val dataOrderbookStart: String,
    @SerializedName("data_quote_end")
    val dataQuoteEnd: String,
    @SerializedName("data_quote_start")
    val dataQuoteStart: String,
    @SerializedName("data_start")
    val dataStart: String,
    @SerializedName("data_symbols_count")
    val dataSymbolsCount: Int,
    @SerializedName("data_trade_end")
    val dataTradeEnd: String,
    @SerializedName("data_trade_state")
    val dataTradeStart: String,
    val name: String,
    @SerializedName("price_usd")
    val price: Double,
    @SerializedName("type_is_crypto")
    val isCrypto: Int,
    @SerializedName("volume_1day_usd")
    val volumeOneDayUsd: Double,
    @SerializedName("volume_1hrs_usd")
    val volumeOneHrsUsd: Double,
    @SerializedName("volume_1mth_usd")
    val volumeOneMthUsd: Double
)
package com.example.cryptocurrencywalletapp.data.mapper

import com.example.cryptocurrencywalletapp.data.local.CoinEntity
import com.example.cryptocurrencywalletapp.data.remote.dto.CoinDTO
import com.example.cryptocurrencywalletapp.data.remote.dto.CoinExchangeDTO
import com.example.cryptocurrencywalletapp.domain.model.Coin
import com.example.cryptocurrencywalletapp.domain.model.CoinExchange
import kotlin.math.roundToInt

fun CoinEntity.toCoin(): Coin {
    return Coin(
        id = symbol,
        isCrypto = 1,
        name = name,
        price =(((priceUSD?.times(100.0))?.roundToInt() ?: 0) / 100.0)
    )
}

fun Coin.toCoinEntity(): CoinEntity{
    return CoinEntity(
        symbol = id,
        name = name,
        priceUSD = price,
         )
}
fun CoinDTO.toCoinEntity(): CoinEntity{
    return CoinEntity(
        symbol = assetId,
        name = name,
        priceUSD = price
    )
}

fun CoinExchangeDTO.toCoinExchange(): CoinExchange {
    return CoinExchange(
        id = assetIdBase,
        rates = rates
    )
}
fun CoinDTO.toCoin(): Coin {
    return Coin(
        id = assetId,
        isCrypto = isCrypto,
        name = name,
        price = (price * 100.0).roundToInt() / 100.0,)
}
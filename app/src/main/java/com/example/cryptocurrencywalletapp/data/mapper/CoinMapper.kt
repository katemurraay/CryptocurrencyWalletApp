package com.example.cryptocurrencywalletapp.data.mapper

import com.example.cryptocurrencywalletapp.data.local.coin.CoinEntity
import com.example.cryptocurrencywalletapp.data.local.coin.CoinExchangeRelationship
import com.example.cryptocurrencywalletapp.data.local.coin.RateEntity
import com.example.cryptocurrencywalletapp.data.remote.dto.CoinDTO
import com.example.cryptocurrencywalletapp.data.remote.dto.CoinExchangeDTO
import com.example.cryptocurrencywalletapp.domain.model.Coin
import com.example.cryptocurrencywalletapp.domain.model.CoinExchange
import com.example.cryptocurrencywalletapp.domain.model.Rate
import kotlin.math.roundToInt

fun CoinEntity.toCoin(): Coin {
    return Coin(
        symbol = symbol,
        isCrypto = 1,
        name = name,
        price =(((priceUSD?.times(100.0))?.roundToInt() ?: 0) / 100.0)
    )
}

fun Coin.toCoinEntity(): CoinEntity {
    return CoinEntity(
        symbol = symbol,
        name = name,
        priceUSD = price,
         )
}
fun CoinDTO.toCoinEntity(): CoinEntity {
    return CoinEntity(
        symbol = assetId,
        name = name,
        priceUSD = price
    )
}




fun CoinExchangeRelationship.toCoinExchange(): CoinExchange{
    return CoinExchange(
        id = this.coin.symbol,
        coin =  this.coin.toCoin(),
        rates = this.rates.map { it.toRate()}
    )
}

fun RateEntity.toRate(): Rate{
    return Rate(
        symbol = this.name,
        rate = this.rate,
        time = this.time
    )
}


fun CoinExchangeDTO.toRateEntity(coinID: Long): RateEntity{
    return RateEntity(
        time = time,
        coinExchangeId = coinID,
        rate = rate,
        name = assetIdQuote
    )

}

fun CoinDTO.toCoin(): Coin {
    return Coin(
        symbol = assetId,
        isCrypto = isCrypto,
        name = name,
        price = (price * 100.0).roundToInt() / 100.0,)
}
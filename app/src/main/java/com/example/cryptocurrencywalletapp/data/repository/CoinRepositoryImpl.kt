package com.example.cryptocurrencywalletapp.data.repository

import com.example.cryptocurrencywalletapp.data.remote.CoinAPI
import com.example.cryptocurrencywalletapp.data.remote.dto.CoinDTO
import com.example.cryptocurrencywalletapp.data.remote.dto.CoinExchangeDTO
import com.example.cryptocurrencywalletapp.domain.model.Coin
import com.example.cryptocurrencywalletapp.domain.model.CoinExchange
import com.example.cryptocurrencywalletapp.domain.repository.CoinRepository
import com.example.cryptocurrencywalletapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.math.roundToInt

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinAPI
) : CoinRepository {

    fun CoinDTO.toCoin(): Coin {
        return Coin(
            id = assetId,
            isCrypto = isCrypto,
            name = name,
            price = (price * 10000.0).roundToInt() / 10000.0,)
    }
    fun CoinExchangeDTO.toCoinExchange(): CoinExchange {
        return CoinExchange(
            id = assetIdBase,
            rates = rates
        )
    }
    override suspend fun getCoins(): MutableList<CoinDTO> {
        return api.getCoins()
    }


    override suspend fun getCoinExchange(coinId: String): CoinExchangeDTO {
        return api.getCoinExchangeInfo(coinId)
    }

    fun getAllCoins(): Flow<Resource<List<Coin>>> = flow{
        try {
            emit(Resource.Loading())
            val coins =  api.getCoins().map {it.toCoin()}
            val mutableCoins = coins.toMutableList()
            mutableCoins.removeAll{ it.isCrypto!=1 }
            val data = mutableCoins.toList()
            emit(Resource.Success(data))
        } catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException){
            emit(Resource.Error( "No service. Check your internet connection"))
        }
    }


    fun getCoinExchangeInfo(coinId: String): Flow<Resource<CoinExchange>> = flow{
        try {
            emit(Resource.Loading())
            val coinInfo =  api.getCoinExchangeInfo(coinId)
            val data = coinInfo.toCoinExchange()
            emit(Resource.Success(data))
        } catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException){
            emit(Resource.Error( "No service. Check your internet connection"))
        }
    }


}
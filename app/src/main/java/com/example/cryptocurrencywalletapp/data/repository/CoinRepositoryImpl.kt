package com.example.cryptocurrencywalletapp.data.repository

import com.example.cryptocurrencywalletapp.data.remote.CoinAPI
import com.example.cryptocurrencywalletapp.data.remote.dto.CoinDTO
import com.example.cryptocurrencywalletapp.domain.model.Coin
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
            isActive = isCrypto ==1,
            name = name,
            price = (price * 100.0).roundToInt() / 100.0,


        )
    }
    override suspend fun getCoins(): List<CoinDTO> {
        return api.getCoins()
    }

    operator fun invoke(): Flow<Resource<List<Coin>>> = flow{
        try {
            emit(Resource.Loading())
            val coins = api.getCoins().map { it.toCoin() }
            emit(Resource.Success(coins))
        } catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException){
            emit(Resource.Error( "No service. Check your internet connection"))
        }
    }
}
package com.example.cryptocurrencywalletapp.data.repository

import android.util.Log
import androidx.room.withTransaction
import com.example.cryptocurrencywalletapp.data.local.CryptoDatabase
import com.example.cryptocurrencywalletapp.data.mapper.toCoin
import com.example.cryptocurrencywalletapp.data.mapper.toCoinEntity
import com.example.cryptocurrencywalletapp.data.mapper.toCoinExchange
import com.example.cryptocurrencywalletapp.data.remote.CoinAPI
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

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinAPI,
    private val db: CryptoDatabase
) : CoinRepository {

    private val dao = db.getCoinDao()


    override  fun getAllCoins(): Flow<Resource<List<Coin>>>{
        return flow{

                emit(Resource.Loading())
                val coinListing = dao.getCoins()

                emit(Resource.Success(
                    data = coinListing.map { it.toCoin() }
                ))


               try{
                    val response = api.getCoins().toList()
                    val filteredResponse = response.filter { it.isCrypto ==1 }
                    db.withTransaction {
                        dao.clearCoinListings()
                        val coinEntities = filteredResponse.map{it.toCoinEntity()}

                        dao.insertCoinListings(coinEntities)
                    }
                   val coins = dao.getCoins().map{it.toCoin()}
                   Log.d("DB_COINS", coins.toString())
                   emit(Resource.Success(
                      data=coins
                   ))


                }catch (e: IOException){
                    e.printStackTrace()
                    emit(Resource.Error( "No service. Check your internet connection"))
                    null
                }catch (e: HttpException){
                    e.printStackTrace()
                    emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
                    null

                 }




                }

        }


    override suspend fun getCoinExchange(coinId: String): CoinExchangeDTO {
        return api.getCoinExchangeInfo(coinId)
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
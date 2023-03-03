package com.example.cryptocurrencywalletapp.data.repository

import android.util.Log
import androidx.room.withTransaction
import com.example.cryptocurrencywalletapp.data.local.CryptoDatabase
import com.example.cryptocurrencywalletapp.data.mapper.toCoin
import com.example.cryptocurrencywalletapp.data.mapper.toCoinEntity
import com.example.cryptocurrencywalletapp.data.mapper.toCoinExchange
import com.example.cryptocurrencywalletapp.data.mapper.toRateEntity
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
    private val rateDAO = db.getRateDao()


    override  fun getAllCoins(): Flow<Resource<List<Coin>>>{
        return flow{

                emit(Resource.Loading())
                val coinListing = dao.getCoins()
                if(coinListing.isNotEmpty()){

                emit(Resource.Success(
                    data = coinListing.map { it.toCoin() }
                ))
                } else {


                    try {
                        val response = api.getCoins().toList()
                        val filteredResponse = response.filter { it.isCrypto == 1 }
                        db.withTransaction {
                            dao.clearCoinListings()
                            val coinEntities = filteredResponse.map { it.toCoinEntity() }

                            dao.insertCoinListings(coinEntities)
                        }
                        val coins = dao.getCoins().map { it.toCoin() }
                        emit(
                            Resource.Success(
                                data = coins
                            )
                        )


                    } catch (e: IOException) {
                        e.printStackTrace()
                        emit(Resource.Error("No service. Check your internet connection"))

                    } catch (e: HttpException) {
                        e.printStackTrace()
                        emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))


                    }


                }

                }

        }








    override fun getCoinExchange(coin: Coin): Flow<Resource<CoinExchange>> = flow{
        emit(Resource.Loading())
        val getCoin = dao.getCoinsBySymbol(coin.symbol)
        val coinListing = rateDAO.getCoinRates(getCoin.coinId!!)
        if (coinListing != null){
            emit(Resource.Success(data = coinListing.toCoinExchange()))
        }
        else{
            try {
                emit(Resource.Loading())
                db.withTransaction {
                    rateDAO.clearCoinRates(getCoin.coinId)
                    val coinUSD = api.getCoinExchangeInfo(coin.symbol, "USD")
                    val coinGBP= api.getCoinExchangeInfo(coin.symbol, "GBP")
                    val coinCNY= api.getCoinExchangeInfo(coin.symbol, "CNY")
                    val coinEUR= api.getCoinExchangeInfo(coin.symbol, "EUR")
                    val coinInfo = listOf(coinUSD, coinGBP, coinCNY, coinEUR)
                    Log.d("COIN INFO", coinInfo.toString())
                    val ratesListing = coinInfo.map { it.toRateEntity(getCoin.coinId) }
                    rateDAO.insertRates(ratesListing)

                }
                val coinExchangeInfo = rateDAO.getCoinRates(getCoin.coinId)
                if (coinExchangeInfo !=null) {
                    emit(Resource.Success(data = coinExchangeInfo.toCoinExchange()))
                } else{
                    emit(Resource.Error("No Rates Found"))

                }
            } catch (e: HttpException){
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException){
                emit(Resource.Error( "No service. Check your internet connection"))
            }
        }
    }


}
package com.example.cryptocurrencywalletapp.presentation.coinDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.cryptocurrencywalletapp.domain.model.Coin
import com.example.cryptocurrencywalletapp.domain.repository.CoinRepository
import com.example.cryptocurrencywalletapp.domain.repository.WalletRepository
import com.example.cryptocurrencywalletapp.presentation.BaseViewModel
import com.example.cryptocurrencywalletapp.presentation.walletList.WalletListState
import com.example.cryptocurrencywalletapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject
@HiltViewModel
class CoinDetailViewModel  @Inject constructor(
        private val coinRepository: CoinRepository, private val walletRepository: WalletRepository) : BaseViewModel<CoinDetailState>()  {
        lateinit var currentCoinData: LiveData<CoinDetailState>
        lateinit var currentUserWallets: LiveData<WalletListState>



        fun getCoinDetails(coin: Coin){
            currentCoinData = coinRepository.getCoinExchange(coin).map{ result ->
                when (result) {
                    is Resource.Success -> {
                        CoinDetailState.Success(result.data!!)
                    }
                    is Resource.Error -> {
                        CoinDetailState.Error(
                            error = result.message ?: "An unexpected error occured"
                        )
                    }
                    is Resource.Loading -> {
                        CoinDetailState.Loading
                    }
                }
            }.asLiveData()
        }

    fun getUserWallets(){
        currentUserWallets = walletRepository.getAllWallets().map{result ->
            when (result) {
                is Resource.Success -> {
                    WalletListState.Success(result.data ?: emptyList())
                }
                is Resource.Error -> {
                    WalletListState.Error(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    WalletListState.Loading
                }
            }
        }.asLiveData()
    }


}
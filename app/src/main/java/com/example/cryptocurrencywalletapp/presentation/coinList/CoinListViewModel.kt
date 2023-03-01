package com.example.cryptocurrencywalletapp.presentation.coinList

import androidx.lifecycle.*
import com.example.cryptocurrencywalletapp.domain.repository.CoinRepository
import com.example.cryptocurrencywalletapp.presentation.BaseViewModel
import com.example.cryptocurrencywalletapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*

import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
 ) : BaseViewModel<CoinListState>()  {
    lateinit var currentCoinData: LiveData<CoinListState>

    init {
        getCoins()
    }

    private fun getCoins(){
        currentCoinData = coinRepository.getAllCoins().map{result ->
            when (result) {
                is Resource.Success -> {
                   CoinListState.Success(result.data ?: emptyList())
                }
                is Resource.Error -> {
                   CoinListState.Error(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                   CoinListState.Loading
                }
            }
        }.asLiveData()
    }

    }










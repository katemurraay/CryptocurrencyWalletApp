package com.example.cryptocurrencywalletapp.presentation.coinList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.cryptocurrencywalletapp.data.repository.CoinRepositoryImpl
import com.example.cryptocurrencywalletapp.domain.repository.CoinRepository
import com.example.cryptocurrencywalletapp.presentation.BaseViewModel
import com.example.cryptocurrencywalletapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
 ) : BaseViewModel<CoinListState>()  {
    var currentStockPriceAsLiveData: LiveData<CoinListState> = coinRepository.getAllCoins().map { result ->
        CoinListState.Success(result.data ?: emptyList()) as CoinListState
    }.onStart {
        emit(CoinListState.Loading)
    }.asLiveData()


    suspend fun onEvent(event: CoinListEvent) {
        when(event) {
            is CoinListEvent.Refresh -> {
                //gettheCoinListings(fetchFromRemote = true)
            }
            is CoinListEvent.OnSearchQueryChange -> {

            }
        }
    }
    private fun gettheCoinListings(fetchFromRemote: Boolean =false){
        viewModelScope.launch { currentStockPriceAsLiveData =   coinRepository.getAllCoins().map { result ->
            CoinListState.Success(result.data ?: emptyList()) as CoinListState
        }.onStart {
            emit(CoinListState.Loading)
        }.asLiveData()
    }
    }



    }






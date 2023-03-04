package com.example.cryptocurrencywalletapp.presentation.walletList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.cryptocurrencywalletapp.domain.model.Wallet
import com.example.cryptocurrencywalletapp.domain.repository.WalletRepository
import com.example.cryptocurrencywalletapp.presentation.BaseViewModel
import com.example.cryptocurrencywalletapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject
@HiltViewModel
class WalletListViewModel @Inject constructor(
    private val walletRepository: WalletRepository,
) : BaseViewModel<WalletListState>()  {

    lateinit var currentWalletData: LiveData<WalletListState>
    init {
        getWallets()
    }
    fun getWallets(){
        currentWalletData = walletRepository.getAllWallets().map{result ->
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

    fun removeWallet(wallet: Wallet){
        Log.d("WALLET_ID", wallet.id.toString())
        currentWalletData = walletRepository.deleteWallet(wallet).map { result ->
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
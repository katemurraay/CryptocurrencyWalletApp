package com.example.cryptocurrencywalletapp.presentation.coinDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.cryptocurrencywalletapp.domain.model.Wallet
import com.example.cryptocurrencywalletapp.domain.repository.WalletRepository
import com.example.cryptocurrencywalletapp.presentation.BaseViewModel
import com.example.cryptocurrencywalletapp.presentation.walletList.WalletDialogState
import com.example.cryptocurrencywalletapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AddCoinToWalletViewModel @Inject constructor(
    private val walletRepository: WalletRepository
): BaseViewModel<AddCoinToWalletState>(){

    lateinit var insertCoinToWalletData: LiveData<AddCoinToWalletState>
    fun onAddCoinToWallet(coinSymbol: String, walletId: Long){
        insertCoinToWalletData = walletRepository.addCoinToWallet(coinSymbol, walletId).map {
                result ->
            when (result) {
                is Resource.Success -> {
                    AddCoinToWalletState.Success(result.data)
                }
                is Resource.Error -> {
                    AddCoinToWalletState.Error(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    AddCoinToWalletState.Loading
                }
            }
        }.asLiveData()
    }
}
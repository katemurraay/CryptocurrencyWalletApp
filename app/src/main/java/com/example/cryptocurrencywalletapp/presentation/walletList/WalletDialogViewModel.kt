package com.example.cryptocurrencywalletapp.presentation.walletList

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.cryptocurrencywalletapp.domain.model.Wallet
import com.example.cryptocurrencywalletapp.domain.repository.WalletRepository
import com.example.cryptocurrencywalletapp.presentation.BaseViewModel
import com.example.cryptocurrencywalletapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class WalletDialogViewModel @Inject constructor(
private val walletRepository: WalletRepository
) : BaseViewModel<WalletDialogState>()  {

    lateinit var updatedWalletData: LiveData<WalletDialogState>
    lateinit var insertWalletData: LiveData<WalletDialogState>

    fun onUpdateWallet(walletId: Long, walletTitle: String,  userID: Long) {
        updatedWalletData = walletRepository.updateWallet(walletId, walletTitle, userID).map{result ->
            when (result) {
                is Resource.Success -> {
                    WalletDialogState.Success(result.data)
                }
                is Resource.Error -> {
                    WalletDialogState.Error(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    WalletDialogState.Loading
                }
            }
        }.asLiveData()
    }

    fun onInsertWallet(wallet: Wallet){
        insertWalletData = walletRepository.insertWallet(wallet).map {
            result ->
                when (result) {
                    is Resource.Success -> {
                        WalletDialogState.Success(result.data)
                    }
                    is Resource.Error -> {
                        WalletDialogState.Error(
                            error = result.message ?: "An unexpected error occured"
                        )
                    }
                    is Resource.Loading -> {
                        WalletDialogState.Loading
                    }
                }
            }.asLiveData()
        }
    }

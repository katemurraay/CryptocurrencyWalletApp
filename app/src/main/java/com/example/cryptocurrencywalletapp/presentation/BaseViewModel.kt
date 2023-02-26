package com.example.cryptocurrencywalletapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<T> : ViewModel() {

    fun uiState(): LiveData<T> = uiState
    private val uiState: MutableLiveData<T> = MutableLiveData()
}
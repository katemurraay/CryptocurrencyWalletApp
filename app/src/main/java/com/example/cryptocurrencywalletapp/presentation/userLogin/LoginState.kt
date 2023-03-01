package com.example.cryptocurrencywalletapp.presentation.userLogin



sealed class LoginState{
    object Loading : LoginState()
    data class Success(val userId: Long?) : LoginState()
    data class Error(val error: String) : LoginState()
}

package com.example.cryptocurrencywalletapp.presentation.userRegister

import com.example.cryptocurrencywalletapp.domain.model.User

sealed class RegistrationState{
    object Loading : RegistrationState()
    data class Success(val user: User?) : RegistrationState()
    data class Error(val error: String) : RegistrationState()
}
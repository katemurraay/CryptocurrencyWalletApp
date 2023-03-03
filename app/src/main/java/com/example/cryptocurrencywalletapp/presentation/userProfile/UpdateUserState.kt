package com.example.cryptocurrencywalletapp.presentation.userProfile

import com.example.cryptocurrencywalletapp.domain.model.User

sealed class UpdateUserState {

        object Loading : UpdateUserState()
        data class Success(val user: User?) : UpdateUserState()
        data class Error(val error: String) : UpdateUserState()

}
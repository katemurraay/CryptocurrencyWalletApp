package com.example.cryptocurrencywalletapp.presentation.userLogin

import androidx.lifecycle.*
import com.example.cryptocurrencywalletapp.CryptoApplication
import com.example.cryptocurrencywalletapp.domain.repository.UserRepository
import com.example.cryptocurrencywalletapp.presentation.BaseViewModel
import com.example.cryptocurrencywalletapp.presentation.userLogin.LoginState
import com.example.cryptocurrencywalletapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*

import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel<LoginState>()  {



    lateinit var loginData: LiveData<LoginState>


    fun onValidateLogin(username: String, password:String){
        loginData =   userRepository.loginUser(username, password).map{result ->
            when (result) {
                is Resource.Success -> {
                    CryptoApplication.user = result.data
                    LoginState.Success(result.data?.userId)
                }
                is Resource.Error -> {
                    LoginState.Error(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    LoginState.Loading
                }
            }
        }.asLiveData()
    }






}
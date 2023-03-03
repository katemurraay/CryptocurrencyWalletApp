package com.example.cryptocurrencywalletapp.presentation.userRegister

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.cryptocurrencywalletapp.CryptoApplication
import com.example.cryptocurrencywalletapp.domain.model.User
import com.example.cryptocurrencywalletapp.domain.repository.UserRepository
import com.example.cryptocurrencywalletapp.presentation.BaseViewModel
import com.example.cryptocurrencywalletapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
@HiltViewModel
class RegisterViewModel@Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel<RegistrationState>()  {
    lateinit var registerData: LiveData<RegistrationState>

    fun onValidateRegistration(user:User) {
        registerData = userRepository.registerUser(user).map{result ->
            when (result) {
                is Resource.Success -> {
                    CryptoApplication.user = result.data
                    RegistrationState.Success(result.data)
                }
                is Resource.Error -> {
                    RegistrationState.Error(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    RegistrationState.Loading
                }
            }
        }.asLiveData()
    }




}




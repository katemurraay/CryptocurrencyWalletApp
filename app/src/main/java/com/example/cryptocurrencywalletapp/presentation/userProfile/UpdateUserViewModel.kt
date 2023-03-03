package com.example.cryptocurrencywalletapp.presentation.userProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.cryptocurrencywalletapp.CryptoApplication
import com.example.cryptocurrencywalletapp.domain.model.User
import com.example.cryptocurrencywalletapp.domain.repository.UserRepository
import com.example.cryptocurrencywalletapp.presentation.BaseViewModel
import com.example.cryptocurrencywalletapp.presentation.userRegister.RegistrationState
import com.example.cryptocurrencywalletapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject
@HiltViewModel
class UpdateUserViewModel@Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel<UpdateUserState>()  {

    lateinit var updatedUserData: LiveData<UpdateUserState>

        fun onUpdateUser(user: User) {
            updatedUserData = userRepository.updateUserDetails(user).map{result ->
                when (result) {
                    is Resource.Success -> {
                        CryptoApplication.user = result.data
                        UpdateUserState.Success(result.data)
                    }
                    is Resource.Error -> {
                        UpdateUserState.Error(
                            error = result.message ?: "An unexpected error occured"
                        )
                    }
                    is Resource.Loading -> {
                        UpdateUserState.Loading
                    }
                }
            }.asLiveData()
        }

    }
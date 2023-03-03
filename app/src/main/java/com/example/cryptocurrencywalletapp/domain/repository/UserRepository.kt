package com.example.cryptocurrencywalletapp.domain.repository

import com.example.cryptocurrencywalletapp.domain.model.User
import com.example.cryptocurrencywalletapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserByUsername(username: String): Flow<Resource<User>>


     fun updateUserDetails(user: User):  Flow<Resource<User>>
    fun registerUser(user: User): Flow<Resource<User>>

    fun loginUser(username: String, password: String): Flow<Resource<User>>
}
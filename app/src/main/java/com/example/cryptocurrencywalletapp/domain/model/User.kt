package com.example.cryptocurrencywalletapp.domain.model

import java.util.Date

data class User(
    val userId: Long?,
    val username: String,
    val email: String,
    val firstName: String,
    val surname: String,
    val password: String,
    val image: String,
    val joined: String?)

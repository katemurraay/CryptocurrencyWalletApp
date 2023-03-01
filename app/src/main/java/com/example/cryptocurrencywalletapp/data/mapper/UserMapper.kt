package com.example.cryptocurrencywalletapp.data.mapper

import com.example.cryptocurrencywalletapp.data.local.user.UserEntity
import com.example.cryptocurrencywalletapp.domain.model.User
import java.util.*

fun UserEntity.toUser(): User {
    return User(
        userId = userId,
        username = username,
        email = email,
        password = password,
        firstName = this.name.split(" ")[0],
        surname = this.name.split(" ")[1],
        joined = joinedDate.toString(),
        image = image_url
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        userId = userId,
        username = username,
        email = email,
        password = password,
        name = "$firstName $surname",
        joinedDate = joined?.toLong()!!,
        image_url = image
    )
}
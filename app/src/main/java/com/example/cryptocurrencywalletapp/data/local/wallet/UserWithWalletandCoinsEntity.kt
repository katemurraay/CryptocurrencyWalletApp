package com.example.cryptocurrencywalletapp.data.local.wallet

import androidx.room.Embedded
import androidx.room.Relation
import com.example.cryptocurrencywalletapp.data.local.user.UserEntity
import com.example.cryptocurrencywalletapp.domain.model.User

data class UserWithWalletandCoinsEntity(
    @Embedded val user: UserEntity,
    @Relation(
            entity = WalletEntity::class,
            parentColumn = "userId",
            entityColumn = "user_creator_id"
)
val wallets: List<WalletWithCoinsEntity>

)

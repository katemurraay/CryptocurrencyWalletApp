package com.example.cryptocurrencywalletapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptocurrencywalletapp.data.local.coin.CoinDAO
import com.example.cryptocurrencywalletapp.data.local.coin.CoinEntity
import com.example.cryptocurrencywalletapp.data.local.user.UserDAO
import com.example.cryptocurrencywalletapp.data.local.user.UserEntity
import com.example.cryptocurrencywalletapp.data.local.wallet.WalletCoinCrossRefEntity
import com.example.cryptocurrencywalletapp.data.local.wallet.WalletDAO
import com.example.cryptocurrencywalletapp.data.local.wallet.WalletEntity
import com.example.cryptocurrencywalletapp.data.local.wallet.WalletWithCoinsEntity


@Database(entities = [WalletEntity::class, CoinEntity::class, UserEntity::class, WalletCoinCrossRefEntity::class], version = 1)
abstract class CryptoDatabase: RoomDatabase() {

    abstract fun getWalletDao(): WalletDAO
    abstract fun getCoinDao(): CoinDAO
    abstract fun getUserDao(): UserDAO

}


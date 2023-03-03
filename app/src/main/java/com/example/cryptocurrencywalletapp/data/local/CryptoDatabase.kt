package com.example.cryptocurrencywalletapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptocurrencywalletapp.data.local.coin.CoinDAO
import com.example.cryptocurrencywalletapp.data.local.coin.CoinEntity
import com.example.cryptocurrencywalletapp.data.local.coin.RateDAO
import com.example.cryptocurrencywalletapp.data.local.coin.RateEntity
import com.example.cryptocurrencywalletapp.data.local.user.UserDAO
import com.example.cryptocurrencywalletapp.data.local.user.UserEntity
import com.example.cryptocurrencywalletapp.data.local.wallet.*


@Database(entities = [WalletEntity::class, CoinEntity::class, UserEntity::class, WalletCoinCrossRefEntity::class, RateEntity::class], version = 3)
abstract class CryptoDatabase: RoomDatabase() {

    abstract fun getWalletDao(): WalletDAO
    abstract fun getCoinDao(): CoinDAO
    abstract fun getUserDao(): UserDAO
    abstract fun getRateDao(): RateDAO

    abstract fun getWalletWithCoinsDao(): WalletCoinCrossRefDAO



}


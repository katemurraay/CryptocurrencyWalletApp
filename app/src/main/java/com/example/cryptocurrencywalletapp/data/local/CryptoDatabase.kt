package com.example.cryptocurrencywalletapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cryptocurrencywalletapp.utils.IConstants.DATABASE_NAME


@Database(entities = [WalletEntity::class, CoinEntity::class], version = 1)
abstract class CryptoDatabase: RoomDatabase() {

    abstract fun getWalletDao(): WalletDAO
    abstract fun getCoinDao(): CoinDAO

}


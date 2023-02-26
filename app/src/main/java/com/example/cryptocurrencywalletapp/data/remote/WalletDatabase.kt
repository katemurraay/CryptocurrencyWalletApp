package com.example.cryptocurrencywalletapp.data.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptocurrencywalletapp.data.remote.dto.WalletDTO
import com.example.cryptocurrencywalletapp.domain.model.Wallet
import com.example.cryptocurrencywalletapp.utils.IConstants.DATABASE_NAME


@Database(entities = arrayOf(WalletDTO::class), version = 1, exportSchema = false)
abstract class WalletDatabase: RoomDatabase() {

    abstract fun getWalletDao(): WalletDAO

    companion object{
        @Volatile
        private var INSTANCE: WalletDatabase?=null

        fun getDatabase(context: Context): WalletDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WalletDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}


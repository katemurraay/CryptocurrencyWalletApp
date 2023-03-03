package com.example.cryptocurrencywalletapp.di

import android.app.Application
import androidx.room.Room

import com.example.cryptocurrencywalletapp.data.local.CryptoDatabase
import com.example.cryptocurrencywalletapp.data.remote.CoinAPI
import com.example.cryptocurrencywalletapp.data.repository.CoinRepositoryImpl
import com.example.cryptocurrencywalletapp.data.repository.UserRepositoryImpl
import com.example.cryptocurrencywalletapp.domain.repository.CoinRepository
import com.example.cryptocurrencywalletapp.domain.repository.UserRepository
import com.example.cryptocurrencywalletapp.utils.Credientals.API_KEY_NAME
import com.example.cryptocurrencywalletapp.utils.Credientals.API_KEY_VALUE
import com.example.cryptocurrencywalletapp.utils.IConstants
import com.example.cryptocurrencywalletapp.utils.IConstants.DATABASE_NAME
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCoinApi(): CoinAPI {

        val interceptor = Interceptor { chain ->
            val newRequest: Request =
                chain.request().newBuilder().addHeader(API_KEY_NAME, API_KEY_VALUE).build()
            chain.proceed(newRequest)
        }


        // Add the interceptor to OkHttpClient
        val builder = OkHttpClient.Builder()
        builder.interceptors().add(interceptor)
        val client: OkHttpClient = builder.build()


        // Set & Return the custom client when building adapter
        return Retrofit.Builder()
            .baseUrl(IConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(CoinAPI::class.java)

    }


    @Provides
    @Singleton
    fun provideCryptoDatabase(app: Application): CryptoDatabase {
        return Room.databaseBuilder(
            app,
            CryptoDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
    @Provides
    @Singleton
    fun provideCoinInfoRepository(
        db: CryptoDatabase,
        api: CoinAPI
    ): CoinRepository {
        return CoinRepositoryImpl(api, db)
    }

    @Provides
    @Singleton
    fun provideUserInfoRepository(
        db: CryptoDatabase
    ): UserRepository {
        return UserRepositoryImpl(db)
    }


}
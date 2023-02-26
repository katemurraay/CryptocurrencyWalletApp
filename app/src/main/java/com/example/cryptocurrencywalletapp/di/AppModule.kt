package com.example.cryptocurrencywalletapp.di

import com.example.cryptocurrencywalletapp.data.remote.CoinAPI
import com.example.cryptocurrencywalletapp.data.repository.CoinRepositoryImpl
import com.example.cryptocurrencywalletapp.domain.repository.CoinRepository
import com.example.cryptocurrencywalletapp.utils.IConstants
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
    fun providePaprikaApi(): CoinAPI {
        val interceptor = Interceptor { chain ->
            val newRequest: Request =
                chain.request().newBuilder().addHeader(IConstants.API_KEY_NAME, IConstants.API_KEY_VALUE).build()
            chain.proceed(newRequest)
        }

        // Add the interceptor to OkHttpClient

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
    fun provideCoinRepository(api: CoinAPI): CoinRepository {
        return CoinRepositoryImpl(api)
    }
}
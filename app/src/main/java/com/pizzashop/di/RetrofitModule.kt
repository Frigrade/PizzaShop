package com.pizzashop.di

import com.pizzashop.data.api.PizzaAPI
import com.pizzashop.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Singleton
    @Provides
    fun getNewsApi(retrofit: Retrofit): PizzaAPI {
        return retrofit.create(PizzaAPI::class.java)
    }

    @Singleton
    @Provides
    fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)


    @Singleton
    @Provides
    fun getOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

    @Singleton
    @Provides
    fun getRetrofitInstance(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
}
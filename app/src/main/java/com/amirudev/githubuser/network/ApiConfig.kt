package com.amirudev.githubuser.network

import com.amirudev.githubuser.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

object ApiConfig {
    fun getApiService(): ApiService {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val client = okhttp3.OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }
}
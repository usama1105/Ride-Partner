package com.example.network.di

import com.example.network.api.ApiService
import com.example.network.utils.NetworkBuilder.baseUrl
import com.example.network.utils.NetworkBuilder.isDebug
import com.example.network.utils.NetworkUtils
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(okHttpClient = get()) }
    single { provideFusionInspectApiService(retrofit = get()) }
}

private fun provideOkHttpClient(): OkHttpClient {
    val logger = HttpLoggingInterceptor()
    logger.level = if (isDebug())
        HttpLoggingInterceptor.Level.BODY
    else HttpLoggingInterceptor.Level.NONE

    // Todo: Add AccessToken
    return OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .callTimeout(60, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor {
            val requestBuilder = it.request().newBuilder().apply {
                addHeader("Authorization", "Bearer " + NetworkUtils.accessToken)
                addHeader("x-platform", "Android")
                addHeader("x-deviceId", NetworkUtils.deviceId)
            }
            it.proceed(requestBuilder.build())
        }
        .addInterceptor(logger)
        .build()
}


private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        ).build()
}


private fun provideFusionInspectApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}
package com.sahalnazar.themoviedb_jetpack_compose.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sahalnazar.themoviedb_jetpack_compose.BuildConfig
import com.sahalnazar.themoviedb_jetpack_compose.data.remote.AuthorizationInterceptor
import com.sahalnazar.themoviedb_jetpack_compose.data.remote.ComposeTheMovieDbApis
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@OptIn(ExperimentalSerializationApi::class)
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authorizationInterceptor: AuthorizationInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(authorizationInterceptor)
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logging)
            }
            connectTimeout(5, TimeUnit.MINUTES)
            readTimeout(5, TimeUnit.MINUTES)
            writeTimeout(5, TimeUnit.MINUTES)
            callTimeout(5, TimeUnit.MINUTES)
        }.build()
    }

    @Provides
    @Singleton
    fun provideJson() = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ComposeTheMovieDbApis {
        return retrofit.create(ComposeTheMovieDbApis::class.java)
    }

}
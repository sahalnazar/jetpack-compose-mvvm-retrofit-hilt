package com.sahalnazar.themoviedb_jetpack_compose.data.remote

import com.sahalnazar.themoviedb_jetpack_compose.util.ApiKey
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url: HttpUrl = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", ApiKey.theMovieDbKey) // Todo: Add your api key here
            .build()
        val request: Request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}
package com.sahalnazar.themoviedb_jetpack_compose.data.remote

import com.sahalnazar.themoviedb_jetpack_compose.util.ApiKey
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url: HttpUrl = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("name", ApiKey.theMovieDbKey) // Todo: Add your api key here
            .build()
        val request: Request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}
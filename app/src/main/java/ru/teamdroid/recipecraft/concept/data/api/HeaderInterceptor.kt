package ru.teamdroid.recipecraft.concept.data.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().addHeader(ACCEPT_HEADER, JSON_TYPE).build()
        return chain.proceed(request)
    }

    companion object {
        private const val ACCEPT_HEADER = "Accept"
        private const val JSON_TYPE = "application/json"
    }
}
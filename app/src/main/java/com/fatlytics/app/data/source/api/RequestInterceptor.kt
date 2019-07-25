package com.fatlytics.app.data.source.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val headers = originalRequest.headers.newBuilder()
            .add("sample_header_name", "sample_header_value")
            .build()

        val url = originalRequest.url.newBuilder()
            .addQueryParameter("sample_query_name", "sample_query_value")
            .build()

        val request = originalRequest.newBuilder()
            .headers(headers)
            .url(url)
            .build()

        return chain.proceed(request)
    }
}
package com.fatlytics.app.data.source.api

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.fatlytics.app.BuildConfig
import com.fatlytics.app.data.source.paper.AuthBook
import com.fatlytics.app.data.source.paper.AuthBookKeys
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.paperdb.Book
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideCache(application: Application): Cache =
        Cache(File(application.cacheDir, "retrofit-cache"), CACHE_SIZE_IN_BYTES)

    @Provides
    fun provideRequestInterceptor(@AuthBook authBook: Book) = Interceptor { chain ->
        var request = chain.request()

        val url = request.url().newBuilder()
            .addQueryParameter("format", "json")
            .build()

        request = request.newBuilder().url(url).build()

        val accessToken = authBook.read<String?>(AuthBookKeys.AUTH_TOKEN)?.let { it }
            ?: BuildConfig.FATSECRET_AUTH_TOKEN
        val accessSecret = authBook.read<String?>(AuthBookKeys.AUTH_SECRET)?.let { it }
            ?: BuildConfig.FATSECRET_AUTH_SECRET

        val oauth1 = Oauth1SigningInterceptor.Builder()
            .consumerKey(BuildConfig.FATSECRET_CONSUMER_KEY)
            .consumerSecret(BuildConfig.FATSECRET_CONSUMER_SECRET)
            .accessToken(accessToken)
            .accessSecret(accessSecret)
            .build()

        chain.proceed(oauth1.signRequest(request))
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    fun provideOkHttpClient(
        cache: Cache, requestInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(requestInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .addNetworkInterceptor(StethoInterceptor())
        .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(Wrapped.ADAPTER_FACTORY)
        .add(DateAdapter())
        .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.FATSECRET_API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    fun provideApi(retrofit: Retrofit): FatlyticsApi = retrofit.create(
        FatlyticsApi::class.java
    )

    companion object ApiPaths {
        private const val TIMEOUT_IN_SECONDS: Long = 30
        private const val CACHE_SIZE_IN_BYTES: Long = 10 * 1024 * 1024
    }
}
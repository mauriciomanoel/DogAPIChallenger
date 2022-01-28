package com.mauricio.dogbreedapi.di.module

import android.app.Application
import android.content.Context
import com.mauricio.dogbreedapi.BuildConfig
import com.mauricio.dogbreedapi.network.HttpHeadersInterceptor
import com.mauricio.dogbreedapi.network.RetrofitApiService
import com.mauricio.dogbreedapi.utils.network.ConnectionNetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val BASE_URL = "https://api.thedogapi.com/"

    @Singleton
    @Provides
    fun provideHeadersInterceptor(): HttpHeadersInterceptor = HttpHeadersInterceptor()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        headersInterceptor: HttpHeadersInterceptor,
        cache: Cache,
        connectionNetwork: ConnectionNetworkUtils): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(headersInterceptor)
        .cache(cache)
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (connectionNetwork.isOnline())
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            else
                request.newBuilder().header("Cache-Control", "public, only-if-cached").build()
            chain.proceed(request)
        }
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): RetrofitApiService = retrofit.create(RetrofitApiService::class.java)

    @Singleton
    @Provides
    fun provideCacheFile(context: Application): Cache {
        val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB
        return Cache(context.cacheDir, cacheSize)
    }

    @Singleton
    @Provides
    fun provideConnectionNetworkUtils(@ApplicationContext appContext: Context): ConnectionNetworkUtils {
        return ConnectionNetworkUtils(appContext)
    }
}


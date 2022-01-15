package com.mauricio.dogapichallenger.di.module

import android.app.Application
import com.mauricio.dogapichallenger.BuildConfig
import com.mauricio.dogapichallenger.network.HttpHeadersInterceptor
import com.mauricio.dogapichallenger.network.RetrofitApiService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class NetworkModule {

    @Module
    companion object {

        const val BASE_URL = "https://api.thedogapi.com/"

        @JvmStatic
        @Singleton
        @Provides
        fun provideHeadersInterceptor(): HttpHeadersInterceptor = HttpHeadersInterceptor()

        @JvmStatic
        @Singleton
        @Provides
        fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideOkHttp(
            loggingInterceptor: HttpLoggingInterceptor,
            headersInterceptor: HttpHeadersInterceptor,
            cache: Cache): OkHttpClient = OkHttpClient.Builder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .writeTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(5, TimeUnit.SECONDS)
                        .addInterceptor(loggingInterceptor)
                        .addInterceptor(headersInterceptor)
                        .cache(cache)
                        .addInterceptor { chain ->
                            var request = chain.request()
                            request = request.newBuilder()
                                .header("Cache-Control", "public, max-age=" + 5)
                                .build()
                            chain.proceed(request)
                        }
                        .build()

        @Singleton
        @JvmStatic
        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
                Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
//                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(okHttpClient)
                        .build()
        @JvmStatic
        @Singleton
        @Provides
        fun provideApiService(retrofit: Retrofit): RetrofitApiService = retrofit.create(RetrofitApiService::class.java)

        @JvmStatic
        @Singleton
        @Provides
        fun provideCacheFile(context: Application): Cache {
            val cacheSize = (5 * 1024 * 1024).toLong()
            return Cache(context.cacheDir, cacheSize)
        }
    }
}

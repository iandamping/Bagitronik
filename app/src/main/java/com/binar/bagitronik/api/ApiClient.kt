package com.binar.bagitronik.api

import com.binar.bagitronik.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
class ApiClient {
    companion object {
        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build()
        }

        private fun getOkHttpClient(): OkHttpClient {
            val timeOut = 60L
            return OkHttpClient.Builder()
                    .readTimeout(timeOut, TimeUnit.SECONDS)
                    .connectTimeout(timeOut, TimeUnit.SECONDS)
                    .writeTimeout(timeOut, TimeUnit.SECONDS)
                    .addInterceptor(getInterceptor())
                    .addInterceptor { chain ->
                        val ongoing = chain.request().newBuilder()
                        ongoing.addHeader("Content-Type", "application/json")
                        chain.proceed(ongoing.build())
                    }
                    .build()
        }


        fun getRetrofitWithMultiPart(header: String?): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClientWithMultiPart(header))
                    .build()
        }

        private fun getOkHttpClientWithMultiPart(header: String?): OkHttpClient {
            val timeOut = 60L
            return OkHttpClient.Builder()
                    .readTimeout(timeOut, TimeUnit.SECONDS)
                    .connectTimeout(timeOut, TimeUnit.SECONDS)
                    .writeTimeout(timeOut, TimeUnit.SECONDS)
                    .addInterceptor(getInterceptor())
                    .addInterceptor { chain ->
                        val ongoing = chain.request().newBuilder()
                        if (header != null) {
                            ongoing.addHeader("Content-Type", "application/json")
                            ongoing.addHeader("Authorization", header)
                        }
                        chain.proceed(ongoing.build())
                    }
                    .build()
        }

        private fun getInterceptor(): Interceptor {
            return HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }
        }

    }

}
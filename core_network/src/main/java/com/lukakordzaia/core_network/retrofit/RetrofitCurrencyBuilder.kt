package com.lukakordzaia.core_network.retrofit

import com.lukakordzaia.core.BuildConfig
import com.lukakordzaia.core_network.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitCurrencyBuilder {
    private var retrofitInstance: Retrofit? = null

    fun getRetrofitInstance(): Retrofit {

        val okHttpClient = OkHttpClient()
            .newBuilder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            okHttpClient
                .addInterceptor(loggingInterceptor)
        }

        if (retrofitInstance == null) {
            retrofitInstance = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitInstance!!
    }
}

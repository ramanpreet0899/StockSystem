package com.example.stockproject.provider

import okhttp3.*
import okhttp3.logging.*
import retrofit2.*
import retrofit2.converter.gson.*

const val BASE_URL = "https://71iztxw7wh.execute-api.us-east-1.amazonaws.com/"

class StockProvider{

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
package com.example.stockproject.provider

import retrofit2.*
import retrofit2.converter.gson.*

const val BASE_URL = "https://71iztxw7wh.execute-api.us-east-1.amazonaws.com/"

class StockProvider {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
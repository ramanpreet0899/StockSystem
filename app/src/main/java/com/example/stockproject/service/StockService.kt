package com.example.stockproject.service

import com.example.stockproject.model.*
import retrofit2.Call
import retrofit2.http.*

interface StockService  {
    @GET("interview/favorite-stocks")
    fun getStocks(): Call<Map<String,Stock>>
}
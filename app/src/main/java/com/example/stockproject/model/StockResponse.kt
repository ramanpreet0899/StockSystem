package com.example.stockproject.model

import java.io.Serializable


data class Stock(
    val name: String?,
    val price: Double,
    val low: Int,
    val high: Int
): Serializable
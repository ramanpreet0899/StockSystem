package com.example.stockproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.stockproject.model.*

class StockDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button_foreground)
        setContentView(R.layout.activity_stock_detail)

        val symbolTv : TextView = findViewById(R.id.detail_symbol_content)
        val nameTv :TextView = findViewById(R.id.detail_name_content)
        val currentPriceTv : TextView = findViewById(R.id.detail_current_price_content)
        val dailyLowTv : TextView = findViewById(R.id.detail_daily_low_content)
        val dailyHighTv :TextView = findViewById(R.id.detail_daily_high_content)

        val title = intent.getStringExtra("title")
        supportActionBar?.title = title
        symbolTv.text = title

        val stock = intent.getSerializableExtra("stock") as Stock
        nameTv.text = stock.name
        currentPriceTv.text = "$${stock.price}"
        dailyHighTv.text = "$${stock.high}"
        dailyLowTv.text = "$${stock.low}"
    }
}
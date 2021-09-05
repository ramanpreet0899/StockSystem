package com.example.stockproject.screens

import android.os.*
import android.widget.*
import androidx.appcompat.app.*
import com.example.stockproject.*
import com.example.stockproject.model.*
import com.example.stockproject.provider.*
import com.example.stockproject.service.*
import kotlinx.android.synthetic.main.activity_stock_detail.*
import retrofit2.*


class StockDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button_foreground)
        setContentView(R.layout.activity_stock_detail)

        val title = intent.getStringExtra("title")
        supportActionBar?.title = title
        detail_symbol_content.text = title

        val provider = StockProvider()
        val service = provider.retrofit.create(StockService::class.java)

        val call = service.getStocks()

        call.enqueue(object : Callback<Map<String, Stock>> {
            override fun onResponse(
                call: Call<Map<String, Stock>>?,
                response: Response<Map<String, Stock>>?
            ) {
                response!!.body()?.get(title)?.let { s ->
                   runOnUiThread {
                       detail_name_content.text = s.name
                       detail_current_price_content.text = "$${s.price}"
                       detail_daily_high_content.text = "$${s.high}"
                       detail_daily_low_content.text = "$${s.low}"
                   }
                }
            }

            override fun onFailure(call: Call<Map<String, Stock>>?, t: Throwable?) {
                Toast.makeText(applicationContext,t?.localizedMessage.toString(),Toast.LENGTH_SHORT).show()
            }

        })
    }
}
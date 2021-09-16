package com.example.stockproject.screens

import android.os.*
import android.util.*
import android.view.*
import android.widget.*
import androidx.appcompat.app.*
import com.example.stockproject.*
import com.example.stockproject.model.*
import com.example.stockproject.provider.*
import com.example.stockproject.service.*
import kotlinx.android.synthetic.main.activity_stock_detail.*
import retrofit2.*
import java.util.*


class StockDetail : AppCompatActivity() {
    private lateinit var stock: Stock

    val handler = Handler(Looper.getMainLooper())
    private val timer = Timer()
    
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

        val doAsynchronousTask: TimerTask = object : TimerTask() {
            override fun run() {
                handler.post {
                    try {
                        val call = service.getStocks()
                        call.enqueue(object : Callback<Map<String, Stock>> {
                            override fun onResponse(
                                call: Call<Map<String, Stock>>?,
                                response: Response<Map<String, Stock>>?
                            ) {
                                response!!.body()?.get(title)?.let { s ->
                                    stock = Stock(s.name, s.price, s.low, s.high)
                                    runOnUiThread {
                                        updateViews(stock)
                                    }
                                }
                            }

                            override fun onFailure(call: Call<Map<String, Stock>>?, t: Throwable?) {
                                Toast.makeText(
                                    applicationContext,
                                    t?.localizedMessage.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        })
                    } catch (e: Exception) {
                        Log.e("Exception", e.toString())
                    }
                }
            }
        }
        timer.schedule(doAsynchronousTask, 0, 3000)
    }

    private fun updateViews(s: Stock) {
        detail_name_content.text = s.name
        detail_current_price_content.text = "$${s.price}"
        detail_daily_high_content.text = "$${s.high}"
        detail_daily_low_content.text = "$${s.low}"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
package com.example.stockproject

import CustomAdapter
import android.content.*
import android.os.*
import android.util.*
import androidx.appcompat.app.*
import androidx.recyclerview.widget.*
import com.example.stockproject.model.*
import com.example.stockproject.service.*
import retrofit2.*
import retrofit2.converter.gson.*


class PopularStocks : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popular_stocks)

        val rView = findViewById<RecyclerView>(R.id.popular_stocks_content_view)

//        calling retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://71iztxw7wh.execute-api.us-east-1.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(StockService::class.java)

        val call = service.getStocks()

        val title = mutableListOf<String>()
        val content = mutableListOf<Stock>()

        call.enqueue(object : Callback<Map<String, Stock>> {
            override fun onResponse(
                call: Call<Map<String, Stock>>?,
                response: Response<Map<String, Stock>>?
            ) {
                response!!.body()?.let {
                    title.addAll(it.keys)
                    content.addAll(it.values)
                }

                val adapter = CustomAdapter(title,content).apply {
                    onStockNavigation = { stock, s ->
                        val intent = Intent(applicationContext,StockDetail::class.java)
                        intent.putExtra("stock", stock)
                        intent.putExtra("title",s)
                        startActivity(intent)
                    }
                }
                rView.adapter = adapter
            }

            override fun onFailure(call: Call<Map<String, Stock>>?, t: Throwable?) {
                Log.e("Failure", t.toString())
            }

        })

//        displaying data

    }
}
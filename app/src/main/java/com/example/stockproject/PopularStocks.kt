package com.example.stockproject

import CustomAdapter
import android.content.*
import android.os.*
import android.widget.*
import androidx.appcompat.app.*
import androidx.recyclerview.widget.*
import com.example.stockproject.model.*
import com.example.stockproject.provider.*
import com.example.stockproject.service.*
import retrofit2.*


class PopularStocks : AppCompatActivity() {
    private val title = mutableListOf<String>()
    private val content = mutableListOf<Stock>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.popular_stocks)
        setContentView(R.layout.popular_stocks)

        val rView = findViewById<RecyclerView>(R.id.popular_stocks_content_view)

        val provider = StockProvider()
        val service = provider.retrofit.create(StockService::class.java)

        val call = service.getStocks()

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
               Toast.makeText(applicationContext,t?.localizedMessage.toString(),Toast.LENGTH_SHORT).show()
            }

        })

    }
}
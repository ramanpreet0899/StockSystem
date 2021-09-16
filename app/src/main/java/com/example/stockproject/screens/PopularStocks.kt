package com.example.stockproject.screens

import android.content.*
import android.os.*
import android.util.*
import android.widget.*
import androidx.appcompat.app.*
import com.example.stockproject.*
import com.example.stockproject.adapter.*
import com.example.stockproject.model.*
import com.example.stockproject.provider.*
import com.example.stockproject.service.*
import kotlinx.android.synthetic.main.popular_stocks.*
import retrofit2.*
import java.util.*


class PopularStocks : AppCompatActivity() {
    private val title = mutableListOf<String>()
    private val content = mutableListOf<Stock>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.popular_stocks)
        setContentView(R.layout.popular_stocks)

        val provider = StockProvider()
        val service = provider.retrofit.create(StockService::class.java)

        val handler = Handler(Looper.getMainLooper())
        val timer = Timer()
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
                                title.clear()
                                content.clear()
                                response!!.body()?.let  {

                                    title.addAll(it.keys)
                                    content.addAll(it.values)
                                }

                                runOnUiThread {
                                    updateViews()
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
                        Log.e("Exception",e.toString())
                    }
                }
            }
        }
        timer.schedule(doAsynchronousTask, 0, 3000)
    }

    private fun updateViews() {
        val adapter = CustomAdapter(title, content).apply {
            onStockNavigation = { s ->
                val intent =
                    Intent(applicationContext, StockDetail::class.java)
                intent.putExtra("title", s)
                startActivity(intent)
            }
        }
        popular_stocks_content_view.adapter = adapter
    }
}
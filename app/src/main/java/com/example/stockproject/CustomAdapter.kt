import android.annotation.*
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.*
import com.example.stockproject.*
import com.example.stockproject.model.*

class CustomAdapter(private val title: MutableList<String>,private val content : MutableList<Stock>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.ps_title)
        val companyNameText :TextView= view.findViewById(R.id.ps_company_name)
        val stockValueText :TextView= view.findViewById(R.id.ps_company_stock)


        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.popular_stock_item_view, viewGroup, false)

        return ViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.titleText.text = title[position]
        viewHolder.companyNameText.text = content[position].name
        viewHolder.stockValueText.text = "$${content[position].price}"
    }


    override fun getItemCount() = title.size

}

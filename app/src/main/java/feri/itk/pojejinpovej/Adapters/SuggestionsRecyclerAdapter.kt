package feri.itk.pojejinpovej.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import feri.itk.pojejinpovej.R
import kotlinx.android.synthetic.main.suggestion_recycler_row.view.*

class SuggestionsRecyclerAdapter(list: List<String>): RecyclerView.Adapter<SuggestionsViewHolder>(){
    val restaurantList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionsViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.suggestion_recycler_row, parent, false)
        return SuggestionsViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: SuggestionsViewHolder, position: Int) {
        holder.bind(restaurantList[position])
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }
}
class SuggestionsViewHolder(val view: View): RecyclerView.ViewHolder(view){
    fun bind(restaurant: String){
        Picasso.get().load("https://images.pexels.com/photos/1383776/pexels-photo-1383776.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260").fit().centerCrop().into(view.suggestion_restaurant_picture)
    }
}
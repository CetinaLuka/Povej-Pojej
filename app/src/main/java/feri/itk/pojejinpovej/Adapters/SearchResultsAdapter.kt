package feri.itk.pojejinpovej.Adapters

import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import feri.itk.pojejinpovej.R
import kotlinx.android.synthetic.main.search_results_recycler_row.view.*
import kotlinx.android.synthetic.main.suggestion_recycler_row.view.*

class SearchResultsAdapter(list: List<String>, val clickListener: (String) -> Unit): RecyclerView.Adapter<SearchResultsViewHolder>(){
    val restaurantList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.search_results_recycler_row, parent, false)
        return SearchResultsViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        holder.bind(restaurantList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }
}
class SearchResultsViewHolder(val view: View): RecyclerView.ViewHolder(view){
    fun bind(restaurant: String, clickListener: (String) -> Unit){
        Picasso.get().load("https://images.pexels.com/photos/1383776/pexels-photo-1383776.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260").fit().centerCrop().into(view.search_restaurant_picture)
        view.setOnClickListener { clickListener(restaurant) }
    }
}
class SearchResultsItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceHeight
            }
            left =  spaceHeight
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}
package feri.itk.pojejinpovej.Adapters

import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import feri.itk.pojejinpovej.Dataclass.Restaurant
import feri.itk.pojejinpovej.R
import kotlinx.android.synthetic.main.search_results_recycler_row.*
import kotlinx.android.synthetic.main.search_results_recycler_row.view.*
import kotlinx.android.synthetic.main.suggestion_recycler_row.view.*

class SearchResultsAdapter(list: List<Restaurant>, val clickListener: (Restaurant, Int) -> Unit): RecyclerView.Adapter<SearchResultsViewHolder>(){
    val restaurantList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.search_results_recycler_row, parent, false)
        return SearchResultsViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        holder.bind(restaurantList[position], position, clickListener)
        holder.itemView.search_restaurant_name.transitionName = "restaurant_name$position"
        holder.itemView.search_restaurant_picture.transitionName = "restaurant_picture$position"
        holder.itemView.search_restaurant_address.transitionName = "restaurant_address$position"
        holder.itemView.search_restaurant_currency.transitionName = "restaurant_currency$position"
        holder.itemView.search_restaurant_description.transitionName = "restaurant_description$position"
        holder.itemView.search_restaurant_distance.transitionName = "restaurant_distance$position"
        holder.itemView.search_restaurant_num_of_reviews.transitionName = "restaurant_num_of_reviews$position"
        holder.itemView.search_restaurant_price.transitionName = "restaurant_price$position"
        holder.itemView.search_restaurant_rating.transitionName = "restaurant_rating$position"
        holder.itemView.search_restaurant_working_hours.transitionName = "restaurant_working_hours$position"
        holder.itemView.search_restaurant_star_icon.transitionName = "restaurant_star_icon$position"
        holder.itemView.search_restaurant_time_icon.transitionName = "restaurant_time_icon$position"

    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }
}
class SearchResultsViewHolder(val view: View): RecyclerView.ViewHolder(view){
    fun bind(restaurant: Restaurant, position: Int, clickListener: (Restaurant, Int) -> Unit){
        view.search_restaurant_name.text = restaurant.name
        Picasso.get().load("https://images.pexels.com/photos/1383776/pexels-photo-1383776.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260").fit().centerCrop().into(view.search_restaurant_picture)
        view.search_results_recycler_row_card_view.setOnClickListener {
            Log.i("click", it.search_restaurant_picture.transitionName)
            val extras = FragmentNavigatorExtras(
                it.search_restaurant_name to "restaurant_name",
                it.search_restaurant_name to "restaurant_name",
                it.search_restaurant_address to "restaurant_address",
                it.search_restaurant_currency to "restaurant_currency",
                it.search_restaurant_description to "restaurant_description",
                it.search_restaurant_distance to "restaurant_distance",
                it.search_restaurant_num_of_reviews to "restaurant_num_of_reviews",
                it.search_restaurant_price to "restaurant_price",
                it.search_restaurant_rating to "restaurant_rating",
                it.search_restaurant_working_hours to "restaurant_working_hours",
                it.search_restaurant_star_icon to "restaurant_star_icon",
                it.search_restaurant_time_icon to "restaurant_time_icon",
                it.search_restaurant_picture to "restaurant_picture")
            view?.findNavController()?.navigate(R.id.action_searchFragment_to_restaurantDetails,
                null, // Bundle of args
                null,
                extras)
        }
    }
}
//item decoration class that makes sure there is equal padding around all recyclerview items
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
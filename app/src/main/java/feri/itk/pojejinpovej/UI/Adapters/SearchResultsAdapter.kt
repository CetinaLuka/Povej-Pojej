package feri.itk.pojejinpovej.UI.Adapters

import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.R
import feri.itk.pojejinpovej.Util.PicassoImageLoader
import kotlinx.android.synthetic.main.search_results_recycler_row.view.*
import kotlinx.android.synthetic.main.suggestion_recycler_row.view.*

class SearchResultsAdapter(list: List<Restaurant>, val clickListener: (Restaurant) -> Unit): RecyclerView.Adapter<SearchResultsViewHolder>(){
    private val restaurantList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.search_results_recycler_row, parent, false)
        return SearchResultsViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        holder.bind(restaurantList[position], clickListener)
        holder.itemView.search_restaurant_name.transitionName = "restaurant_name$position"
        holder.itemView.search_restaurant_picture.transitionName = "restaurant_picture$position"
        holder.itemView.search_restaurant_address.transitionName = "restaurant_address$position"
        holder.itemView.search_restaurant_currency.transitionName = "restaurant_currency$position"
        holder.itemView.search_restaurant_description.transitionName = "restaurant_description$position"
        holder.itemView.search_restaurant_distance.transitionName = "restaurant_distance$position"
        holder.itemView.search_restaurant_price.transitionName = "restaurant_price$position"
        holder.itemView.search_restaurant_rating.transitionName = "restaurant_rating$position"
        holder.itemView.search_restaurant_working_hours.transitionName = "restaurant_working_hours$position"
        holder.itemView.search_restaurant_star_icon.transitionName = "restaurant_star_icon$position"
        holder.itemView.search_restaurant_time_icon.transitionName = "restaurant_time_icon$position"
        holder.itemView.search_results_recycler_row_card_view.transitionName = "restaurant_card$position"

    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }
}
class SearchResultsViewHolder(val view: View): RecyclerView.ViewHolder(view){
    fun bind(restaurant: Restaurant, clickListener: (Restaurant) -> Unit){
        view.search_restaurant_name.text = restaurant.name
        view.search_restaurant_price.text = restaurant.price.toString()
        view.search_restaurant_address.text = restaurant.address
        view.search_restaurant_description.text = restaurant.description
        view.search_restaurant_distance.text = restaurant.distance.toString()
        view.search_restaurant_rating.text = restaurant.rate.toString()
        PicassoImageLoader.loadImage(restaurant.picture, view.search_restaurant_picture)
        if(!restaurant.isOpenNow()){
            Picasso.get().load(R.drawable.closed_circle).into(view.search_open_indicator)
        }
        view.search_results_recycler_row_card_view.setOnClickListener {
            clickListener(restaurant)
            val extras = FragmentNavigatorExtras(
                it.search_results_recycler_row_card_view to "restaurant_card",
                it.search_restaurant_name to "restaurant_name",
                it.search_restaurant_name to "restaurant_name",
                it.search_restaurant_address to "restaurant_address",
                it.search_restaurant_currency to "restaurant_currency",
                it.search_restaurant_description to "restaurant_description",
                it.search_restaurant_distance to "restaurant_distance",
                it.search_restaurant_price to "restaurant_price",
                it.search_restaurant_rating to "restaurant_rating",
                it.search_restaurant_working_hours to "restaurant_working_hours",
                it.search_restaurant_star_icon to "restaurant_star_icon",
                it.search_restaurant_time_icon to "restaurant_time_icon",
                it.search_restaurant_picture to "restaurant_picture")
            if (view?.findNavController().currentDestination?.id == R.id.searchFragment) {
                view?.findNavController()?.navigate(R.id.action_searchFragment_to_restaurantDetails,
                    null, // Bundle of args
                    null,
                    extras)
            }
        }
    }
}
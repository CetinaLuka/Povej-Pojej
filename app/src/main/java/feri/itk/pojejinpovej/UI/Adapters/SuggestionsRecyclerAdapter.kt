package feri.itk.pojejinpovej.UI.Adapters

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
import kotlinx.android.synthetic.main.fragment_main_screen.*
import kotlinx.android.synthetic.main.suggestion_recycler_row.view.*

class SuggestionsRecyclerAdapter(list: List<Restaurant>, val clickListener: (Restaurant) -> Unit): RecyclerView.Adapter<SuggestionsViewHolder>(){
    val restaurantList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionsViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.suggestion_recycler_row, parent, false)
        return SuggestionsViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: SuggestionsViewHolder, position: Int) {
        holder.bind(restaurantList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }
}
class SuggestionsViewHolder(val view: View): RecyclerView.ViewHolder(view){
    fun bind(restaurant: Restaurant, clickListener: (Restaurant) -> Unit){
        view.suggestion_restaurant_name.text = restaurant.name
        view.suggestion_restaurant_address.text = restaurant.address
        view.suggestion_restaurant_price.text = restaurant.price.toString()
        view.suggestion_restaurant_description.text = restaurant.description

        view.suggestions_card_view.setOnClickListener {
            clickListener(restaurant)
            if (view?.findNavController().currentDestination?.id == R.id.mainScreen) {
//                val extras = FragmentNavigatorExtras(
//                    searchBar to "search_bar",
//                    main_screen_background to "header")
                view?.findNavController()?.navigate(R.id.action_mainScreen_to_restaurantDetails,
                    null, // Bundle of args
                    null, // NavOptions
                    null)

            }
        }
        if(restaurant.picture.isEmpty()){
            Picasso
                .get()
                .load(R.drawable.app_logo_transparent)
                .fit()
                .centerCrop()
                .into(view.suggestion_restaurant_picture)
        }
        else{
            Picasso
                .get()
                .load(restaurant.picture)
                .fit()
                .centerCrop()
                .into(view.suggestion_restaurant_picture, object: com.squareup.picasso.Callback{
                    override fun onSuccess() {

                    }

                    override fun onError(e: Exception?) {
                        Log.i("picasso", e.toString())
                        Picasso
                            .get()
                            .load(R.drawable.ic_restaurant_black_24dp)
                            .fit()
                            .centerCrop()
                            .into(view.suggestion_restaurant_picture)
                    }

                })
        }
    }
}
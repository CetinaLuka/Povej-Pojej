package feri.itk.pojejinpovej.UI.Fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.squareup.picasso.Picasso
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.Models.Review
import feri.itk.pojejinpovej.Data.ViewModels.RestaurantDetailsViewModel
import feri.itk.pojejinpovej.R
import feri.itk.pojejinpovej.UI.Adapters.RestaurantReviewsAdapter
import feri.itk.pojejinpovej.UI.Util.RecyclerViewItemDecoration
import kotlinx.android.synthetic.main.fragment_restaurant_details.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class RestaurantDetails : Fragment() {

    lateinit var restaurantViewModel: RestaurantDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //transition is postponed to give picasso time to load the picture
        postponeEnterTransition()
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initiating lateinit var restaurantViewModel which contains data for the restaurant
        restaurantViewModel = ViewModelProviders.of(activity!!)[RestaurantDetailsViewModel::class.java]
        loadRestaurantData(restaurantViewModel.getRestaurant())

    }

    private fun loadRestaurantData(restaurant: Restaurant){
        loadRestaurantPicture(restaurant.picture)
        details_restaurant_name.text = restaurant.name
        details_restaurant_address.text = restaurant.address
        details_restaurant_description.text = restaurant.description
        details_restaurant_price.text = restaurant.price.toString()
    }

    private fun loadRestaurantPicture(picture: String) {
        if(picture.isEmpty()){
            Picasso
                .get()
                .load(R.drawable.app_logo_transparent)
                .fit()
                .centerCrop()
                .into(details_restaurant_picture)
            startPostponedEnterTransition()
        }
        else{
            Picasso
                .get()
                .load(picture)
                .fit()
                .centerCrop()
                .into(details_restaurant_picture, object: com.squareup.picasso.Callback{
                    override fun onSuccess() {
                        //transition is resumed once picasso has loaded the picture
                        startPostponedEnterTransition()
                    }

                    override fun onError(e: Exception?) {
                        Toast.makeText(context, R.string.error_loading_restaurant_picture, Toast.LENGTH_SHORT).show()
                        Log.i("picasso", e.toString())
                    }

                })
        }
    }

}

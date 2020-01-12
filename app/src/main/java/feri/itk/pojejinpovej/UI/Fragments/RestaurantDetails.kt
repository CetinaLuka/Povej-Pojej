package feri.itk.pojejinpovej.UI.Fragments


import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.Models.Review
import feri.itk.pojejinpovej.Data.ViewModels.RestaurantDetailsViewModel
import feri.itk.pojejinpovej.R
import feri.itk.pojejinpovej.UI.Adapters.RestaurantReviewsAdapter
import feri.itk.pojejinpovej.Util.RecyclerViewItemDecoration
import kotlinx.android.synthetic.main.fragment_restaurant_details.*
import kotlinx.android.synthetic.main.review_alert.view.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

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
        restaurantViewModel.getRestaurant().observe(this, Observer { restaurant ->
            loadRestaurantData(restaurant)
        })
        addReviewFAB.setOnClickListener {
            openReviewAlert()
        }

    }

    private fun loadRestaurantData(restaurant: Restaurant){
        loadRestaurantPicture(restaurant.picture)
        details_restaurant_name.text = restaurant.name
        details_restaurant_address.text = restaurant.address
        details_restaurant_description.text = restaurant.description
        details_restaurant_price.text = restaurant.price.toString()
        //ATTENTION NOT LIVE DATA MUST BE FIXED
        details_restaurant_food_rating_stars.rating = 4.3.toFloat()
        details_restaurant_offer_rating_stars.rating = 2.3.toFloat()
        details_restaurant_service_rating_stars.rating = 4.7.toFloat()

        setupReviewsList(restaurant.reviews)
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
    private fun setupReviewsList(reviews: ArrayList<Review>) {
        val reviewsAdapter =
            RestaurantReviewsAdapter(reviews,{review: Review -> reviewLikeButtonClicked(review)}, {review: Review -> reviewDislikeButtonClicked(review)})
        val restauranReviewsRecycler = restaurant_reviews_recycler_view
        val layoutManager = LinearLayoutManager(context)
        restauranReviewsRecycler.layoutManager = layoutManager
        restauranReviewsRecycler.adapter = reviewsAdapter
        restauranReviewsRecycler.addItemDecoration(
            RecyclerViewItemDecoration(
                resources.getDimension(R.dimen.reviews_row_padding).toInt()
            )
        )
    }
    private fun reviewLikeButtonClicked(review: Review){
        restaurantViewModel.reviewLiked(review)
    }
    private fun reviewDislikeButtonClicked(review: Review){
        restaurantViewModel.reviewDisliked(review)
    }
    private fun openReviewAlert(){
        val alertView = LayoutInflater.from(context).inflate(R.layout.review_alert, view as ViewGroup, false)
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.rate_restaurant_title)
            .setMessage(resources.getString(R.string.rate_restaurant_text))
            .setNegativeButton(resources.getString(R.string.cancel), null)
            .setPositiveButton(resources.getString(R.string.confirm)){ _, _ ->
                val food = alertView.review_alert_food_rating.rating
                val offer = alertView.review_alert_offer_rating.rating
                val service = alertView.review_alert_service_rating.rating
                val reviewText = alertView.review_input.text.toString()
                restaurantViewModel.addReview(food, offer, service, reviewText)
                Toast.makeText(context, R.string.review_sent, Toast.LENGTH_SHORT).show()
                Log.i("review", reviewText)
            }
            .setOnDismissListener {
                val parent = alertView.parent as ViewGroup
                parent.removeView(alertView)
            }
            .setView(alertView)
            .show()

    }

}

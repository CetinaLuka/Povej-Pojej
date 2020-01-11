package feri.itk.pojejinpovej.Data.ViewModels

import androidx.lifecycle.ViewModel
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.Models.Review

//class that hold data which is displayed in the restaurant details screen
//when a restaurant in a list is clicked the restaurant var is set to its value
class RestaurantDetailsViewModel: ViewModel() {
    private lateinit var restaurant: Restaurant

    fun getRestaurant():Restaurant{
        if(!::restaurant.isInitialized){
            noRestaurantSelected()
        }
        return restaurant
    }
    fun setRestaurant(restaurant: Restaurant){
        this.restaurant = restaurant
    }

    fun reviewLiked(review: Review){
        review.reviewLiked()
    }
    fun reviewDisliked(review: Review){
        review.reviewDisliked()
    }

    private fun noRestaurantSelected(){
        this.restaurant = Restaurant("Not available")
    }
}
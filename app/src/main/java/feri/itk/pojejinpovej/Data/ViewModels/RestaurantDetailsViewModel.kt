package feri.itk.pojejinpovej.Data.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.Models.Review

//class that hold data which is displayed in the restaurant details screen
//when a restaurant in a list is clicked the restaurant var is set to its value
class RestaurantDetailsViewModel: ViewModel() {
    private var restaurant = MutableLiveData<Restaurant>()

    fun getRestaurant():MutableLiveData<Restaurant>{
        /*if(!::restaurant.isInitialized){
            noRestaurantSelected()
        }*/
        return restaurant
    }
    fun setRestaurant(restaurant: Restaurant){
        this.restaurant.value = restaurant
    }

    fun reviewLiked(review: Review){
        val r = restaurant.value?.findReview(review)
        if(r?.id != "x")
            r!!.reviewLiked()
    }
    fun reviewDisliked(review: Review){
        val r = restaurant.value?.findReview(review)
        if(r?.id != "x")
            r!!.reviewDisliked()
    }

    private fun noRestaurantSelected(){
        val liveData = MutableLiveData<Restaurant>()
        liveData.value = Restaurant("Not available")
        this.restaurant = liveData
    }
}
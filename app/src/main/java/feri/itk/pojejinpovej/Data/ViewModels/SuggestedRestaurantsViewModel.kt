package feri.itk.pojejinpovej.Data.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.Models.Review
import feri.itk.pojejinpovej.Data.RestaurantRepository

class SuggestedRestaurantsViewModel: ViewModel() {
    private lateinit var restaurants: MutableLiveData<List<Restaurant>>
    private val restaurantRepository = RestaurantRepository

    fun getRestaurants(): MutableLiveData<List<Restaurant>> {
        if(!::restaurants.isInitialized){
            loadRestaurants()
        }
        return restaurants
    }
    private fun loadRestaurants(){
        this.restaurants = restaurantRepository.returnSuggestedRestaurants()
    }
}
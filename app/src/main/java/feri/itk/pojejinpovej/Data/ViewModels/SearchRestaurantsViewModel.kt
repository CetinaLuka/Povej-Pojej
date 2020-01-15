package feri.itk.pojejinpovej.Data.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.Models.Review
import feri.itk.pojejinpovej.Data.RestaurantRepository

class SearchRestaurantsViewModel: ViewModel() {
    private val restaurantRepository = RestaurantRepository
    private lateinit var restaurants: MutableLiveData<List<Restaurant>>
    private var filterRestaurants = MutableLiveData<List<Restaurant>>()

    fun getRestaurants(): MutableLiveData<List<Restaurant>>{
        if(!::restaurants.isInitialized){
            loadRestaurants()
        }
        return filterRestaurants
    }
    private fun loadRestaurants(){

        this.restaurants = restaurantRepository.returnAllRestaurants()
        this.filterRestaurants = restaurantRepository.returnAllRestaurants()
    }
    fun filterRestaurants(filter: String){
        var filteredRestaurants = ArrayList<Restaurant>()
        if(filter.isNotEmpty()){
            for(r in restaurants.value!!){
                if(r.name.contains(filter, true)){
                    filteredRestaurants.add(r)
                }
            }
        }
        else{
            filteredRestaurants.addAll(restaurants.value!!)
        }
        filterRestaurants.value = filteredRestaurants
    }
}
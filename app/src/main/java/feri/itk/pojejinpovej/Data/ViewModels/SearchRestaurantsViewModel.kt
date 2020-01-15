package feri.itk.pojejinpovej.Data.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import feri.itk.pojejinpovej.Data.FirebaseDatabase
import feri.itk.pojejinpovej.Data.Models.Restaurant

class SearchRestaurantsViewModel: ViewModel() {
    private lateinit var restaurants: MutableLiveData<List<Restaurant>>
    private var filterRestaurants = MutableLiveData<List<Restaurant>>()
    private val firebaseDatabase = FirebaseDatabase

    fun getRestaurants(): MutableLiveData<List<Restaurant>>{
        if(!::restaurants.isInitialized){
            loadRestaurants()
        }
        return filterRestaurants
    }
    private fun loadRestaurants(){
        this.restaurants = firebaseDatabase.returnAllRestaurants()
        this.filterRestaurants = firebaseDatabase.returnAllRestaurants()
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
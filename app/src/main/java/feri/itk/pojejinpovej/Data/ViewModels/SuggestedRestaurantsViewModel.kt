package feri.itk.pojejinpovej.Data.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import feri.itk.pojejinpovej.Data.FirebaseDatabase
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.Models.Review
import feri.itk.pojejinpovej.Data.RestaurantRepository

class SuggestedRestaurantsViewModel: ViewModel() {
    private lateinit var suggestedRestaurants: MutableLiveData<List<Restaurant>>
    private lateinit var nearbyRestaurants: MutableLiveData<List<Restaurant>>
    private val firebaseDatabase = FirebaseDatabase

    fun getSuggestedRestaurants(): MutableLiveData<List<Restaurant>> {
        if(!::suggestedRestaurants.isInitialized){
            loadSuggestedRestaurants()
        }
        return suggestedRestaurants
    }
    fun getNearbyRestaurants(): MutableLiveData<List<Restaurant>> {
        if(!::nearbyRestaurants.isInitialized){
            loadNearbyRestaurants()
        }
        return nearbyRestaurants
    }
    private fun loadSuggestedRestaurants(){
        this.suggestedRestaurants = firebaseDatabase.returnSuggestedRestaurants()
    }
    private fun loadNearbyRestaurants(){
        this.nearbyRestaurants = firebaseDatabase.returnNearbyRestaurants()
    }
}
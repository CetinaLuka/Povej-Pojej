package feri.itk.pojejinpovej.Data.ViewModels

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import feri.itk.pojejinpovej.Data.FirebaseDatabase
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Util.LocationUtil
import kotlin.math.roundToInt

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
    fun findRestaurant(name: String): Restaurant{
        var res = Restaurant()
        for(r in restaurants.value!!){
            if(r.name == name){
                res = r
            }
        }
        return res
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
    fun sortRestaurants(position: Int){
        Log.i("sort", position.toString())
        when(position){
            0 -> {
                restaurants.value = restaurants.value!!.sortedWith(compareBy { it.name })
                filterRestaurants.value = filterRestaurants.value!!.sortedWith(compareBy { it.name })
                Log.i("sort", "sorting by name")
            }
        }
    }
    fun filterOpenRestaurants(open: Boolean, filter: String){
        Log.i("open", "$open ($filter)")
        var filteredRestaurants = ArrayList<Restaurant>()
        if(open){
            for(r in filterRestaurants.value!!){
                if(r.isOpenNow()){
                    filteredRestaurants.add(r)
                }
            }
            filterRestaurants.value = filteredRestaurants
        }
        else{
            Log.i("open", "untoggle")
            filteredRestaurants.addAll(restaurants.value!!)
            filterRestaurants.value = filteredRestaurants
            filterRestaurants(filter)
        }

    }
    /*fun calculateDistances(location: Location, context: Context){
        Log.i("length", ::restaurants.isInitialized.toString())
        for(r in filterRestaurants.value!!){

            val restaurantLocation = LocationUtil.returnLocationFromAddress(context, r.address)
            val rLocation = Location("rLocation")
            rLocation.latitude = restaurantLocation.latitude
            rLocation.longitude = restaurantLocation.longitude

            var distance = location.distanceTo(rLocation)/1000
            val number3digits:Double = (distance * 1000.0).roundToInt() / 1000.0
            val number2digits:Double = (number3digits * 100.0).roundToInt() / 100.0
            r.distance =  (number2digits * 10.0).roundToInt() / 10.0
        }
        val rest = restaurants.value
        restaurants.value = rest
    }*/
}
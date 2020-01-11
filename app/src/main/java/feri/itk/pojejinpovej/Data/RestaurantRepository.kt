package feri.itk.pojejinpovej.Data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.Models.Review

object RestaurantRepository {
    private var reviews = arrayListOf(
        Review("0","Janez Novak","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
        Review("1","Mirko Čeh","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
        Review("2","Marko Janežič","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
        Review("3","Jaka Novak","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
        Review("4","Darko Glušič","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
        Review("5","Janez Kranjec","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
        Review("6","Brigita Šuštarič","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
        Review("7","Janez Novak","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
        Review("8","Janez Novak","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
        Review("9","Janez Novak","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
        Review("10","Janez Novak","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")
    )
    private var restaurants = arrayListOf(
        Restaurant("Baščaršija", "https://dobregostilne.si/image/restavracija-bascarsija-n-750-750-922.jpg", 3.25, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
        Restaurant("Ancora", "https://content.selectbox.si/uploads/2014/10/darilni_paketi_restavracija_ancora_maribor_3.jpg", 3.74, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
        Restaurant("Papagayo", "https://s.inyourpocket.com/gallery/99354.jpg", 2.94, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy."),
        Restaurant("Alf", "", 2.25, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
        Restaurant("Q", "", 4.27, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
        Restaurant("Mango", "", 3.60, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
        Restaurant("Chuty's", "", 4.30, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
        Restaurant("Sarajevo", "", 2.80, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews)
    )
    fun returnAllRestaurants(): MutableLiveData<List<Restaurant>> {
        val mutableData = MutableLiveData<List<Restaurant>>()
        mutableData.value = restaurants
        return mutableData
    }

    fun returnSuggestedRestaurants(): MutableLiveData<List<Restaurant>> {
        val mutableData = MutableLiveData<List<Restaurant>>()
        mutableData.value = restaurants
        return mutableData
    }

    fun likeReview(review: Review){
        findReview(review).reviewRating =+ 1
    }
    fun dislikeReview(review: Review){
        findReview(review).reviewRating =- 1
    }
    private fun findReview(review: Review): Review{
        for(r in reviews){
            if(r.id == review.id)
                return r
        }
        Log.i("repository", "review does not exist")
        return Review("x")
    }
}
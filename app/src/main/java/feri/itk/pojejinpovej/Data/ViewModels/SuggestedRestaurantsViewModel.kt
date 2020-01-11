package feri.itk.pojejinpovej.Data.ViewModels

import androidx.lifecycle.ViewModel
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.Models.Review

class SuggestedRestaurantsViewModel: ViewModel() {
    private lateinit var restaurants: ArrayList<Restaurant>

    fun getRestaurants(): ArrayList<Restaurant> {
        if(!::restaurants.isInitialized){
            loadRestaurants()
        }
        return restaurants
    }
    private fun loadRestaurants(){
        val reviews = arrayListOf(
            Review("Janez Novak","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
            Review("Mirko Čeh","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
            Review("Marko Janežič","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
            Review("Jaka Novak","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
            Review("Darko Glušič","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
            Review("Janez Kranjec","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
            Review("Brigita Šuštarič","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
            Review("Janez Novak","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
            Review("Janez Novak","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
            Review("Janez Novak","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
            Review("Janez Novak","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")
        )
        val list = arrayListOf(
            Restaurant("Baščaršija", "https://dobregostilne.si/image/restavracija-bascarsija-n-750-750-922.jpg", 3.25, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
            Restaurant("Ancora", "https://content.selectbox.si/uploads/2014/10/darilni_paketi_restavracija_ancora_maribor_3.jpg", 3.74, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
            Restaurant("Papagayo", "https://s.inyourpocket.com/gallery/99354.jpg", 2.94, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy."),
            Restaurant("Alf", "", 2.25, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
            Restaurant("Q", "", 4.27, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
            Restaurant("Mango", "", 3.60, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
            Restaurant("Chuty's", "", 4.30, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
            Restaurant("Sarajevo", "", 2.80, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews)
        )
        this.restaurants = list
    }
}
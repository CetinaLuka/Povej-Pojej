package feri.itk.pojejinpovej.Data.ViewModels

import androidx.lifecycle.ViewModel
import feri.itk.pojejinpovej.Data.Models.Restaurant

class SuggestedRestaurantsViewModel: ViewModel() {
    private lateinit var restaurants: ArrayList<Restaurant>

    fun getRestaurants(): ArrayList<Restaurant> {
        if(!::restaurants.isInitialized){
            loadRestaurants()
        }
        return restaurants
    }
    private fun loadRestaurants(){
        val list = arrayListOf(
            Restaurant("Baščaršija", "https://dobregostilne.si/image/restavracija-bascarsija-n-750-750-922.jpg", 3.25, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy."),
            Restaurant("Ancora", "https://content.selectbox.si/uploads/2014/10/darilni_paketi_restavracija_ancora_maribor_3.jpg", 3.74, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy."),
            Restaurant("Papagayo", "https://s.inyourpocket.com/gallery/99354.jpg", 2.94, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy."),
            Restaurant("Alf", "", 2.25, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy."),
            Restaurant("Q", "", 4.27, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy."),
            Restaurant("Mango", "", 3.60, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy."),
            Restaurant("Chuty's", "", 4.30, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy."),
            Restaurant("Sarajevo", "", 2.80, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.")
            )
        this.restaurants = list
    }
}
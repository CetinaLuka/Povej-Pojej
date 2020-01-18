package feri.itk.pojejinpovej.UI.Fragments


import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mancj.materialsearchbar.MaterialSearchBar
import feri.itk.pojejinpovej.UI.Adapters.SearchResultsAdapter
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.ViewModels.RestaurantDetailsViewModel
import feri.itk.pojejinpovej.Data.ViewModels.SearchRestaurantsViewModel

import feri.itk.pojejinpovej.R
import feri.itk.pojejinpovej.Util.LocationUtil
import feri.itk.pojejinpovej.Util.PermissionChecker
import feri.itk.pojejinpovej.Util.RecyclerViewItemDecoration
import kotlinx.android.synthetic.main.fragment_restaurant_locations_map.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.searchBar

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment(), MaterialSearchBar.OnSearchActionListener, TextWatcher {

    private lateinit var searchRestaurantsViewModel: SearchRestaurantsViewModel
    private lateinit var restaurantDetailsViewModel: RestaurantDetailsViewModel

//    private lateinit var fusedLocationClient: FusedLocationProviderClient
//    private lateinit var mCurrentLocation: Location
//    private val LOCATION_PERMISSIONS_CODE = 99

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        searchRestaurantsViewModel = ViewModelProviders.of(this)[SearchRestaurantsViewModel::class.java]
        restaurantDetailsViewModel = ViewModelProviders.of(activity!!)[RestaurantDetailsViewModel::class.java]

        initCityDropdown()
        initFilterDropdown()

        //argument to know if the previous fragment was search fragment and the search must be enabled (clicked)
        /*if(arguments!!.getBoolean("fromMainScreen")){
            searchBar.enableSearch()
        }*/

        searchBar.setOnSearchActionListener(this)

        setupSearchResultsList()
        restaurant_open_group.addOnButtonCheckedListener { _, _, isChecked ->
            searchRestaurantsViewModel.filterOpenRestaurants(isChecked, searchBar.text)
        }
        filter_drop_down.setOnItemSelectedListener { _, position, _, _ ->
            filterSelected(position)
        }
        searchBar.addTextChangeListener(this)
        openMapsFAB.setOnClickListener {
            navigateToMapsFragment()
        }

    }

    /*private fun setupLocation(){
        val allowed = PermissionChecker(LOCATION_PERMISSIONS_CODE).checkLocationPermissions(context!!)
        Log.i("location", allowed.toString())
        if(!allowed){
            PermissionChecker(LOCATION_PERMISSIONS_CODE).requestLocationPermissions(activity!!, context!!)
        }
        else{
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                mCurrentLocation = location!!
                Log.i("location", "success $location")
                searchRestaurantsViewModel.calculateDistances(location!!, context!!)

            }
        }
    }*/

    private fun navigateToMapsFragment(){
        view?.findNavController()?.navigate(R.id.action_searchFragment_to_restaurantLocationsMap,
            null, // Bundle of args
            null,
            null)
        /*val restaurantLocationsMap = layoutInflater.inflate(R.layout.fragment_restaurant_locations_map, null)
        val dialog = BottomSheetDialog(context!!)
        dialog.setContentView(restaurantLocationsMap)
        dialog.show()
        restaurantLocationsMap.findViewById<TextView>(R.id.textView).setOnClickListener {
            dialog.dismiss()
        }*/
    }

    private fun filterSelected(position: Int){
        searchRestaurantsViewModel.sortRestaurants(position)
    }

    private fun setupSearchResultsList() {

        searchRestaurantsViewModel.getRestaurants().observe(this, Observer { restaurants ->
            val searchResultsAdapter = SearchResultsAdapter(restaurants){restaurant: Restaurant -> searchItemClicked(restaurant) }
            val searchResultsRecycler = search_results_recycler
            val layoutManager = LinearLayoutManager(context)
            searchResultsRecycler.layoutManager = layoutManager
            searchResultsRecycler.adapter = searchResultsAdapter
        })
        search_results_recycler.addItemDecoration(
            RecyclerViewItemDecoration(
                resources.getDimension(R.dimen.search_results_row_padding).toInt()
            )
        )
    }

    private fun searchItemClicked(restaurant: Restaurant){
        restaurantDetailsViewModel.setRestaurant(restaurant)
        Log.i("viewmodel", "restaurant set")
    }

    private fun initCityDropdown(){
        city_drop_down.setItems("Maribor")
    }
    private fun initFilterDropdown(){
        filter_drop_down.setItems("Abeceda", "Ocena", "Oddaljenost", "Cena")
    }

    private fun searchBarBackButtonClicked(){
        val extras = FragmentNavigatorExtras(
            searchBar to "search_bar",
            search_header to "header")
        view?.findNavController()?.navigate(R.id.action_searchFragment_to_mainScreen,
            null, // Bundle of args
            null,
            extras)
    }

    override fun onButtonClicked(buttonCode: Int) {

    }

    override fun onSearchStateChanged(enabled: Boolean) {
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        Toast.makeText(context, text.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        searchRestaurantsViewModel.filterRestaurants(searchBar.text)
    }

}

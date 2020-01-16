package feri.itk.pojejinpovej.UI.Fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.mancj.materialsearchbar.MaterialSearchBar
import feri.itk.pojejinpovej.UI.Adapters.SearchResultsAdapter
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.ViewModels.RestaurantDetailsViewModel
import feri.itk.pojejinpovej.Data.ViewModels.SearchRestaurantsViewModel

import feri.itk.pojejinpovej.R
import feri.itk.pojejinpovej.Util.RecyclerViewItemDecoration
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.searchBar

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment(), MaterialSearchBar.OnSearchActionListener, TextWatcher {

    lateinit var searchRestaurantsViewModel: SearchRestaurantsViewModel
    lateinit var restaurantDetailsViewModel: RestaurantDetailsViewModel

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

        searchRestaurantsViewModel = ViewModelProviders.of(this)[SearchRestaurantsViewModel::class.java]
        restaurantDetailsViewModel = ViewModelProviders.of(activity!!)[RestaurantDetailsViewModel::class.java]

        initCityDropdown()
        initFilterDropdown()

        //argument to know if the previous fragment was search fragment and the search must be enabled (clicked)
        /*if(arguments!!.getBoolean("fromMainScreen")){
            searchBar.enableSearch()
        }*/

        searchBar.setOnSearchActionListener(this)

        restaurant_open_group.addOnButtonCheckedListener { _, _, isChecked ->
            searchRestaurantsViewModel.filterOpenRestaurants(isChecked, searchBar.text)
        }
        setupSearchResultsList()
        filter_drop_down.setOnItemSelectedListener { _, position, _, _ ->
            filterSelected(position)
        }
        searchBar.addTextChangeListener(this)

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
        Log.i("nav", view?.findNavController()?.currentDestination?.label.toString())
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
        if(!enabled){
            Toast.makeText(context, "disabled", Toast.LENGTH_SHORT).show()
            //searchBarBackButtonClicked()
        }
        else{
            Toast.makeText(context, "enabled", Toast.LENGTH_SHORT).show()
        }
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

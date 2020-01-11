package feri.itk.pojejinpovej.UI.Fragments


import android.os.Bundle
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
class SearchFragment : Fragment(), MaterialSearchBar.OnSearchActionListener {

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

        if(arguments!!.getBoolean("fromMainScreen")){
            searchBar.enableSearch()
        }
        Toast.makeText(context, arguments!!.getBoolean("fromMainScreen").toString(), Toast.LENGTH_SHORT).show()
        searchBar.setOnSearchActionListener(this)

        restaurant_open_group.addOnButtonCheckedListener { group, checkedId, isChecked ->
            Toast.makeText(context, checkedId, Toast.LENGTH_SHORT).show()
        }
        setupSearchResultsList()

    }


    private fun setupSearchResultsList() {

        searchRestaurantsViewModel.getRestaurants().observe(this, Observer { restaurants ->
            val searchResultsAdapter = SearchResultsAdapter(restaurants){restaurant: Restaurant -> searchItemClicked(restaurant) }
            val searchResultsRecycler = search_results_recycler
            val layoutManager = LinearLayoutManager(context)
            searchResultsRecycler.layoutManager = layoutManager
            searchResultsRecycler.adapter = searchResultsAdapter
            searchResultsRecycler.addItemDecoration(
                RecyclerViewItemDecoration(
                    resources.getDimension(R.dimen.search_results_row_padding).toInt()
                )
            )
        })
    }

    private fun searchItemClicked(restaurant: Restaurant){
        restaurantDetailsViewModel.setRestaurant(restaurant)
        Log.i("viewmodel", "restaurant set")
        Log.i("nav", view?.findNavController()?.currentDestination?.label.toString())
    }

    private fun initCityDropdown(){
        city_drop_down.setItems("Celje", "Maribor", "Ljubljana")
    }
    private fun initFilterDropdown(){
        filter_drop_down.setItems("Cena", "Oddaljenost", "Ocena")
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
            searchBarBackButtonClicked()
        }
    }

    override fun onSearchConfirmed(text: CharSequence?) {

    }

}

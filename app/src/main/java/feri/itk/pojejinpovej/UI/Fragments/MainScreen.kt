package feri.itk.pojejinpovej.UI.Fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.transition.TransitionInflater
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import feri.itk.pojejinpovej.R
import feri.itk.pojejinpovej.UI.Adapters.SuggestionsRecyclerAdapter
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.ViewModels.RestaurantDetailsViewModel
import kotlinx.android.synthetic.main.fragment_main_screen.*

/**
 * A simple [Fragment] subclass.
 */
class MainScreen : Fragment() {

    lateinit var restaurantDetailsViewModel: RestaurantDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.shared_element_transition)
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restaurantDetailsViewModel = ViewModelProviders.of(activity!!)[RestaurantDetailsViewModel::class.java]

        setupSuggestedRestaurantsList()
        setupSecondSuggestedRestaurantsList()

        //clicking search bar starts transition to search fragment
        searchBar.setOnClickListener {
            openSearchScreen()
        }
        //button that starts transition to login fragment
        main_screen_account_button.setOnClickListener {
            openLogin()
        }
    }



    //function that specifyes shared elements between main and login screen and starts navigation to login screen
    private fun openLogin(){
        val extras = FragmentNavigatorExtras(
            main_screen_title to "main_screen_login_title",
            main_screen_background to "header")
        view?.findNavController()?.navigate(R.id.action_mainScreen_to_login,
            null, // Bundle of args
            null, // NavOptions
            extras)
    }
    //function that specifyes shared elements between main and search screen and starts navigation to search screen
    private fun openSearchScreen(){
        val extras = FragmentNavigatorExtras(
            searchBar to "search_bar",
            main_screen_background to "header")
        view?.findNavController()?.navigate(R.id.action_mainScreen_to_searchFragment,
            null, // Bundle of args
            null, // NavOptions
            extras)
    }
    private fun restaurantClicked(restaurant: Restaurant){
        Log.i("viewmodel", "before: ${restaurantDetailsViewModel.getRestaurant().name}")
        restaurantDetailsViewModel.setRestaurant(restaurant)
        Log.i("viewmodel", "after: ${restaurantDetailsViewModel.getRestaurant().name}")
    }
    private fun setupSuggestedRestaurantsList(){
        val restaurants = arrayListOf(Restaurant("Baščaršija"), Restaurant("Ancora"), Restaurant("Piano"), Restaurant("Alf"), Restaurant("Takos"), Restaurant("Papagayo"))
        val suggestedRestaurantsAdapter =
            SuggestionsRecyclerAdapter(restaurants){restaurant: Restaurant -> restaurantClicked(restaurant) }
        val suggestionsRecyclerView = main_screen_suggestion_list
        suggestionsRecyclerView.adapter = suggestedRestaurantsAdapter
        setDiscreteViewItemTransformation(suggestionsRecyclerView)
        suggestionsRecyclerView.setSlideOnFling(true)
    }
    private fun setupSecondSuggestedRestaurantsList(){
        val restaurants = arrayListOf(Restaurant("Baščaršija"), Restaurant("Ancora"), Restaurant("Piano"), Restaurant("Alf"), Restaurant("Takos"), Restaurant("Papagayo"))
        val suggestedRestaurantsAdapter =
            SuggestionsRecyclerAdapter(restaurants){restaurant: Restaurant -> restaurantClicked(restaurant) }
        val suggestionsRecyclerView = main_screen_second_suggestion_list
        suggestionsRecyclerView.adapter = suggestedRestaurantsAdapter
        setDiscreteViewItemTransformation(suggestionsRecyclerView)
        suggestionsRecyclerView.setSlideOnFling(true)
    }
    //transforms items while scrolling so that the center item is bigger
    private fun setDiscreteViewItemTransformation(suggestionsRecyclerView: DiscreteScrollView) {
        suggestionsRecyclerView.setItemTransformer(
            ScaleTransformer
                .Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.99f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build()
        )
    }


}

package feri.itk.pojejinpovej.UI.Fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.transition.TransitionInflater
import com.google.firebase.auth.FirebaseAuth
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import feri.itk.pojejinpovej.Data.FirebaseDatabase
import feri.itk.pojejinpovej.R
import feri.itk.pojejinpovej.UI.Adapters.SuggestionsRecyclerAdapter
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.ViewModels.RestaurantDetailsViewModel
import feri.itk.pojejinpovej.Data.ViewModels.SuggestedRestaurantsViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_main_screen.*

/**
 * A simple [Fragment] subclass.
 */
class MainScreen : Fragment() {

    lateinit var restaurantDetailsViewModel: RestaurantDetailsViewModel
    lateinit var suggestedRestaurantsViewModel: SuggestedRestaurantsViewModel

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.shared_element_transition)
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        if(firebaseAuth.currentUser!=null){
            main_screen_account_button.visibility=View.INVISIBLE
            main_screen_user_profile_button.visibility=View.VISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseDatabase.ctx = activity!!.applicationContext
        
        restaurantDetailsViewModel = ViewModelProviders.of(activity!!)[RestaurantDetailsViewModel::class.java]
        suggestedRestaurantsViewModel = ViewModelProviders.of(activity!!)[SuggestedRestaurantsViewModel::class.java]

        suggestedRestaurantsViewModel.getRestaurants().observe(this, Observer { restaurants ->
            setupSuggestedRestaurantsList(restaurants)
            setupSecondSuggestedRestaurantsList(restaurants)
        })

        //clicking search bar starts transition to search fragment
        searchBar.setOnClickListener {
            openSearchScreen()
        }
        //button that starts transition to login fragment
        main_screen_account_button.setOnClickListener {
            openLogin()
        }

        main_screen_user_profile_button.setOnClickListener {
            openUserSettings()
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

    //function that specifyes shared elements between main and user profile screen and starts navigation to user profile screen
    private fun openUserSettings(){
        val extras = FragmentNavigatorExtras(
            main_screen_title to "main_screen_login_title",
            main_screen_background to "header")
        view?.findNavController()?.navigate(R.id.action_mainScreen_to_userProfile,
            null, // Bundle of args
            null, // NavOptions
            null)
    }

    //function that specifyes shared elements between main and search screen and starts navigation to search screen
    private fun openSearchScreen(){
        val extras = FragmentNavigatorExtras(
            searchBar to "search_bar",
            main_screen_background to "header")
        val bundle = bundleOf("fromMainScreen" to true)
        view?.findNavController()?.navigate(R.id.action_mainScreen_to_searchFragment,
            bundle, // Bundle of args
            null, // NavOptions
            extras)
    }
    private fun restaurantClicked(restaurant: Restaurant){
        restaurantDetailsViewModel.setRestaurant(restaurant)

    }
    private fun setupSuggestedRestaurantsList(restaurants: List<Restaurant>){
        val suggestedRestaurantsAdapter =
            SuggestionsRecyclerAdapter(restaurants){restaurant: Restaurant -> restaurantClicked(restaurant) }
        val suggestionsRecyclerView = main_screen_suggestion_list
        suggestionsRecyclerView.adapter = suggestedRestaurantsAdapter
        setDiscreteViewItemTransformation(suggestionsRecyclerView)
        suggestionsRecyclerView.setSlideOnFling(true)
    }
    private fun setupSecondSuggestedRestaurantsList(restaurants: List<Restaurant>){
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

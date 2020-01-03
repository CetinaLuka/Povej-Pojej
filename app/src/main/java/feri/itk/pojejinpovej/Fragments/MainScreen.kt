package feri.itk.pojejinpovej.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.transition.TransitionInflater
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import feri.itk.pojejinpovej.R
import feri.itk.pojejinpovej.Adapters.SuggestionsRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_main_screen.*

/**
 * A simple [Fragment] subclass.
 */
class MainScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSuggestedRestaurantsList()
        setupSecondSuggestedRestaurantsList()

        searchBar.setOnClickListener {
            openSearchScreen()
        }
        main_screen_account_button.setOnClickListener {
            view.findNavController().navigate(R.id.action_mainScreen_to_login)
        }
    }
    fun openSearchScreen(){
        val extras = FragmentNavigatorExtras(
            searchBar to "search_bar",
            main_screen_header to "header")
        view?.findNavController()?.navigate(R.id.action_mainScreen_to_searchFragment,
            null, // Bundle of args
            null, // NavOptions
            extras)
    }
    fun setupSuggestedRestaurantsList(){
        val restaurants = arrayListOf<String>("Baščaršija", "Ancora", "Piano", "Alf", "Takos", "Papagayo")
        val suggestedRestaurantsAdapter =
            SuggestionsRecyclerAdapter(restaurants)
        val suggestionsRecyclerView = main_screen_suggestion_list
        suggestionsRecyclerView.adapter = suggestedRestaurantsAdapter
        suggestionsRecyclerView.setItemTransformer(
            ScaleTransformer
                .Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.99f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build()
        )
        suggestionsRecyclerView.setSlideOnFling(true)
    }
    fun setupSecondSuggestedRestaurantsList(){
        val restaurants = arrayListOf<String>("Baščaršija", "Ancora", "Piano", "Alf", "Takos", "Papagayo")
        val suggestedRestaurantsAdapter =
            SuggestionsRecyclerAdapter(restaurants)
        val suggestionsRecyclerView = main_screen_second_suggestion_list
        suggestionsRecyclerView.adapter = suggestedRestaurantsAdapter
        suggestionsRecyclerView.setItemTransformer(
            ScaleTransformer
                .Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.99f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build()
            )
        suggestionsRecyclerView.setSlideOnFling(true)
    }


}

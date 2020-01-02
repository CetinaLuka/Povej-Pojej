package feri.itk.pojejinpovej.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import feri.itk.pojejinpovej.R
import feri.itk.pojejinpovej.SuggestionsRecyclerAdapter
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
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSuggestedRestaurantsList()
        setupSecondSuggestedRestaurantsList()
    }
    fun setupSuggestedRestaurantsList(){
        val restaurants = arrayListOf<String>("Baščaršija", "Ancora", "Piano", "Alf", "Takos", "Papagayo")
        val suggestedRestaurantsAdapter = SuggestionsRecyclerAdapter(restaurants)
        val suggestionsRecyclerView = main_screen_suggestion_list
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        suggestionsRecyclerView.layoutManager = layoutManager
        suggestionsRecyclerView.adapter = suggestedRestaurantsAdapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(suggestionsRecyclerView)
    }
    fun setupSecondSuggestedRestaurantsList(){
        val restaurants = arrayListOf<String>("Baščaršija", "Ancora", "Piano", "Alf", "Takos", "Papagayo")
        val suggestedRestaurantsAdapter = SuggestionsRecyclerAdapter(restaurants)
        val suggestionsRecyclerView = main_screen_second_suggestion_list
//        val layoutManager = LinearLayoutManager(context)
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//        suggestionsRecyclerView.layoutManager = layoutManager
        suggestionsRecyclerView.adapter = suggestedRestaurantsAdapter
//        val snapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(suggestionsRecyclerView)
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

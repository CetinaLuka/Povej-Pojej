package feri.itk.pojejinpovej.Fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.transition.TransitionInflater
import com.mancj.materialsearchbar.MaterialSearchBar

import feri.itk.pojejinpovej.R
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment(), MaterialSearchBar.OnSearchActionListener {


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

        initCityDropdown()
        initFilterDropdown()

        searchBar.enableSearch()
        searchBar.setOnSearchActionListener(this)
        searchBar.setCardViewElevation(10)
    }
    fun initCityDropdown(){
        city_drop_down.setItems("Celje", "Maribor", "Ljubljana")
    }
    fun initFilterDropdown(){
        filter_drop_down.setItems("Jelly Bean", "KitKat", "Lollipop", "Marshmallow")
    }

    override fun onButtonClicked(buttonCode: Int) {
        Toast.makeText(context,"clicked button", Toast.LENGTH_SHORT).show()
        Log.i("search", "Back clicked")
        when (buttonCode){
            MaterialSearchBar.BUTTON_BACK -> (
                    Log.i("search", "Back clicked")

            )
        }
    }

    override fun onSearchStateChanged(enabled: Boolean) {
        Log.i("search", "state changed")

    }

    override fun onSearchConfirmed(text: CharSequence?) {
        Log.i("search", "search confirmed")
    }
}

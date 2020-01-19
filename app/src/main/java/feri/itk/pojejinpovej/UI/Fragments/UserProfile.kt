package feri.itk.pojejinpovej.UI.Fragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.facebook.login.LoginManager
import com.google.android.material.slider.Slider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import feri.itk.pojejinpovej.Data.FirebaseDatabase
import feri.itk.pojejinpovej.R
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_user_profile.*

/**
 * A simple [Fragment] subclass.
 */
class UserProfile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseDatabase.ctx = activity!!.applicationContext
        //logout
        username.setText(FirebaseAuth.getInstance().currentUser!!.displayName)
        logout_button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            LoginManager.getInstance().logOut()
            Toast.makeText(activity,"Uspešno ste se odjavili", Toast.LENGTH_SHORT).show()
            val extras = FragmentNavigatorExtras(
                searchBar to "search_bar",
                search_header to "header")
            view?.findNavController()?.navigate(R.id.action_userProfile_to_mainScreen,
                null, // Bundle of args
                null, // NavOptions
                null)
        }
        
        //sliders
        val sharedPref: SharedPreferences = this.activity!!.getSharedPreferences("pref",Context.MODE_PRIVATE)
        //read and set slider values
        var default = "2"
        sliderFood.value=sharedPref.getFloat("sliderFood", default.toFloat())
        sliderOffer.value=sharedPref.getFloat("sliderOffer", default.toFloat())
        sliderService.value=sharedPref.getFloat("sliderService", default.toFloat())
        //listen sliders for changes
        sliderFood.setOnChangeListener(object : Slider.OnChangeListener {
            override fun onValueChange(slider: Slider?, value: Float) {
                val editor = sharedPref.edit()
                editor.putFloat("sliderFood",value)
                editor.apply()
            }
        })
        sliderOffer.setOnChangeListener(object : Slider.OnChangeListener {
            override fun onValueChange(slider: Slider?, value: Float) {
                val editor = sharedPref.edit()
                editor.putFloat("sliderOffer",value)
                editor.apply()
            }
        })
        sliderService.setOnChangeListener(object : Slider.OnChangeListener {
            override fun onValueChange(slider: Slider?, value: Float) {
                val editor = sharedPref.edit()
                editor.putFloat("sliderService",value)
                editor.apply()
            }
        })
    }
}

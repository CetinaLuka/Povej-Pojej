package feri.itk.pojejinpovej.UI.Fragments


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
import com.google.firebase.auth.FirebaseAuth
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
    }
}

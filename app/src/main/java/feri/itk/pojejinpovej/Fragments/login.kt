package feri.itk.pojejinpovej.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionInflater
import feri.itk.pojejinpovej.R

/**
 * A simple [Fragment] subclass.
 */
class login : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.shared_element_transition)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


}

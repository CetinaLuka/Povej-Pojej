package feri.itk.pojejinpovej.UI.Fragments


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.transition.TransitionInflater
import com.facebook.*
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import feri.itk.pojejinpovej.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class login : Fragment() {

    val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions

    // Initialize Facebook Login button
    val callbackManager = CallbackManager.Factory.create()

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.shared_element_transition)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        if(firebaseAuth.currentUser==null){
            logout_button.isClickable=false
            logout_button.visibility=View.INVISIBLE
        }
        else{
            login_button.isClickable=false
            login_button.visibility=View.INVISIBLE
            google_login_button.isClickable=false
            google_login_button.visibility=View.INVISIBLE
            textView4.visibility=View.INVISIBLE
        }
        //Google
        initGoogleOptions()
        setupUI()

        //Facebook

        login_button.setOnClickListener {
            LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_ONLY)
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"))
            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d(TAG, "facebook:onSuccess:$loginResult")
                    System.out.println("loginan")
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.d(TAG, "facebook:onCancel")
                    System.out.println("kensl")
                    // ...
                }

                override fun onError(error: FacebookException) {
                    Log.d(TAG, "facebook:onError", error)
                    System.out.println("NAPAKA")
                    System.out.println(error.localizedMessage)
                    // ...
                }
            })
        }


        //logout
        logout_button.setOnClickListener {
            mGoogleSignInClient.signOut()
            FirebaseAuth.getInstance().signOut()
            LoginManager.getInstance().logOut()
            Toast.makeText(activity,"Logged out",Toast.LENGTH_SHORT).show()
            val extras = FragmentNavigatorExtras(
                searchBar to "search_bar",
                search_header to "header")
            view?.findNavController()?.navigate(R.id.action_login_to_mainScreen,
                null, // Bundle of args
                null, // NavOptions
                null)
        }
    }

    private fun initGoogleOptions(){
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, mGoogleSignInOptions)
    }

    private fun setupUI(){
        google_login_button.setOnClickListener {
            signIn()
        }
    }

    private fun signIn(){
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Google Login
        if(requestCode == RC_SIGN_IN){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            }
            catch(e: ApiException){
                e.printStackTrace()
                //Toast.makeText(activity,"Google Sign In failed miserably",Toast.LENGTH_SHORT).show()
            }
        }
        //Facebook Login
        else{
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount){
        val credential=GoogleAuthProvider.getCredential(acct.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(activity,"Google Sign In successful as "+firebaseAuth.currentUser?.email,Toast.LENGTH_SHORT).show()
                val bundle = bundleOf("userId" to firebaseAuth.currentUser?.uid)
                val extras = FragmentNavigatorExtras(
                    searchBar to "search_bar",
                    search_header to "header")
                view?.findNavController()?.navigate(R.id.action_login_to_mainScreen,
                    bundle, // Bundle of args
                    null, // NavOptions
                    null)
            }
            else{
                Toast.makeText(activity,"Google Sign In failed",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithFacebookCredential:success")
                    Toast.makeText(activity,"Facebook Sign In successful as "+firebaseAuth.currentUser?.displayName,Toast.LENGTH_SHORT).show()
                    val user = firebaseAuth.currentUser
                    val bundle = bundleOf("userId" to firebaseAuth.currentUser?.uid)
                    val extras = FragmentNavigatorExtras(
                        searchBar to "search_bar",
                        search_header to "header")
                    view?.findNavController()?.navigate(R.id.action_login_to_mainScreen,
                        bundle, // Bundle of args
                        null, // NavOptions
                        null)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithFacebookCredential:failure", task.exception)
                    Toast.makeText(activity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }

                // ...
            }
    }
}

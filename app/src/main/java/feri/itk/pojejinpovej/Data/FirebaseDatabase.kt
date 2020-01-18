package feri.itk.pojejinpovej.Data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import feri.itk.pojejinpovej.Data.Models.Restaurant


object FirebaseDatabase {
    lateinit var firebase: String
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    init {
        Log.i("FirebaseDatabase", "initiating firebase database")

        database = FirebaseDatabase.getInstance().reference
        firebaseAuth = FirebaseAuth.getInstance()

    }

    fun returnAllRestaurants(): MutableLiveData<List<Restaurant>>{
        val mutableData = MutableLiveData<List<Restaurant>>()
        val rootRef =
            FirebaseDatabase.getInstance().reference
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listek: ArrayList<Restaurant> = ArrayList()
                for (ds in dataSnapshot.children) {
                    val r:Restaurant = Restaurant()
                    r.name = ds.child("name").value.toString()
                    r.picture = ds.child("picture").value.toString()
                    r.price = ds.child("price").value.toString().toDouble()
                    r.address = ds.child("address").value.toString()
                    r.postCode = ds.child("postCode").value.toString()
                    r.description = ds.child("description").value.toString()
                    //rates
                    r.rateFood = ds.child("rateFood").value.toString().toDouble()
                    r.rateOffer = ds.child("rateOffer").value.toString().toDouble()
                    r.rateService = ds.child("rateService").value.toString().toDouble()
                    //calculating rate for person
                    if(firebaseAuth.currentUser!=null) {
                        // TO DO - DIFFERENT CALCULATION DEPENDING ON USER SETTINGS
                        r.rate = (r.rateFood+r.rateOffer+r.rateService)/5
                    }
                    else{
                        r.rate = (r.rateFood+r.rateOffer+r.rateService)/3
                    }
                    listek.add(r)
                    //Log.d("TAG", restaurantId)
                }
                mutableData.value=listek
                //Log.d("TAG", list)

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        rootRef.addListenerForSingleValueEvent(eventListener)
        return mutableData
    }

}
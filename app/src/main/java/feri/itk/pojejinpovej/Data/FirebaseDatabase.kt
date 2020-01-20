package feri.itk.pojejinpovej.Data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import feri.itk.pojejinpovej.Data.Models.Restaurant
import kotlin.math.round


object FirebaseDatabase{
    lateinit var ctx: Context
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var sharedPref: SharedPreferences

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
                    r.website = ds.child("website").value.toString()
                    //rates
                    r.rateFood = ds.child("rateFood").value.toString().toDouble()
                    r.rateOffer = ds.child("rateOffer").value.toString().toDouble()
                    r.rateService = ds.child("rateService").value.toString().toDouble()
                    r.reviews = RestaurantRepository.reviews
                    //calculating rate for person
                    if(firebaseAuth.currentUser!=null) {
                        sharedPref = ctx.getSharedPreferences("pref", Context.MODE_PRIVATE)
                        var  sFood = sharedPref.getFloat("sliderFood", Float.MAX_VALUE)
                        var  sOffer = sharedPref.getFloat("sliderOffer", Float.MAX_VALUE)
                        var  sService = sharedPref.getFloat("sliderService", Float.MAX_VALUE)
                        // different calculations depending on user settings
                        r.rate = (r.rateFood*(sFood/3)+r.rateOffer*(sOffer/3)+r.rateService*(sService/3))/3
                    }
                    else{
                        r.rate = (r.rateFood+r.rateOffer+r.rateService)/3
                    }
                    r.rate=round(r.rate*10)/10
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

    fun returnSuggestedRestaurants(): MutableLiveData<List<Restaurant>>{
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
                    r.website = ds.child("website").value.toString()
                    //rates
                    r.rateFood = ds.child("rateFood").value.toString().toDouble()
                    r.rateOffer = ds.child("rateOffer").value.toString().toDouble()
                    r.rateService = ds.child("rateService").value.toString().toDouble()
                    r.reviews = RestaurantRepository.reviews
                    //calculating rate for person
                    if(firebaseAuth.currentUser!=null) {
                        sharedPref = ctx.getSharedPreferences("pref", Context.MODE_PRIVATE)
                        var  sFood = sharedPref.getFloat("sliderFood", Float.MAX_VALUE)
                        var  sOffer = sharedPref.getFloat("sliderOffer", Float.MAX_VALUE)
                        var  sService = sharedPref.getFloat("sliderService", Float.MAX_VALUE)
                        // different calculations depending on user settings
                        r.rate = (r.rateFood*(sFood/3)+r.rateOffer*(sOffer/3)+r.rateService*(sService/3))/3
                    }
                    else{
                        r.rate = (r.rateFood+r.rateOffer+r.rateService)/3
                    }
                    r.rate=round(r.rate*10)/10
                    if(r.rate < 3.5)
                        continue
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

    fun returnNearbyRestaurants(): MutableLiveData<List<Restaurant>>{
        val mutableData = MutableLiveData<List<Restaurant>>()
        val rootRef =
            FirebaseDatabase.getInstance().reference
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listek: ArrayList<Restaurant> = ArrayList()
                for (ds in dataSnapshot.children) {
                    val r:Restaurant = Restaurant()
                    if(r.distance > 300)
                        continue
                    r.name = ds.child("name").value.toString()
                    r.picture = ds.child("picture").value.toString()
                    r.price = ds.child("price").value.toString().toDouble()
                    r.address = ds.child("address").value.toString()
                    r.postCode = ds.child("postCode").value.toString()
                    r.description = ds.child("description").value.toString()
                    r.website = ds.child("website").value.toString()
                    //rates
                    r.rateFood = ds.child("rateFood").value.toString().toDouble()
                    r.rateOffer = ds.child("rateOffer").value.toString().toDouble()
                    r.rateService = ds.child("rateService").value.toString().toDouble()
                    r.reviews = RestaurantRepository.reviews
                    //calculating rate for person
                    if(firebaseAuth.currentUser!=null) {
                        sharedPref = ctx.getSharedPreferences("pref", Context.MODE_PRIVATE)
                        var  sFood = sharedPref.getFloat("sliderFood", Float.MAX_VALUE)
                        var  sOffer = sharedPref.getFloat("sliderOffer", Float.MAX_VALUE)
                        var  sService = sharedPref.getFloat("sliderService", Float.MAX_VALUE)
                        // different calculations depending on user settings
                        r.rate = (r.rateFood*(sFood/3)+r.rateOffer*(sOffer/3)+r.rateService*(sService/3))/3
                    }
                    else{
                        r.rate = (r.rateFood+r.rateOffer+r.rateService)/3
                    }
                    r.rate=round(r.rate*10)/10
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
package feri.itk.pojejinpovej.Data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import feri.itk.pojejinpovej.Data.Models.Restaurant


object FirebaseDatabase {
    lateinit var firebase: String
    private lateinit var database: DatabaseReference

    init {
        Log.i("FirebaseDatabase", "initiating firebase database")

        database = FirebaseDatabase.getInstance().reference

    }

    fun returnAllRestaurants(): MutableLiveData<List<Restaurant>>{
        val mutableData = MutableLiveData<List<Restaurant>>()
        val rootRef =
            FirebaseDatabase.getInstance().reference
        //val usersRef = rootRef.child("users")
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //val list: MutableList<String?> = ArrayList()
                val listek: ArrayList<Restaurant> = ArrayList()
                for (ds in dataSnapshot.children) {
                    val r:Restaurant = Restaurant()
                    val restaurantId = ds.key
                    //list.add(restaurantId)
                    r.name = ds.child("name").value.toString()
                    r.picture = ds.child("picture").value.toString()
                    r.price = ds.child("price").value.toString().toDouble()
                    r.address = ds.child("address").value.toString()
                    r.postCode = ds.child("postCode").value.toString()
                    r.description = ds.child("description").value.toString()
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
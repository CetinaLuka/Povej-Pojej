package feri.itk.pojejinpovej.UI.Fragments


import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.WorkerThread
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.ViewModels.SearchRestaurantsViewModel
import feri.itk.pojejinpovej.R

/**
 * A simple [Fragment] subclass.
 */
class RestaurantLocationsMap : Fragment(), OnMapReadyCallback {

    lateinit var searchRestaurantsViewModel: SearchRestaurantsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_restaurant_locations_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchRestaurantsViewModel = ViewModelProviders.of(this)[SearchRestaurantsViewModel::class.java]
    }

    override fun onMapReady(map: GoogleMap?) {
        searchRestaurantsViewModel.getRestaurants().observe(this, Observer { restaurants ->
            showRestaurantsOnMap(restaurants, map!!)
        })
    }
    private fun showRestaurantsOnMap(restaurants: List<Restaurant>, map: GoogleMap){
        for(r in restaurants){
            val location = returnLocationFromAddress(r.address+", 2000 Maribor")
            map.addMarker(MarkerOptions()
                .position(LatLng(location.latitude,location.longitude))
                .title(r.name))
        }
        premakniKamero(LatLng(46.558393, 15.6449331), 1000, map)
    }
    private fun premakniKamero(latLng: LatLng, duration: Int, map: GoogleMap){
        val googlePlex = CameraPosition.builder()
            .target(latLng)
            .zoom(17f)
            .bearing(0f)
            .build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), duration, null)
    }
    @WorkerThread
    fun returnLocationFromAddress(address: String): Address{
        val geocoder = Geocoder(context)
        val address = geocoder.getFromLocationName(address, 1)
        Log.i("address", address.toString())
        return address[0]
    }


}

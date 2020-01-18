package feri.itk.pojejinpovej.UI.Fragments


import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.ViewModels.RestaurantDetailsViewModel
import feri.itk.pojejinpovej.Data.ViewModels.SearchRestaurantsViewModel
import feri.itk.pojejinpovej.R
import feri.itk.pojejinpovej.Util.LocationUtil
import feri.itk.pojejinpovej.Util.PermissionChecker

/**
 * A simple [Fragment] subclass.
 */
class RestaurantLocationsMap : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    lateinit var searchRestaurantsViewModel: SearchRestaurantsViewModel
    lateinit var restaurantViewModel: RestaurantDetailsViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mCurrentLocation: Location
    private val LOCATION_PERMISSIONS_CODE = 99
    private lateinit var gMap : GoogleMap

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        searchRestaurantsViewModel = ViewModelProviders.of(this)[SearchRestaurantsViewModel::class.java]
        restaurantViewModel = ViewModelProviders.of(activity!!)[RestaurantDetailsViewModel::class.java]
    }

    fun retrieveLocation(){
        Log.i("location", "retrieving")
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            mCurrentLocation = location!!
            LocationUtil.moveCamera(LatLng(mCurrentLocation.latitude, mCurrentLocation.longitude), 1500, gMap)
            gMap.isMyLocationEnabled = true
            Log.i("location", "success $location")
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        gMap = map!!
        val allowed = PermissionChecker(LOCATION_PERMISSIONS_CODE).checkLocationPermissions(context!!)
        Log.i("location", allowed.toString())
        if(!allowed){
            PermissionChecker(LOCATION_PERMISSIONS_CODE).requestLocationPermissions(activity!!, context!!)
        }
        else{
            retrieveLocation()
        }

        map!!.uiSettings.isMyLocationButtonEnabled = true
        searchRestaurantsViewModel.getRestaurants().observe(this, Observer { restaurants ->
            showRestaurantsOnMap(restaurants, map)
        })
    }
    private fun showRestaurantsOnMap(restaurants: List<Restaurant>, map: GoogleMap){
        for(r in restaurants){
            val location = LocationUtil.returnLocationFromAddress(context!!,r.address+", 2000 Maribor")
            map.addMarker(MarkerOptions()
                .position(LatLng(location.latitude,location.longitude))
                .title(r.name))
        }
        map.setOnInfoWindowClickListener(this)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.i("location", "permissions callback")
        if(requestCode == LOCATION_PERMISSIONS_CODE){
            if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(context!!,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                    retrieveLocation()
                    if(::gMap.isInitialized){
                        gMap.isMyLocationEnabled = true
                    }
                    Toast.makeText(context, "Odobreno", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(context, "Ni dovoljenja", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onInfoWindowClick(marker: Marker?) {
        Log.i("location", "marker clicked ${marker!!.title}")
        val restaurant = searchRestaurantsViewModel.findRestaurant(marker!!.title)
        restaurantViewModel.setRestaurant(restaurant)
        view?.findNavController()?.navigate(R.id.action_restaurantLocationsMap_to_restaurantDetails,
            null, // Bundle of args
            null,
            null)
    }
}

package feri.itk.pojejinpovej.Util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.annotation.WorkerThread
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

object LocationUtil {

    @WorkerThread
    fun returnLocationFromAddress(context: Context, address: String): Address {
        var gc = Geocoder(context)
        val address = gc.getFromLocationName(address, 1)
        Log.i("address", address.toString())
        return address[0]
    }

    fun moveCamera(latLng: LatLng, duration: Int, map: GoogleMap){
        val googlePlex = CameraPosition.builder()
            .target(latLng)
            .zoom(17f)
            .bearing(0f)
            .build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), duration, null)
    }
}
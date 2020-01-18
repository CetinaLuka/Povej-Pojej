package feri.itk.pojejinpovej.Util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.fragment.app.FragmentActivity

class PermissionChecker(val permissionCode: Int) {
    fun checkLocationPermissions(context: Context): Boolean{
        return (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }
    fun requestLocationPermissions(activity: FragmentActivity, context: Context){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            AlertDialog.Builder(context)
                .setTitle("Potrebno je dovoljenje za dostop do vaše lokacije")
                .setMessage("Aplikacija potrebuje dostop do vaše lokacije za delovanje zato vas prosimo da to omogočite.")
                .setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
                    requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
                })
                .create()
                .show()
        } else {
            // No explanation needed, we can request the permission.
            requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }
}
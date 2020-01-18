package feri.itk.pojejinpovej.Data.Models

import android.util.Log
import feri.itk.pojejinpovej.R

data class Restaurant (
    var name: String = "",
    var picture: String = "",
    var price: Double = 0.0,
    var address: String = "",
    var postCode: String = "",
    var description: String = "",
    var reviews: ArrayList<Review> = ArrayList(),
    var workingHours: WorkingHours = WorkingHours(),
    var distance: Int = 100,
    var rateFood: Double = 0.0,
    var rateOffer: Double = 0.0,
    var rateService: Double = 0.0,
    var rate: Double = 0.0
){
    fun findReview(review: Review): Review{
        for(r in reviews){
            if(r.id == review.id)
                return r
        }
        return Review("x")
    }
    fun addReview(review: Review){
        reviews.add(review)
    }
    fun isOpenNow():Boolean{
        Log.i("open", "$name - ${workingHours.isOpenNow()}")
        return workingHours.isOpenNow()
    }
}
package feri.itk.pojejinpovej.Data.Models

import feri.itk.pojejinpovej.R

data class Restaurant (
    var name: String = "",
    var picture: String = "",
    var price: Double = 0.0,
    var address: String = "",
    var postCode: String = "",
    var description: String = "",
    var reviews: ArrayList<Review> = ArrayList()
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
}
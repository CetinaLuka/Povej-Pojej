package feri.itk.pojejinpovej.Data.Models

import feri.itk.pojejinpovej.R

data class Restaurant (
    val name: String = "",
    val picture: String = "",
    val price: Double = 0.0,
    val address: String = "",
    val postCode: String = "",
    val description: String = "",
    val reviews: ArrayList<Review> = ArrayList()
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
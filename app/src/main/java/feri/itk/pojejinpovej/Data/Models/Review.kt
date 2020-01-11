package feri.itk.pojejinpovej.Data.Models

import java.util.*

data class Review(
    val reviewer: String = "",
    var text: String = "",
    val date: Date = Date(),
    var reviewRating: Int = 0,
    var foodRating: Double = 0.0,
    var offerRating: Double = 0.0,
    var serviceRating: Double = 0.0

) {
    fun reviewLiked(){
        reviewRating += 1
    }
    fun reviewDisliked(){
        reviewRating -= 1
    }
}
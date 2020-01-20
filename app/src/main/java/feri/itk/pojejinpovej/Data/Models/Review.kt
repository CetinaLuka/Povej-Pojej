package feri.itk.pojejinpovej.Data.Models

import java.time.LocalDate
import java.util.*

data class Review(
    val id: String = "0",
    val reviewer: String = "",
    var text: String = "",
    var foodRating: Double = 0.0,
    var offerRating: Double = 0.0,
    var serviceRating: Double = 0.0,
    val date: LocalDate = LocalDate.of((2018..2019).random(), (1..12).random(), (1..28).random())

) {
    var reviewRating: Int = 0
    fun reviewLiked(){
        reviewRating += 1
    }
    fun reviewDisliked(){
        reviewRating -= 1
    }
}
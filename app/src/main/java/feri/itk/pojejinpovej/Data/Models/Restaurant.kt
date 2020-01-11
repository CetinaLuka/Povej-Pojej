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
}
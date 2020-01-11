package feri.itk.pojejinpovej.Data.Models

data class Review(
    var reviewer: String = "",
    var text: String = "",
    var rating: Int = 0
) {
    fun reviewLiked(){
        rating += 1
    }
    fun reviewDisliked(){
        rating -= 1
    }
}
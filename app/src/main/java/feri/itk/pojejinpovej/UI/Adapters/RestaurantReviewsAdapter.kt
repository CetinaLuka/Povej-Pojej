package feri.itk.pojejinpovej.UI.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.Models.Review
import feri.itk.pojejinpovej.R
import kotlinx.android.synthetic.main.restaurant_reviews_recycler_view_row.view.*

class RestaurantReviewsAdapter(list: List<Review>, val likeClickListener: (Review) -> Unit, val dislikeClickListener: (Review) -> Unit): RecyclerView.Adapter<ReviewsViewHolder>(){
    val reviewList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.restaurant_reviews_recycler_view_row, parent, false)
        return ReviewsViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        holder.bind(reviewList[position], likeClickListener, dislikeClickListener)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }
}
class ReviewsViewHolder(val view: View): RecyclerView.ViewHolder(view){
    fun bind(review: Review, likeClickListener: (Review) -> Unit, dislikeClickListener: (Review) -> Unit){
        view.review_like_button.setOnClickListener{
            likeClickListener(review)
        }
        view.review_dislike_button.setOnClickListener{
            dislikeClickListener(review)
        }
    }
}
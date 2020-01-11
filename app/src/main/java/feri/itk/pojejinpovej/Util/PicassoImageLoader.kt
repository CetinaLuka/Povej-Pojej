package feri.itk.pojejinpovej.Util

import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import feri.itk.pojejinpovej.R
import kotlinx.android.synthetic.main.suggestion_recycler_row.view.*

object PicassoImageLoader {
    fun loadImage(picture: String, imageView: ImageView){
        val picasso = Picasso.get()
        if(picture.isEmpty()){
            picasso
                .load(R.drawable.app_logo_transparent)
                .fit()
                .centerCrop()
                .into(imageView)
        }
        else{
            picasso
                .load(picture)
                .fit()
                .centerCrop()
                .into(imageView, object: com.squareup.picasso.Callback{
                    override fun onSuccess() {

                    }

                    override fun onError(e: Exception?) {
                        Log.i("picasso", e.toString())
                        picasso
                            .load(R.drawable.app_logo_transparent)
                            .fit()
                            .centerCrop()
                            .into(imageView)
                    }

                })
        }
    }
}
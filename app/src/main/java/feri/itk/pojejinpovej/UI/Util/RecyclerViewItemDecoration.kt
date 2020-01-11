package feri.itk.pojejinpovej.UI.Util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

//item decoration class that makes sure there is equal padding around all recyclerview items
class RecyclerViewItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceHeight
            }
            left =  spaceHeight
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}
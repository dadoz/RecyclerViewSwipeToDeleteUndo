package application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter.callbacks

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

import application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter.DeletableRvAdapter
import application.davidelmn.swipetodeleteundorecyclerviewlibrary.utils.ColorUtils

/**
 * Created by davide-syn on 9/27/17.
 */

class SimpleTouchHelperCallbacks(private val recyclerView: RecyclerView, dragDirs: Int, swipeDirs: Int) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    /**
     * if (testAdapter.isUndoOn() && testAdapter.isPendingRemoval(position)) {
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    override fun getSwipeDirs(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        val hasPendingRemoval = (recyclerView!!.adapter as DeletableRvAdapter<*, *>)
                .isPendingRemoval(viewHolder!!.adapterPosition)
        return if (hasPendingRemoval) 0 else super.getSwipeDirs(recyclerView, viewHolder)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
        (recyclerView.adapter as DeletableRvAdapter<*, *>).pendingRemoval(viewHolder.adapterPosition)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                             dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if (viewHolder.adapterPosition == -1) {
            return
        }
        // draw red background
        var translationX = dX

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                translationX = Math.min(-dX, (-viewHolder.itemView.width / 4).toFloat())
            }

            //red background canvass
            val redBck = ColorUtils.getBackgroundColorDrawable(viewHolder.itemView.context)
            redBck.setBounds(viewHolder.itemView.right + dX.toInt(), viewHolder.itemView.top,
                    viewHolder.itemView.right, viewHolder.itemView.bottom)
            redBck.draw(c)
            //white background canvass
            //            Drawable whiteBck = new ColorDrawable(Color.WHITE);
            //            whiteBck.setBounds(viewHolder.itemView.getRight() -viewHolder.itemView.getRight()/4, viewHolder.itemView.getTop(),
            //                    viewHolder.itemView.getLeft(), viewHolder.itemView.getBottom());
            //            whiteBck.draw(c);
        }
        super.onChildDraw(c, recyclerView, viewHolder, translationX, dY, actionState, isCurrentlyActive)
    }

}
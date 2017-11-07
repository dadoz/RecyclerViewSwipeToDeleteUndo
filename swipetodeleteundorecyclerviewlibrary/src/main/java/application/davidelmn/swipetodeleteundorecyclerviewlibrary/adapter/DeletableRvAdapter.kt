package application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import application.davidelmn.swipetodeleteundorecyclerviewlibrary.utils.ColorUtils
import java.util.*

/**
 * Created by davide-syn on 9/27/17.
 */
abstract class DeletableRvAdapter<VH : DeletableVh, T>
/**
 * constructor
 * @param items
 * @param context
 */
protected constructor(val items: List<T>, context: Context) : RecyclerView.Adapter<VH>() {
    private val pengingSubscriptions = HashMap<T, Subscription>() // map of items to pending runnables, so we can cancel a removal if need be
    private val redColor: Drawable = ColorUtils.getBackgroundColorDrawable(context)
    private val whiteColor: Drawable = ColorDrawable(Color.WHITE)

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        val item = items[position]
        val isDeletedItemInPending = hasDeleteItemInPending(item)
        //set delete button
        viewHolder.setUndoButtonEnabled(View.OnClickListener { handleUndoButtonClick(item) }, isDeletedItemInPending)
        //set background color
        viewHolder.itemView.background = if (isDeletedItemInPending) redColor else whiteColor
    }

    override fun getItemCount(): Int {
        return items.size
    }


    /**
     * @param item
     */
    fun handleUndoButtonClick(item: T) {
        // user wants to undo the removal, let's cancel the pending task
        val subscription = pengingSubscriptions[item]
        if (subscription != null) {
            subscription.removeCallbacks()
            // this will rebind the row in "normal" state
            notifyItemChanged(subscription.position)
            //remove handler
            pengingSubscriptions.remove(item)
        }
    }

    /**
     * pending removal
     * @param position
     */
    fun pendingRemoval(position: Int) {
        val item = items[position]

        // let's create, store and post a runnable to remove the item
        val handler = Handler() // hanlder for running delayed runnables
        val runnable = Runnable { remove(position) }
        handler.postDelayed(runnable, PENDING_REMOVAL_TIMEOUT.toLong())
        pengingSubscriptions.put(item, Subscription(handler, runnable, position))

        // this will redraw row in "undo" state
        notifyItemChanged(position)
    }

    /**
     * remove
     * @param position
     */
    private fun remove(position: Int) {
        val item = items[position]
        if (items.contains(item)) {
            (items as ArrayList).removeAt(position)
            notifyItemRemoved(position)
        }
    }

    /**
     *
     * @param position
     * @return
     */
    fun isPendingRemoval(position: Int): Boolean {
        return hasDeleteItemInPending(items[position])
    }

    private fun hasDeleteItemInPending(item: T): Boolean {
        return pengingSubscriptions.keys.contains(item)
    }

    private inner class Subscription internal constructor(val handler: Handler, val runnable: Runnable, val position: Int) {
        fun removeCallbacks() {
            handler.removeCallbacks(runnable)
        }
    }

    companion object {
        private val PENDING_REMOVAL_TIMEOUT = 3000 // 3sec TODO move to config
    }
}
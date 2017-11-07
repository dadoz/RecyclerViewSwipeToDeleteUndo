package application.davidelmn.swipetodeleteundorecyclerviewlibrary.views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter.callbacks.SimpleTouchHelperCallbacks
import application.davidelmn.swipetodeleteundorecyclerviewlibrary.helper.ItemTouchHelper

/**
 * Created by davide-syn on 9/27/17.
 */

class SwipeDeleteRecyclerView : RecyclerView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    /**
     * init recycler view
     */
    private fun init() {
        //setItemTouchHelper
        ItemTouchHelper(SimpleTouchHelperCallbacks(this, 0, ItemTouchHelper.LEFT))
                .attachToRecyclerView(this)
    }
}

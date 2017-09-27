package application.davidelmn.swipetodeleteundorecyclerviewlibrary.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;

import application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter.callbacks.SimpleTouchHelperCallbacks;
import application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter.decoration.SimpleItemDecoration;

/**
 * Created by davide-syn on 9/27/17.
 */

public class SwipeDeleteRecyclerView extends RecyclerView {
    public SwipeDeleteRecyclerView(Context context) {
        super(context);
        init();
    }

    public SwipeDeleteRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeDeleteRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * init recycler view
     */
    void init() {
        //setItemTouchHelper
        new ItemTouchHelper(new SimpleTouchHelperCallbacks(this, 0, ItemTouchHelper.LEFT))
                .attachToRecyclerView(this);
        //set temDocrator
        addItemDecoration(new SimpleItemDecoration());
    }
}

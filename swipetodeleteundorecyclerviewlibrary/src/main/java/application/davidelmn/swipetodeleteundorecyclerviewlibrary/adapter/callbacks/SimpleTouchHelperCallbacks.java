package application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter.callbacks;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter.DeletableRvAdapter;
import application.davidelmn.swipetodeleteundorecyclerviewlibrary.utils.ColorUtils;

/**
 * Created by davide-syn on 9/27/17.
 */

public class SimpleTouchHelperCallbacks extends ItemTouchHelper.SimpleCallback {
    private final RecyclerView recyclerView;

    public SimpleTouchHelperCallbacks(@NonNull RecyclerView rv, int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
        recyclerView = rv;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * if (testAdapter.isUndoOn() && testAdapter.isPendingRemoval(position)) {
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        boolean hasPendingRemoval = ((DeletableRvAdapter)recyclerView.getAdapter())
                .isPendingRemoval(viewHolder.getAdapterPosition());
        return  hasPendingRemoval ? 0 : super.getSwipeDirs(recyclerView, viewHolder);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        ((DeletableRvAdapter) recyclerView.getAdapter()).pendingRemoval(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder.getAdapterPosition() == -1) {
            return;
        }
        // draw red background
        float translationX = dX;

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                translationX = Math.min(-dX, -viewHolder.itemView.getWidth() / 4);
            }

            //red background canvass
            Drawable redBck = ColorUtils.getBackgroundColorDrawable(viewHolder.itemView.getContext());
            redBck.setBounds(viewHolder.itemView.getRight() + (int) dX, viewHolder.itemView.getTop(),
                    viewHolder.itemView.getRight(), viewHolder.itemView.getBottom());
            redBck.draw(c);
            //white background canvass
//            Drawable whiteBck = new ColorDrawable(Color.WHITE);
//            whiteBck.setBounds(viewHolder.itemView.getRight() -viewHolder.itemView.getRight()/4, viewHolder.itemView.getTop(),
//                    viewHolder.itemView.getLeft(), viewHolder.itemView.getBottom());
//            whiteBck.draw(c);
        }
        super.onChildDraw(c, recyclerView, viewHolder, translationX, dY, actionState, isCurrentlyActive);
    }

}
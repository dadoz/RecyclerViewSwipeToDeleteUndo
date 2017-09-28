package application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter.callbacks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.lang.ref.WeakReference;

import application.davidelmn.swipetodeleteundorecyclerviewlibrary.R;
import application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter.DeletableRvAdapter;
/**
 * Created by davide-syn on 9/27/17.
 */

public class SimpleTouchHelperCallbacks extends ItemTouchHelper.SimpleCallback {
    private final RecyclerView recyclerView;
//    private Drawable xMark;
//    private int xMarkMargin;
    private WeakReference<Context> context;

    public SimpleTouchHelperCallbacks(@NonNull RecyclerView rv, int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
        recyclerView = rv;
        context = new WeakReference<>(recyclerView.getContext());
        init();
    }

    /**
     * init view
     */
    private void init() {
//        xMark = ContextCompat.getDrawable(context.get(), R.drawable.ic_clear_24dp);
//        xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
//        xMarkMargin = (int) context.get().getResources().getDimension(R.dimen.ic_clear_margin);
    }

    // not important, we don't want drag & drop
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int position = viewHolder.getAdapterPosition();
        DeletableRvAdapter testAdapter = (DeletableRvAdapter)recyclerView.getAdapter();
        //if (testAdapter.isUndoOn() && testAdapter.isPendingRemoval(position)) {
        return testAdapter.isPendingRemoval(position) ? 0 :
                super.getSwipeDirs(recyclerView, viewHolder);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        int swipedPosition = viewHolder.getAdapterPosition();
        DeletableRvAdapter adapter = (DeletableRvAdapter) recyclerView.getAdapter();
        adapter.pendingRemoval(swipedPosition);
//        boolean undoOn = adapter.isUndoOn();
//        if (undoOn) {
//            adapter.pendingRemoval(swipedPosition);
//        } else {
//            adapter.remove(swipedPosition);
//        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        // not sure why, but this method get's called for viewholder that are already swiped away
        if (viewHolder.getAdapterPosition() == -1) {
            // not interested in those
            return;
        }

        // draw red background
        Drawable background = Utils.getBackgroundColorDrawable(context.get());
        background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        background.draw(c);

//        // draw x mark
//        int itemHeight = itemView.getBottom() - itemView.getTop();
//        int intrinsicWidth = xMark.getIntrinsicWidth();
//        int intrinsicHeight = xMark.getIntrinsicWidth();
//
//        int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
//        int xMarkRight = itemView.getRight() - xMarkMargin;
//        int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
//        int xMarkBottom = xMarkTop + intrinsicHeight;
//        xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);
//
//        xMark.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public static class Utils {
        public static Drawable getBackgroundColorDrawable(Context context) {
            //mv to config - color
            return new ColorDrawable(ContextCompat.getColor(context, R.color.md_red_400));
        }
    }

}
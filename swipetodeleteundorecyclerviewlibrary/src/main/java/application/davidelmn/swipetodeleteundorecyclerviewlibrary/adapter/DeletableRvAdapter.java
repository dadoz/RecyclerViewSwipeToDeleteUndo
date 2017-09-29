package application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.davidelmn.swipetodeleteundorecyclerviewlibrary.utils.ColorUtils;

/**
 * Created by davide-syn on 9/27/17.
 */
public abstract class DeletableRvAdapter<VH extends DeletableVh, T> extends RecyclerView.Adapter<VH> {
    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec TODO move to config
    private HashMap<T, Subscription> pengingSubscriptions = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be
    private List<T> items = new ArrayList<>(); //items
    private Drawable redColor;
    private Drawable whiteColor;

    /**
     * constructor
     * @param items
     * @param context
     */
    protected DeletableRvAdapter(List<T> items, Context context) {
        this.items = items;
        redColor = ColorUtils.getBackgroundColorDrawable(context);
        whiteColor = new ColorDrawable(Color.WHITE);
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(VH viewHolder, int position) {
        T item = items.get(position);
        boolean isDeletedItemInPending = hasDeleteItemInPending(item);
        //set delete button
        viewHolder.setUndoButtonEnabled(v -> handleUndoButtonClick(item), isDeletedItemInPending);
        //set background color
        viewHolder.itemView.setBackground(isDeletedItemInPending ? redColor : whiteColor);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * get items
     * @return
     */
    public List<T> getItems() { return items; }

    /**
     * @param item
     */
    public void handleUndoButtonClick(T item) {
        // user wants to undo the removal, let's cancel the pending task
        Subscription subscription = pengingSubscriptions.get(item);
        if (subscription != null) {
            subscription.removeCallbacks();
            // this will rebind the row in "normal" state
            notifyItemChanged(subscription.getPosition());
            //remove handler
            pengingSubscriptions.remove(item);
        }
    }

    /**
     * pending removal
     * @param position
     */
    public void pendingRemoval(int position) {
        final T item = items.get(position);

        // let's create, store and post a runnable to remove the item
        Handler handler = new Handler(); // hanlder for running delayed runnables
        Runnable runnable = () -> remove(position);
        handler.postDelayed(runnable, PENDING_REMOVAL_TIMEOUT);
        pengingSubscriptions.put(item, new Subscription(handler, runnable, position));

        // this will redraw row in "undo" state
        notifyItemChanged(position);
    }

    /**
     * remove
     * @param position
     */
    public void remove(int position) {
        T item = items.get(position);
        if (items.contains(item)) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     *
     * @param position
     * @return
     */
    public boolean isPendingRemoval(int position) {
        return hasDeleteItemInPending(items.get(position));
    }

    private boolean hasDeleteItemInPending(T item) {
        return pengingSubscriptions.keySet().contains(item);
    }

    private class Subscription {
        private final Handler handler;
        private final Runnable runnable;
        private int position;

        Subscription(Handler handler, Runnable runnable, int position) {
            this.handler = handler;
            this.runnable = runnable;
            this.position = position;
        }

        public Handler getHandler() {
            return handler;
        }

        public Runnable getRunnable() {
            return runnable;
        }

        public void removeCallbacks() {
            handler.removeCallbacks(runnable);
        }

        public int getPosition() {
            return position;
        }
    }
}
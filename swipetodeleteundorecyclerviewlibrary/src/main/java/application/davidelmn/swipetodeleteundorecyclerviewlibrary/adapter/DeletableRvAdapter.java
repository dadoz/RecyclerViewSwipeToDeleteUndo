package application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.davidelmn.swipetodeleteundorecyclerviewlibrary.R;

import static application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter.callbacks.SimpleTouchHelperCallbacks.Utils;

/**
 * Created by davide-syn on 9/27/17.
 */
public abstract class DeletableRvAdapter<VH extends DeletableVh, T> extends RecyclerView.Adapter<VH> {
    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec TODO move to config
    private Handler handler = new Handler(); // hanlder for running delayed runnables
    private HashMap<T, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be
    private List<T> itemsPendingRemoval = new ArrayList<>();//deleted item in pending queue
    private List<T> items = new ArrayList<T>(); //items

    protected DeletableRvAdapter(List<T> items) {
        this.items = items;
    }

    public List<T> getItems() {
        return items;
    }


    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(VH viewHolder, int position) {
        T item = items.get(position);
        //set delete button
        viewHolder.setUndoButtonEnabled(v -> handleDeleteButtonClick(item), hasDeleteItemInPending(item));
        //set color
        viewHolder.itemView.setBackground(hasDeleteItemInPending(item) ?
                Utils.getBackgroundColorDrawable(viewHolder.itemView.getContext()) :
                new ColorDrawable(Color.WHITE));
        viewHolder.itemView.findViewById(R.id.mainViewLayoutContainerId).setVisibility(hasDeleteItemInPending(item) ? View.GONE: View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     *
     * @param item
     */
    public void handleDeleteButtonClick(T item) {
        // user wants to undo the removal, let's cancel the pending task
        Runnable pendingRemovalRunnable = pendingRunnables.get(item);
        pendingRunnables.remove(item);
        if (pendingRemovalRunnable != null) handler.removeCallbacks(pendingRemovalRunnable);
        itemsPendingRemoval.remove(item);
        // this will rebind the row in "normal" state
        notifyItemChanged(items.indexOf(item));
    }

    /**
     * pending removal
     * @param position
     */
    public void pendingRemoval(int position) {
        final T item = items.get(position);
        itemsPendingRemoval.add(item);
        // this will redraw row in "undo" state
        notifyItemChanged(position);
        // let's create, store and post a runnable to remove the item
        Runnable pendingRemovalRunnable = () -> remove(items.indexOf(item));
        handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
        pendingRunnables.put(item, pendingRemovalRunnable);
    }

    /**
     * remove
     * @param position
     */
    public void remove(int position) {
        T item = items.get(position);
        if (hasDeleteItemInPending(item)) {
            itemsPendingRemoval.remove(item);
        }
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
        return itemsPendingRemoval.contains(item);
    }
}
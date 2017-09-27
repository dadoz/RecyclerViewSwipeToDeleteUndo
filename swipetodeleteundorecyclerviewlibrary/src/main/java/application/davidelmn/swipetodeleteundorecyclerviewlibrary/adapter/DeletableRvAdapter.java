package application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter.callbacks.SimpleTouchHelperCallbacks.Utils;

/**
 * Created by davide-syn on 9/27/17.
 */

public abstract class DeletableRvAdapter<VH extends DeletableVh> extends RecyclerView.Adapter<VH> {
    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    private List<String> items = new ArrayList<>();
    private List<String> itemsPendingRemoval = new ArrayList<>();
    private int lastInsertedIndex; // so we can add some more items for testing purposes
    private boolean undoOn = true; // is undo on, you can turn it on from the toolbar menu

    private Handler handler = new Handler(); // hanlder for running delayed runnables
    private HashMap<String, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be

    protected DeletableRvAdapter() {
        // let's generate some items
        lastInsertedIndex = 15;
        // this should give us a couple of screens worth
        for (int i=1; i<= lastInsertedIndex; i++) {
            items.add("Item " + i);
        }
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);
//        return new DeletableVh(parent);

    @Override
    public void onBindViewHolder(VH viewHolder, int position) {
        final String item = items.get(position);
        if (itemsPendingRemoval.contains(item)) {
            removeItemMode(viewHolder, item);
        } else {
            normalMode(viewHolder, item);
        }
    }

    /**
     *
     * @param viewHolder
     */
    protected void removeItemMode(VH viewHolder, final String item) {

        // we need to show the "undo" state of the row
        viewHolder.itemView.setBackground(Utils.getBackgroundColorDrawable(viewHolder.itemView.getContext()));
        viewHolder.getTitleTextView().setVisibility(View.GONE);
        viewHolder.getUndoButton().setVisibility(View.VISIBLE);
        viewHolder.getUndoButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // user wants to undo the removal, let's cancel the pending task
                Runnable pendingRemovalRunnable = pendingRunnables.get(item);
                pendingRunnables.remove(item);
                if (pendingRemovalRunnable != null) handler.removeCallbacks(pendingRemovalRunnable);
                itemsPendingRemoval.remove(item);
                // this will rebind the row in "normal" state
                notifyItemChanged(items.indexOf(item));
            }
        });

    }

    /**
     *
     * @param viewHolder
     * @param item
     */
    private void normalMode(VH viewHolder, String item) {
        // we need to show the "normal" state
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
        viewHolder.getTitleTextView().setVisibility(View.VISIBLE);
        viewHolder.getTitleTextView().setText(item);
        viewHolder.getUndoButton().setVisibility(View.GONE);
        viewHolder.getUndoButton().setOnClickListener(null);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     *  Utility method to add some rows for testing purposes. You can add rows from the toolbar menu.
     */
    public void addItems(int howMany){
        if (howMany > 0) {
            for (int i = lastInsertedIndex + 1; i <= lastInsertedIndex + howMany; i++) {
                items.add("Item " + i);
                notifyItemInserted(items.size() - 1);
            }
            lastInsertedIndex = lastInsertedIndex + howMany;
        }
    }

    public void setUndoOn(boolean undoOn) {
        this.undoOn = undoOn;
    }

    public boolean isUndoOn() {
        return undoOn;
    }

    public void pendingRemoval(int position) {
        final String item = items.get(position);
        if (!itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.add(item);
            // this will redraw row in "undo" state
            notifyItemChanged(position);
            // let's create, store and post a runnable to remove the item
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {
                    remove(items.indexOf(item));
                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(item, pendingRemovalRunnable);
        }
    }

    public void remove(int position) {
        String item = items.get(position);
        if (itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.remove(item);
        }
        if (items.contains(item)) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public boolean isPendingRemoval(int position) {
        String item = items.get(position);
        return itemsPendingRemoval.contains(item);
    }

}
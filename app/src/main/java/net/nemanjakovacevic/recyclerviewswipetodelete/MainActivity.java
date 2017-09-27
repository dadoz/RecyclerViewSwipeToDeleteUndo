package net.nemanjakovacevic.recyclerviewswipetodelete;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import net.nemanjakovacevic.recyclerviewswipetodelete.adapter.DeletableRvAdapter;
import net.nemanjakovacevic.recyclerviewswipetodelete.adapter.callbacks.SimpleTouchHelperCallbacks;
import net.nemanjakovacevic.recyclerviewswipetodelete.adapter.decoration.SimpleItemDecoration;

/**
 * Sample activity demonstrating swipe to remove on recycler view functionality.
 * The interesting parts are drawing while items are animating to their new positions after some items is removed
 * and a possibility to undo the removal.
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        //set toolbar
        setToolbar();
        //set recyclerview
        setUpRecyclerView();
    }

    /**
     * set toolbar action
     */
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recyclerView = null;
    }

    /**
     * This is the standard support library way of implementing "swipe to delete" feature. You can do custom drawing in onChildDraw method
     * but whatever you draw will disappear once the swipe is over, and while the items are animating to their new position the recycler view
     * background will be visible. That is rarely an desired effect.
     *
     * We're gonna setup another ItemDecorator that will draw the red background in the empty space while the items are animating to thier new positions
     * after an item is removed.
     */
    private void setUpRecyclerView() {
        //set recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DeletableRvAdapter(true));
        recyclerView.setHasFixedSize(true);
        //set itemTouchHeler
        SimpleTouchHelperCallbacks callbacks = new SimpleTouchHelperCallbacks(this, recyclerView, 0, ItemTouchHelper.LEFT);
        new ItemTouchHelper(callbacks).attachToRecyclerView(recyclerView);
        //set temDocrator
        recyclerView.addItemDecoration(new SimpleItemDecoration());
    }

}

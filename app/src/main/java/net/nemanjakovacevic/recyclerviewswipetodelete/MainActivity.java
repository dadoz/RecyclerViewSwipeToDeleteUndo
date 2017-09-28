package net.nemanjakovacevic.recyclerviewswipetodelete;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import net.nemanjakovacevic.recyclerviewswipetodelete.adapter.SampleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.davidelmn.swipetodeleteundorecyclerviewlibrary.views.SwipeDeleteRecyclerView;

/**
 *
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set toolbar
        setToolbar();

        //set recyclerview
        initRecyclerView();
    }

    /**
     * set toolbar action
     */
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * set recyclerview
     */
    private void initRecyclerView() {
        SwipeDeleteRecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SampleAdapter<>(retrieveDataStore()));
        recyclerView.setHasFixedSize(true);
    }


    /**
     * get data method
     * @return
     */
    List<String> retrieveDataStore() {
        List<String> list = new ArrayList<>();
        List<String> items = Arrays.asList("blal", "blal", "blal", "blal", "blal", "blal", "blal", "blal", "blal", "blal", "blal", "blal");
        list.addAll(items);
        return list;
    }
}

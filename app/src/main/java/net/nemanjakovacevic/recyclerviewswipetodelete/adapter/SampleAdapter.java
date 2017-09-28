package net.nemanjakovacevic.recyclerviewswipetodelete.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.nemanjakovacevic.recyclerviewswipetodelete.R;

import java.util.List;

import application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter.DeletableRvAdapter;
import application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter.DeletableVh;

/**
 * Created by davide-syn on 9/27/17.
 */

public class SampleAdapter<T> extends DeletableRvAdapter<SampleAdapter.SampleViewHolder, T> {
    public SampleAdapter(List<T> items) {
        super(items);
    }

    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_item, parent, false);
        return new SampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SampleAdapter.SampleViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //set text
        holder.title.setText(getItems().get(position).toString());
    }
    /**
     * custom view holder to do what you like more
     */
    public class SampleViewHolder extends DeletableVh {
        private final TextView title;

        public SampleViewHolder(View parent) {
            super(parent);
            title = itemView.findViewById(R.id.sampleTextViewId);
        }
    }
}

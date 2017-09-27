package application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import application.davidelmn.swipetodeleteundorecyclerviewlibrary.R;

/**
 * Created by davide-syn on 9/27/17.
 */
/**
 * ViewHolder capable of presenting two states: "normal" and "undo" state.
 */

public class DeletableVh extends RecyclerView.ViewHolder {
    private TextView titleTextView;
    private Button undoButton;

    public DeletableVh(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false));
        titleTextView = itemView.findViewById(R.id.title_text_view);
        undoButton = itemView.findViewById(R.id.undo_button);
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public Button getUndoButton() {
        return undoButton;
    }
}

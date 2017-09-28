package application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import application.davidelmn.swipetodeleteundorecyclerviewlibrary.R;

/**
 * Created by davide-syn on 9/27/17.
 */
public class DeletableVh extends RecyclerView.ViewHolder {
    private Button undoButton;

    public DeletableVh(View view) {
        super(LayoutInflater.from(view.getContext()).inflate(R.layout.row_view, (ViewGroup) view.getRootView(), false));
        ((ViewGroup) itemView.findViewById(R.id.mainViewLayoutContainerId)).addView(view);
        undoButton = itemView.findViewById(R.id.undo_button);
    }

    /**
     *
     * @param undoButtonEnabled
     */
    public void setUndoButtonEnabled(View.OnClickListener listener, boolean undoButtonEnabled) {
        undoButton.setVisibility(undoButtonEnabled ? View.VISIBLE : View.GONE);
        undoButton.setOnClickListener(!undoButtonEnabled ? null: listener);
    }
}

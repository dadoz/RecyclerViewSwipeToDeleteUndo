package application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import application.davidelmn.swipetodeleteundorecyclerviewlibrary.R;

/**
 * Created by davide-syn on 9/27/17.
 */
public class DeletableVh extends RecyclerView.ViewHolder {
    private final ViewGroup mainView;
    private Button undoButton;

    public DeletableVh(View view) {
        super(LayoutInflater.from(view.getContext()).inflate(R.layout.row_view, (ViewGroup) view.getRootView(), false));
        mainView = itemView.findViewById(R.id.mainViewLayoutContainerId);
        undoButton = itemView.findViewById(R.id.undo_button);
        mainView.addView(view);
    }

    /**
     *
     * @param undoButtonEnabled
     */
    public void setUndoButtonEnabled(View.OnClickListener listener, boolean undoButtonEnabled) {
        Log.e(getClass().getName(), undoButtonEnabled ? "ENABLE" : "NOT");
        undoButton.setVisibility(undoButtonEnabled ? View.VISIBLE : View.GONE);
        undoButton.setOnClickListener(undoButtonEnabled ? listener : null);
        mainView.setVisibility(undoButtonEnabled ? View.GONE: View.VISIBLE);
//        mainView.setX(undoButtonEnabled ? -mainView.getWidth()/4 : 0);
    }

    public ViewGroup getMainView() {
        return mainView;
    }

    public Button getUndoButton() {
        return undoButton;
    }
}

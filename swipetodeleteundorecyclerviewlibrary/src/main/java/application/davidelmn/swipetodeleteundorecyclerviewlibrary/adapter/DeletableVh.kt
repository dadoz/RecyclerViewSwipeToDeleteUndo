package application.davidelmn.swipetodeleteundorecyclerviewlibrary.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import application.davidelmn.swipetodeleteundorecyclerviewlibrary.R

/**
 * Created by davide-syn on 9/27/17.
 */
open class DeletableVh(view: View) : RecyclerView.ViewHolder(LayoutInflater.from(view.context).inflate(R.layout.row_view, view.rootView as ViewGroup, false)) {
    val mainView: ViewGroup
    val undoButton: Button

    init {
        mainView = itemView.findViewById(R.id.mainViewLayoutContainerId)
        undoButton = itemView.findViewById(R.id.undo_button)
        mainView.addView(view)
    }

    /**
     *
     * @param undoButtonEnabled
     */
    fun setUndoButtonEnabled(listener: View.OnClickListener, undoButtonEnabled: Boolean) {
        Log.e(javaClass.name, if (undoButtonEnabled) "ENABLE" else "NOT")
        undoButton.visibility = if (undoButtonEnabled) View.VISIBLE else View.GONE
        undoButton.setOnClickListener(if (undoButtonEnabled) listener else null)
        mainView.visibility = if (undoButtonEnabled) View.GONE else View.VISIBLE
        //        mainView.setX(undoButtonEnabled ? -mainView.getWidth()/4 : 0);
    }
}

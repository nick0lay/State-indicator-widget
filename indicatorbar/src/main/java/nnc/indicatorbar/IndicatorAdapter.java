package nnc.indicatorbar;

import android.database.DataSetObserver;
import android.view.View;

/**
 * Indicator adapter to work with {@link nnc.statebarwidget.circularprogressbar.view.Indicator}
 */
public interface IndicatorAdapter {
    /**
     * Indicator elements count
     * @return
     */
    int getCount();

    /**
     * Return {@link android.view.View} of each point in indicator
     * @param position
     * @param indicatorView - view that indicator contains
     * @return
     */
    View getView(View indicatorView, int position);

    /**
     * Set an observer that is called when changes happen to the data used by this adapter.
     * @param observer - the object that gets notified when the data set changes.
     */
    void setDataObserver(DataSetObserver observer);

    /**
     * Notify data changed
     */
    void notifyDataChanged();
}

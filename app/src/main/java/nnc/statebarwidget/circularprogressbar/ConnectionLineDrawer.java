package nnc.statebarwidget.circularprogressbar;

import android.graphics.Canvas;
import android.view.View;

/**
 * Interface for connection line drawer. Draw {@link nnc.statebarwidget.circularprogressbar.view.Indicator}
 * connection line.
 */
public interface ConnectionLineDrawer {

    /**
     * Draw connection line between two state view elements
     * @param canvas
     * @param from
     * @param to
     */
    void draw(Canvas canvas, View from, View to);
}

package nnc.statebarwidget.circularprogressbar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Simple implementation of {@link nnc.statebarwidget.circularprogressbar.ConnectionLineDrawer}.
 * This implementation draw straight line between to points with defined style.
 */
public class SimpleConnectionLineDrawer implements ConnectionLineDrawer{
    private final Paint linePaint;

    public SimpleConnectionLineDrawer(float lineWeight,
                                      int lineColor){
        linePaint = new Paint();
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(lineWeight);
        linePaint.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas, View from, View to) {
        int fromXCenter = from.getLeft() + from.getWidth()/2;
        int toXCenter = to.getLeft() + to.getWidth()/2;
        //Considered that view items placed horizontally and share the same y coordinate
        int fromYCenter = from.getTop() + from.getHeight()/2;
        int startX;
        int endX;
        if(fromXCenter < toXCenter){
            startX = fromXCenter + from.getWidth()/2;
            endX = toXCenter - to.getWidth()/2;
        } else {
            startX = fromXCenter - from.getWidth()/2;
            endX = toXCenter + to.getWidth()/2;
        }
        canvas.drawLine(startX, fromYCenter, endX, fromYCenter, linePaint);
    }
}

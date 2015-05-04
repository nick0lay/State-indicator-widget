package nnc.statebarwidget.circularprogressbar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Underlying grey bar implementation
 *
 * The following element model supported
 *
 *         length
 * |<----------------->|
 * |                   |
 * |---|---|---|---|---|
 * 1 tick    ^         6 tick
 *           |
 *        segment
 * in this example 5 segments
 * During calculation process tick tick width don't count.
 */

public class Bar {
    //Paint
    private final Paint barPaint;
    private final Paint activeBarPaint;

    //Coordinates
    private final float leftX;
    private final float rightX;
    private final float y;

    //Segments
    private final int numSegments;
    private float tickDistance;
    private float tickWidth;

    /**
     * Bar constructor
     *
     * @param x - x coordinate of first tick center
     * @param y - y coordinate of first tick center
     * @param length - length of bar from center of first tick to center of last tick
     * @param tickCount
     * @param tickWidth
     * @param barWeight
     * @param activeBarWeight
     * @param barColor
     * @param activeBarColor
     */
    public Bar(float x,
               float y,
               float length,
               int tickCount,
               float tickWidth,
               float barWeight,
               float activeBarWeight,
               int barColor,
               int activeBarColor){

        leftX = x;
        rightX = x + length;
        this.y = y;
        numSegments = tickCount - 1;
        tickDistance = length / numSegments;
        this.tickWidth = tickWidth;

        // Initialize the paint.
        barPaint = new Paint();
        barPaint.setColor(barColor);
        barPaint.setStrokeWidth(barWeight);
        barPaint.setAntiAlias(true);

        activeBarPaint = new Paint();
        activeBarPaint.setColor(activeBarColor);
        activeBarPaint.setStrokeWidth(activeBarWeight);
        activeBarPaint.setAntiAlias(true);
    }

    public void draw(Canvas canvas) {
        for(int i = 0; i < numSegments; i++) {
            canvas.drawLine(getLeftX(i), y, getRightX(i), y, barPaint);
            Log.d("Bar", "Draw bar xLeft - " + getLeftX(i) + " xRight - " + getRightX(i) + " y - " + y);
        }
    }

    private float getLeftX(int segmentIndex){
        float segmentStartX = tickDistance * segmentIndex;
        return leftX + segmentStartX + tickWidth/2;
    }

    private float getRightX(int segmentIndex){
        float segmentEndX = tickDistance * (segmentIndex + 1);
        return leftX + segmentEndX - tickWidth/2;
    }
}

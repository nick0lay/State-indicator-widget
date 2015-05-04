package nnc.statebarwidget.circularprogressbar;

import android.graphics.Rect;
import android.util.Log;

/**
 * Strategy to positioning points inside layout
 *
 *         width
 * |<----------------->|
 * | -   -   -   -   - |
 * |---|---|---|---|---|
 *   -   -   -   -   -
 *           ^
 *           |
 *      point to draw
 *
 * Full width divided into element count equals segment.
 * At the middle of segment center of element will be placed.
 */
public class PointPositionStrategy {
    private final int pointCount;
    private final int numSegments;
    private float segmentLenght;
    private int height = 0;
    private int width = 0;

    public PointPositionStrategy(int pointCount){
        this.pointCount = pointCount;
        this.numSegments = pointCount;
    }

    public void setAvailiableSpace(int height, int width){
        this.height = height;
        this.width = width;
        segmentLenght = width/numSegments;
        Log.d("strategy", "segmentLenght - " + segmentLenght + " calculated for width - " + width + " and numSegments - " + numSegments);
    }

    public Rect getElementRect(int position, int elementWidth, int elementHeight, Rect elementRect){
        float y = height/2;
        float xSegmentCenter = (segmentLenght * (position + 1)) - segmentLenght/2;
        Log.d("strategy", "xSegmentCenter - " + xSegmentCenter + " segmentLenght - " + segmentLenght);
        int xLeft = (int)(xSegmentCenter - elementWidth/2);
        int xRight = (int)(xSegmentCenter + elementWidth/2);
        Log.d("strategy", "xLeft - " + xLeft + " xRight - " + xRight + " for elementWidth - " + elementWidth + " elementHeight - " + elementHeight);
        elementRect.bottom = (int)(y + elementHeight/2);
        elementRect.top = (int)(y - elementHeight/2);
        elementRect.left = xLeft;
        elementRect.right = xRight;
        return elementRect;
    }
}

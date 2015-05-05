package nnc.indicatorbar;

import android.graphics.Rect;

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
public class StatePositionStrategyImpl implements StatePositioningStrategy {
    private int pointCount;
    private int numSegments;
    private float segmentLenght;
    private int height = 0;
    private int width = 0;

    @Override
    public void init(int stateCount, int height, int width) {
        this.pointCount = stateCount;
        this.numSegments = stateCount;
        this.height = height;
        this.width = width;
        segmentLenght = width/numSegments;
    }

    public void getElementRect(int position, int elementWidth, int elementHeight, Rect elementRect){
        float y = height/2;
        float xSegmentCenter = (segmentLenght * (position + 1)) - segmentLenght/2;
        int xLeft = (int)(xSegmentCenter - elementWidth/2);
        int xRight = (int)(xSegmentCenter + elementWidth/2);
        elementRect.bottom = (int)(y + elementHeight/2);
        elementRect.top = (int)(y - elementHeight/2);
        elementRect.left = xLeft;
        elementRect.right = xRight;
    }
}

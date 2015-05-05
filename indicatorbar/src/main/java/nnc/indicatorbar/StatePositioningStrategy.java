package nnc.indicatorbar;

import android.graphics.Rect;

/**
 * Define strategy to place state views inside indicator
 */
public interface StatePositioningStrategy {
    /**
     * Set indicator size to use in {@link StatePositioningStrategy#getElementRect(int, int, int, android.graphics.Rect)}
     * @param height
     * @param width
     */
    void init(int stateCount, int height, int width);

    /**
     * Call {@link StatePositioningStrategy#init(int, int, int)} before using this method
     * @param position
     * @param elementWidth
     * @param elementHeight
     * @param elementRect
     */
    void getElementRect(int position, int elementWidth, int elementHeight, Rect elementRect);
}

package nnc.statebarwidget.circularprogressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Full state bar representation
 */
public class StateBar extends View {

    private int mDefaultWidth = 500;
    private int mDefaultHeight = 150;

    private Bar bar;

    public StateBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width;
        int height;

        // Get measureSpec mode and size values.
        final int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        // The RangeBar width should be as large as possible.
        if (measureWidthMode == MeasureSpec.AT_MOST) {
            width = measureWidth;
        } else if (measureWidthMode == MeasureSpec.EXACTLY) {
            width = measureWidth;
        } else {
            width = mDefaultWidth;
        }

        // The RangeBar height should be as small as possible.
        if (measureHeightMode == MeasureSpec.AT_MOST) {
            height = Math.min(mDefaultHeight, measureHeight);
        } else if (measureHeightMode == MeasureSpec.EXACTLY) {
            height = measureHeight;
        } else {
            height = mDefaultHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float tickWidth = 10.0f;
        float padding = tickWidth/2;
        int tickCount = 6;
        float strokeWidth = 2.0f;
        float activeStrokeWidth = 4.0f;
        int barColor = Color.BLACK;
        int activeBarColor = Color.GREEN;
        float length = w - 2 * padding;

        float x = padding;
        float y = h/2;
        bar = new Bar(x, y, length, tickCount, tickWidth, strokeWidth, activeStrokeWidth, barColor, activeBarColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bar != null){
            bar.draw(canvas);
        }
    }
}

package nnc.statebarwidget.circularprogressbar.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import nnc.statebarwidget.circularprogressbar.ConnectionLineDrawer;
import nnc.statebarwidget.circularprogressbar.IndicatorAdapter;
import nnc.statebarwidget.circularprogressbar.StatePositionStrategyImpl;
import nnc.statebarwidget.circularprogressbar.SimpleConnectionLineDrawer;
import nnc.statebarwidget.circularprogressbar.StatePositioningStrategy;

/**
 * Indicator layout implementation
 * Based on <a href = "http://developer.android.com/reference/android/view/ViewGroup.html>ViewGroup</>
 * implementation example
 */
public class Indicator extends ViewGroup {

    /** The amount of space used by children in the left gutter. */
    private int mLeftWidth;

    /** The amount of space used by children in the right gutter. */
    private int mRightWidth;

    private final Rect mTmpChildRect = new Rect();

    private ArrayList<CircularProgressBar> points = new ArrayList<>();
    private StatePositioningStrategy strategy = new StatePositionStrategyImpl();
    private ConnectionLineDrawer simpleConnectionLineDrawer = new SimpleConnectionLineDrawer(2.0f, Color.BLACK);

    private IndicatorAdapter adapter;
    private DataSetObserver observer = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            updateIndicator();
        }
    };

    public Indicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();

        // These keep track of the space we are using on the left and right for
        // views positioned there; we need member variables so we can also use
        // these for layout later.
        mLeftWidth = 0;
        mRightWidth = 0;

        // Measurement will ultimately be computing these values.
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                // Measure the child.
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                maxWidth = Math.max(maxWidth,
                        child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
                maxHeight = Math.max(maxHeight,
                        child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
                childState = combineMeasuredStates(childState, child.getMeasuredState());
            }
        }

        // Total width is the maximum width of all inner children plus the gutters.
        maxWidth += mLeftWidth + mRightWidth;

        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());
        Log.d("measure", "Width - " + maxWidth + " Height - " + maxHeight);

        // Report our final dimensions.
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();
        strategy.init(count, bottom - top, right - left);

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final int width = child.getMeasuredWidth();
                final int height = child.getMeasuredHeight();

                strategy.getElementRect(i, width, height, mTmpChildRect);
                child.layout(mTmpChildRect.left, mTmpChildRect.top,
                        mTmpChildRect.right, mTmpChildRect.bottom);
            }
        }
    }

    private void updateIndicator() {
        if(adapter == null){
            removeAllViews();
            return;
        }
        int childCount = getChildCount();
        int adapterCount = adapter.getCount();
        if(childCount != adapterCount){
            invalidateViews();
        } else {
            updateViews();
        }
    }

    /**
     * Update view values
     */
    private void updateViews() {
        int adapterCount = adapter.getCount();
        for(int i = 0; i < adapterCount; i++){
            View view = getChildAt(i);
            //Update existing view
            View updatedView = adapter.getView(view, i);
            if(!view.equals(updatedView)){
                throw new  IllegalStateException("Update supplied indicator view instead creating new one");
            }
        }
        invalidate();
    }

    /**
     * Fill in indicator with new view items
     */
    private void invalidateViews() {
        removeAllViews();
        int adapterCount = adapter.getCount();
        for(int i = 0; i < adapterCount; i++){
            View updatedView = adapter.getView(null, i);
            addView(updatedView);
        }
        requestLayout();
    }

    public IndicatorAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(IndicatorAdapter adapter) {
        this.adapter = adapter;
        adapter.setDataObserver(observer);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int childCount = getChildCount();
        if(simpleConnectionLineDrawer != null && childCount > 1){
            for(int i = 0; i < (childCount - 1); i++){
                View childFrom = getChildAt(i);
                View childTo = getChildAt(i + 1);
                simpleConnectionLineDrawer.draw(canvas, childFrom, childTo);
            }
        }
    }

    // ----------------------------------------------------------------------
    // The rest of the implementation is for custom per-child layout parameters.
    // If you do not need these (for example you are writing a layout manager
    // that does fixed positioning of its children), you can drop all of this.

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new Indicator.LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /**
     * Custom per-child layout information.
     */
    public static class LayoutParams extends MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}

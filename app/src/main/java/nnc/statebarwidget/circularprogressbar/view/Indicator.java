package nnc.statebarwidget.circularprogressbar.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import nnc.statebarwidget.circularprogressbar.ConnectionLineDrawer;
import nnc.statebarwidget.circularprogressbar.IndicatorAdapter;
import nnc.statebarwidget.circularprogressbar.StatePositionStrategyImpl;
import nnc.statebarwidget.circularprogressbar.SimpleConnectionLineDrawer;
import nnc.statebarwidget.circularprogressbar.StatePositioningStrategy;

/**
 * Full state bar representation
 */
public class Indicator extends ViewGroup {

    private int mDefaultWidth = 500;
    private int mDefaultHeight = 150;

    /** The amount of space used by children in the left gutter. */
    private int mLeftWidth;

    /** The amount of space used by children in the right gutter. */
    private int mRightWidth;

    /** These are used for computing child frames based on their gravity. */
    private final Rect mTmpContainerRect = new Rect();
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
        initProgressBar(attrs);
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


//        int width;
//        int height;
//
//        // Get measureSpec mode and size values.
//        final int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
//        final int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
//        final int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
//        final int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
//
//        // The RangeBar width should be as large as possible.
//        if (measureWidthMode == MeasureSpec.AT_MOST) {
//            width = measureWidth;
//        } else if (measureWidthMode == MeasureSpec.EXACTLY) {
//            width = measureWidth;
//        } else {
//            width = mDefaultWidth;
//        }
//
//        // The RangeBar height should be as small as possible.
//        if (measureHeightMode == MeasureSpec.AT_MOST) {
//            height = Math.min(mDefaultHeight, measureHeight);
//        } else if (measureHeightMode == MeasureSpec.EXACTLY) {
//            height = measureHeight;
//        } else {
//            height = mDefaultHeight;
//        }
//        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();

        // These are the far left and right edges in which we are performing layout.
        int leftPos = getPaddingLeft();
        int rightPos = right - left - getPaddingRight();

        // This is the middle region inside of the gutter.
        final int middleLeft = leftPos + mLeftWidth;
        final int middleRight = rightPos - mRightWidth;

        // These are the top and bottom edges in which we are performing layout.
        final int parentTop = getPaddingTop();
        final int parentBottom = bottom - top - getPaddingBottom();

        strategy.init(count, bottom - top, right - left);

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();

                final int width = child.getMeasuredWidth();
                final int height = child.getMeasuredHeight();


//                // Compute the frame in which we are placing this child.
//                mTmpContainerRect.left = middleLeft + lp.leftMargin;
//                mTmpContainerRect.right = middleRight - lp.rightMargin;
//                mTmpContainerRect.top = parentTop + lp.topMargin;
//                mTmpContainerRect.bottom = parentBottom - lp.bottomMargin;
//
//                // Use the child's gravity and size to determine its final
//                // frame within its container.
//                Gravity.apply(lp.gravity, width, height, mTmpContainerRect, mTmpChildRect);
//
//                // Place the child.
//                child.layout(mTmpChildRect.left, mTmpChildRect.top,
//                        mTmpChildRect.right, mTmpChildRect.bottom);

                strategy.getElementRect(i, width, height, mTmpChildRect);
                child.layout(mTmpChildRect.left, mTmpChildRect.top,
                        mTmpChildRect.right, mTmpChildRect.bottom);

                Log.d("layout", "Container - " + mTmpChildRect.toString());
            }
        }
//
//        float tickWidth = 10.0f;
//        float padding = tickWidth/2;
//        int tickCount = 6;
//        float strokeWidth = 2.0f;
//        float activeStrokeWidth = 4.0f;
//        int barColor = Color.BLACK;
//        int activeBarColor = Color.GREEN;
//
//        int chilCount = getChildCount();
//        if(chilCount > 0) {
//            View firstChild = getChildAt(0);
//            View lastChild = getChildAt(count - 1);
//            float xCenterFirst = firstChild.getLeft() + (firstChild.getRight() - firstChild.getLeft()) / 2;
//            float xCenterLast = lastChild.getLeft() + (lastChild.getRight() - lastChild.getLeft()) / 2;
//            float length = xCenterLast - xCenterFirst;
//            float x = xCenterFirst;
//            float y = (firstChild.getBottom() - firstChild.getTop()) / 2;
//            tickCount = count;
//            tickWidth = firstChild.getRight() - firstChild.getLeft();
//            connnectionLineDrawer = new ConnnectionLineDrawerImpl(x, y, length, tickCount, tickWidth, strokeWidth, barColor);
//        }
    }

    private void initProgressBar(AttributeSet attrs) {
//        CircularProgressBarWithText point = new CircularProgressBarWithText(getContext());
//        addView(point);
//        CircularProgressBarWithText point1 = new CircularProgressBarWithText(getContext());
//        addView(point1);
//        CircularProgressBarWithText point2 = new CircularProgressBarWithText(getContext());
//        addView(point2);
//        CircularProgressBarWithText point3 = new CircularProgressBarWithText(getContext());
//        addView(point3);
//        CircularProgressBarWithText point4 = new CircularProgressBarWithText(getContext());
//        addView(point4);
//        strategy = new StatePositionStrategyImpl(5);
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
        /**
         * The gravity to apply with the View to which these layout parameters
         * are associated.
         */
        public int gravity = Gravity.TOP | Gravity.START;

        public static int POSITION_MIDDLE = 0;
        public static int POSITION_LEFT = 1;
        public static int POSITION_RIGHT = 2;

        public int position = POSITION_MIDDLE;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            // Pull the layout param values from the layout XML during
            // inflation.  This is not needed if you don't care about
            // changing the layout behavior in XML.
//            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.CustomLayoutLP);
//            gravity = a.getInt(R.styleable.CustomLayoutLP_android_layout_gravity, gravity);
//            position = a.getInt(R.styleable.CustomLayoutLP_layout_position, position);
//            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}

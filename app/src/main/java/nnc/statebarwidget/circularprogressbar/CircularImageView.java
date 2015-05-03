package nnc.statebarwidget.circularprogressbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class CircularImageView extends ImageView {
    private int centerY;
    private int centerX;
    private float strokeWidth = 2.0f;

    private int circleRadius;
    private int ringRadius;
    private Paint circlePaint;
    private Paint ringPaint;
    private int color = Color.DKGRAY;

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setFocusable(false);
        setClickable(true);
        setScaleType(ScaleType.CENTER_INSIDE);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(adjustAlpha(color, 0.3f));
//        circlePaint.setShadowLayer(shadowRadius, radiusX, radiusY, Color.argb(shadowTransparency, 0, 0, 0));
//
        ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ringPaint.setStyle(Paint.Style.STROKE);
        ringPaint.setColor(color);
//        setWillNotDraw(false);
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        int color = Color.BLACK;
//        if (attrs != null) {
//            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
//            color = a.getColor(R.styleable.CircleImageView_android_color, Color.BLACK);
//            ringWidthRatio = a.getFloat(R.styleable.CircleImageView_fbb_progressWidthRatio, ringWidthRatio);
//            a.recycle();
//        }
//        setColor(color);
//        final int pressedAnimationTime = animationDuration;
//        ringAnimator = ObjectAnimator.ofFloat(this, "currentRingWidth", 0f, 0f);
//        ringAnimator.setDuration(pressedAnimationTime);
//        ringAnimator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (fabViewListener != null) {
//                    fabViewListener.onProgressVisibilityChanged(progressVisible);
//                }
//            }
//        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, ringRadius, ringPaint); // the outer ring
        canvas.drawCircle(centerX, centerY, circleRadius, circlePaint); //the actual circle
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int min = Math.min(width, height);
        setMeasuredDimension(min, min);
        centerX = width / 2;
        centerY = height / 2;
        circleRadius = (int) (min/2 - strokeWidth);
//        ringRadius = (int) (circleRadius - strokeWidth/2);
        ringRadius = (int) (circleRadius - strokeWidth);
    }

    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}

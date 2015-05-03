package nnc.statebarwidget.circularprogressbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Circular progress bar with text elements above and below
 */
public class CircularProgressBarWithText extends LinearLayout{
    private CircleProgressBar progressBar;
    private TextView above;
    private TextView below;

    public CircularProgressBarWithText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


}

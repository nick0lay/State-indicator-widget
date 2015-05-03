package nnc.statebarwidget.circularprogressbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import nnc.statebarwidget.R;

/**
 * Circular progress bar with text elements above and below
 */
public class CircularProgressBarWithText extends LinearLayout{
    private CircleProgressBar progressBar;
    private TextView above;
    private TextView below;

    public CircularProgressBarWithText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View v = View.inflate(context, R.layout.circular_progress_bar_with_text, this);
        progressBar = (CircleProgressBar)v.findViewById(R.id.progressCircular);
        above = (TextView)v.findViewById(R.id.textAbove);
        below = (TextView)v.findViewById(R.id.textBelow);
    }


}

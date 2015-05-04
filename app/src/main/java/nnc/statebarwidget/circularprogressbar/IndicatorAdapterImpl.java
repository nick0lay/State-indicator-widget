package nnc.statebarwidget.circularprogressbar;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

import nnc.statebarwidget.R;
import nnc.statebarwidget.circularprogressbar.view.CircularProgressBar;
import nnc.statebarwidget.circularprogressbar.view.CircularProgressBarWithText;

/**
 * {@link nnc.statebarwidget.circularprogressbar.IndicatorAdapter} implementation
 */
public class IndicatorAdapterImpl implements IndicatorAdapter{
    private Context context;
    private DataSetObserver observer;

    public IndicatorAdapterImpl(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public View getView(View indicatorView, int position) {
        Holder holder;
        if(indicatorView == null) {
            holder = new Holder();
//            indicatorView = new CircularProgressBarWithText(context);
            indicatorView = View.inflate(context, R.layout.circular_progress_bar_with_text, null);
            holder.progressBar = (CircularProgressBar) indicatorView.findViewById(R.id.progressCircular);
            holder.above = (TextView) indicatorView.findViewById(R.id.textAbove);
            holder.below = (TextView) indicatorView.findViewById(R.id.textBelow);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            indicatorView.setLayoutParams(layoutParams);
            indicatorView.setTag(holder);
        } else {
            holder = (Holder)indicatorView.getTag();
        }
        holder.above.setText("above");
        holder.below.setText("below");
        int randInt = new Random().nextInt();
        int progress = randInt/Integer.MAX_VALUE * 100;
        holder.progressBar.setProgressWithAnimation(progress);
        return indicatorView;
    }

    @Override
    public void setDataObserver(DataSetObserver observer) {
        this.observer = observer;
    }

    public void notifyDataChanged(){
        if(observer != null){
            observer.onChanged();
        }
    }

    private static final class Holder{
        TextView below;
        TextView above;
        CircularProgressBar progressBar;
    }
}

package nnc.indicatorbar;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import nnc.indicatorbar.view.CircularProgressBar;


/**
 * {@link nnc.indicatorbar.IndicatorAdapter} implementation
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
            indicatorView = View.inflate(context, R.layout.custom_circular_progress_bar, null);
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
        holder.progressBar.setProgressWithAnimation(50);
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

package nnc.statebarwidget;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import nnc.statebarwidget.circularprogressbar.IndicatorAdapterImpl;
import nnc.statebarwidget.circularprogressbar.view.CircularProgressBar;
import nnc.statebarwidget.circularprogressbar.view.Indicator;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private ProgressBar progressBar;
        private SeekBar seekBar;
        private CircularProgressBar circularProgressBar;
        private Button show;
        private Button hide;
        private Indicator indicator;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            indicator = (Indicator) rootView.findViewById(R.id.stateBar);
            progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
            seekBar = (SeekBar) rootView.findViewById(R.id.seekBar);
            circularProgressBar = (CircularProgressBar) rootView.findViewById(R.id.progressCircular);
            show = (Button) rootView.findViewById(R.id.show);
            hide = (Button) rootView.findViewById(R.id.hide);
            show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    circularProgressBar.showProgressBackground();
                }
            });
            hide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    circularProgressBar.hideProgressBackground();
                }
            });
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressBar.setProgress(progress);
                    circularProgressBar.setProgressWithAnimation(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            indicator.setAdapter(new IndicatorAdapterImpl(getActivity()));
            indicator.getAdapter().notifyDataChanged();
            return rootView;
        }
    }
}

package patil.ashish.customprogressbar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import patil.ashish.customprogressview.CustomProgressView;
import patil.ashish.customprogressview.TEXT_SIZE;

public class MainActivity extends AppCompatActivity {


    CustomProgressView customProgressView,customProgressView1,customProgressView2;
    private boolean mStopHandler = false;
    private int p = 0;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customProgressView = findViewById(R.id.custom_progress);
        customProgressView.setProgressType(CustomProgressView.TYPE.BASIC_CIRCULAR);
        customProgressView.setTextSize(TEXT_SIZE.SMALL);
        customProgressView.setTextColor(ContextCompat.getColor(this,R.color.progress_color));

        customProgressView1 = findViewById(R.id.custom_progress1);
        customProgressView1.setProgressType(CustomProgressView.TYPE.DISK_RING);
        customProgressView1.setTextSize(TEXT_SIZE.SMALL);
        customProgressView1.setTextColor(ContextCompat.getColor(this,R.color.progress_color));


        customProgressView2 = findViewById(R.id.custom_progress2);
        customProgressView2.setProgressType(CustomProgressView.TYPE.LINE_PROGRESS);
        customProgressView2.setTextSize(TEXT_SIZE.MEDIUM);
        customProgressView2.setTextColor(ContextCompat.getColor(this,R.color.progress_color));



        SwipeRefreshLayout refresh = findViewById(R.id.refresh);

        refresh.setOnRefreshListener(() -> {
            runProgressView();
            refresh.setRefreshing(false);
        });


        /*

            you can user below methods to set custom colors
         customProgressView.setProgressBackgroundColor(ContextCompat.getColor(this,R.color.light_gray));
         customProgressView.setProgressColor(ContextCompat.getColor(this,R.color.green));
         */
        runProgressView();
    }


    private void runProgressView() {
        p = 0;
        mStopHandler = false;
        // reset progress view
        customProgressView.resetProgress();
        customProgressView1.resetProgress();
        customProgressView2.resetProgress();

        mHandler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                p = p+1;
                if (p == 100) {
                    mStopHandler = true;
                }
                customProgressView.setProgress(p);
                customProgressView.setCenterText(String.valueOf(p));

                customProgressView1.setProgress(p);
                customProgressView1.setCenterText(String.valueOf(p));

                customProgressView2.setProgress(p);
                customProgressView2.setCenterText(String.valueOf(p));

                if (!mStopHandler) {
                    mHandler.post(this);
                }
            }
        };

        mHandler.post(runnable);
    }

}




package patil.ashish.customprogressbar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import patil.ashish.customprogressview.CustomProgressView;
import patil.ashish.customprogressview.TEXT_SIZE;

public class MainActivity extends AppCompatActivity {


    CustomProgressView customProgressView;
    private boolean mStopHandler = false;
    private int p = 0;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customProgressView = findViewById(R.id.custom_progress);
        customProgressView.setTextSize(TEXT_SIZE.MEDIUM);
        customProgressView.setTextColor(ContextCompat.getColor(this,R.color.progress_color));


        /*
            you can user below methods to set custom colors
         customProgressView.setProgressBackgroundColor(ContextCompat.getColor(this,R.color.light_gray));
         customProgressView.setProgressColor(ContextCompat.getColor(this,R.color.green));
         */



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
                if (!mStopHandler) {
                    mHandler.post(this);
                }
            }
        };

        mHandler.post(runnable);
    }
}

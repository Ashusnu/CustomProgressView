package patil.ashish.customprogressview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class CustomProgressView extends View {

    public enum TYPE {
        BASIC_CIRCULAR,
        CIRCULAR_CENTER_TEXT
    }

    private Paint back_circlePaint = new Paint();
    private Paint progress_arc = new Paint();
    private Paint center_txtPaint = new Paint();
    private RectF rectF = new RectF();
    private double progress;
    double one_degree = 3.6;
    private Canvas canvas;
    ValueAnimator animator;
    private int arc_colour = Color.parseColor("#FF00FF");
    private int arc_bg_colour = Color.parseColor("#FBEFFB");

    public CustomProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public CustomProgressView(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attributeSet) {
        back_circlePaint.setStrokeWidth(40f);
        back_circlePaint.setColor(arc_bg_colour);
        back_circlePaint.setStyle(Paint.Style.STROKE);

        progress_arc.setStrokeWidth(40f);
        progress_arc.setColor(arc_colour);
        progress_arc.setStyle(Paint.Style.STROKE);
        progress_arc.setAntiAlias(true);
        progress_arc.setStrokeCap(Paint.Cap.ROUND);

        center_txtPaint.setColor(arc_colour);
        center_txtPaint.setTextSize(TEXT_SIZE.MEDIUM);
        center_txtPaint.setTypeface(Typeface.MONOSPACE);
        center_txtPaint.setAntiAlias(true);

        animator = ValueAnimator.ofInt(0, 100);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public void setProgress(int progress) {
        animator.addUpdateListener(animation -> {
            this.progress = one_degree * progress;
            CustomProgressView.this.postInvalidate();
        });
        animator.start();
    }






    public int getProgrees() {
        return (int) this.progress;
    }

    public void resetProgress() {
        this.setProgress(0);
    }

    public void setProgressColor(int progressColor) {
        this.arc_colour = progressColor;
        progress_arc.setColor(arc_colour);
    }

    public void setProgressBackgroundColor(int progressBackgroundColor) {
        this.arc_bg_colour = progressBackgroundColor;
        back_circlePaint.setColor(arc_bg_colour);
    }






    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        int height = canvas.getHeight() / 2;
        int width = canvas.getWidth() / 2;
        int radius = canvas.getWidth()/5;
        rectF.set(width - radius, height - radius,  width + radius, height + radius);
        canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,canvas.getWidth()/5,back_circlePaint);
        canvas.drawArc(rectF,-90,(float) progress,false,progress_arc);
        canvas.drawText(String.valueOf(Math.round(progress/3.6)),(canvas.getWidth()/2) - 20,(canvas.getHeight()/2)+15,center_txtPaint);
    }


}

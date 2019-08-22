package patil.ashish.customprogressview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class CustomProgressView extends View {

    public enum TYPE {
        BASIC_CIRCULAR,
        DISK_RING,
        LINE_PROGRESS
    }

    private Paint back_circlePaint = new Paint();
    private Paint progress_arc = new Paint();
    private Paint bullet_paint = new Paint();
    private Paint center_txtPaint = new TextPaint();
    private Paint circular_disk_bg_paint = new Paint();

    private Paint progress_line_paint = new Paint();
    private Paint progress_line_bg_paint = new Paint();
    private RectF rectF = new RectF();
    private double progress;
    double one_degree = 3.6;
    private Canvas canvas;
    private ValueAnimator animator;
    private boolean has_bullet = false;
    private String centerText;
    private float handleRadius;
    private float textHeight;
    private float textOffset;
    private int progress_color = Color.parseColor("#F50057");
    private int progress_bg_color = Color.parseColor("#FCE4EC");
    private int textColor = Color.parseColor("#F50057");
    private TYPE current_pb_type = TYPE.BASIC_CIRCULAR;

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
        back_circlePaint.setColor(progress_bg_color);
        back_circlePaint.setStyle(Paint.Style.STROKE);

        circular_disk_bg_paint.setColor(progress_bg_color);
        circular_disk_bg_paint.setStyle(Paint.Style.FILL);

        progress_arc.setStrokeWidth(40f);
        progress_arc.setColor(progress_color);
        progress_arc.setStyle(Paint.Style.STROKE);
        progress_arc.setAntiAlias(true);
        progress_arc.setStrokeCap(Paint.Cap.ROUND);

        bullet_paint.setColor(progress_color);
        bullet_paint.setStyle(Paint.Style.FILL);
        bullet_paint.setAntiAlias(true);


        center_txtPaint.setColor(textColor);
        center_txtPaint.setTextSize(TEXT_SIZE.MEDIUM);
        center_txtPaint.setTextAlign(Paint.Align.CENTER);
        // text attributes
        textHeight = center_txtPaint.descent() - center_txtPaint.ascent();
        textOffset = textHeight / 2 - center_txtPaint.descent();


        progress_line_paint.setColor(progress_color);
        progress_line_paint.setAntiAlias(true);
        progress_line_paint.setStyle(Paint.Style.STROKE);
        progress_line_paint.setStrokeCap(Paint.Cap.ROUND);
        progress_line_paint.setStrokeWidth(40f);

        progress_line_bg_paint.setColor(progress_bg_color);
        progress_line_bg_paint.setAntiAlias(true);
        progress_line_bg_paint.setStyle(Paint.Style.STROKE);
        progress_line_bg_paint.setStrokeCap(Paint.Cap.ROUND);
        progress_line_bg_paint.setStrokeWidth(40f);


        animator = ValueAnimator.ofInt(0, 100);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
    }


    public void setProgressType(TYPE progressType) {
        this.current_pb_type = progressType;
    }

    public void setProgress(int progress) {
        animator.addUpdateListener(animation -> {
          switch (current_pb_type) {
                case BASIC_CIRCULAR:
                    this.progress = one_degree * progress;
                    break;

                case DISK_RING:
                    this.progress = one_degree * progress;
                    break;

                case LINE_PROGRESS:
                    this.progress = progress;
                    break;
            }


            CustomProgressView.this.postInvalidate();
        });
        animator.start();
    }

    public int getProgrees() {
        return (int) this.progress;
    }

    public void resetProgress() {
        this.setProgress(0);
        setCenterText(null);
    }

    public void setProgressColor(int progressColor) {
        this.progress_color = progressColor;
        progress_arc.setColor(progress_color);
    }

    public void setProgressBackgroundColor(int progressBackgroundColor) {
        this.progress_bg_color = progressBackgroundColor;
        back_circlePaint.setColor(progress_bg_color);
    }

    public void setCenterText(String centerText) {
        this.centerText = centerText;
    }

    public void setTextSize(int textSize) {
        center_txtPaint.setTextSize(textSize);
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        center_txtPaint.setColor(textColor);
    }

   /* public void setRoundBullet(boolean need_bullet) {
        this.has_bullet = need_bullet;
    }*/






    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.canvas = canvas;
        float center_height = canvas.getHeight() / 2;
        float center_width = canvas.getWidth() / 2;
        float radius = canvas.getWidth()/3;




        switch (current_pb_type) {
            case BASIC_CIRCULAR:


                handleRadius = radius /10;

                long p = Math.round(progress/3.6);

                rectF.set(center_width - radius, center_height - radius,  center_width + radius, center_height + radius);
                canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,radius,back_circlePaint);
                canvas.drawArc(rectF,-90,(float) progress,false,progress_arc);

                if (centerText != null) {
                    canvas.drawText(centerText,center_width,center_height + textOffset,center_txtPaint);
                }

   /*     if (has_bullet) {
            double bullet_progress = progress/one_degree;

            canvas.drawCircle((float) (center_width + (Math.sin(bullet_progress * 2 * Math.PI) * radius)),
                    (float) (center_height - (Math.cos(bullet_progress * 2 * Math.PI) * radius)),
                    handleRadius,
                    bullet_paint);
        }
*/

                break;

            case DISK_RING:


                rectF.set(center_width - radius, center_height - radius,  center_width + radius, center_height + radius);
                canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,radius,circular_disk_bg_paint);
                canvas.drawArc(rectF,-90,(float) progress,false,progress_arc);

                if (centerText != null) {
                    canvas.drawText(centerText,center_width,center_height + textOffset,center_txtPaint);
                }


                break;

            case LINE_PROGRESS:

                int offset = 30;
                float height = canvas.getHeight();
                float width  = canvas.getWidth();
                float one_percent = width/100;
                float line_progress = (float) (progress * one_percent);

                if (centerText != null) {
                    canvas.drawText(centerText,width/2,(height/2) - progress_line_paint.getStrokeWidth() - offset  + offset,center_txtPaint);
                }

                canvas.drawLine(offset,
                        height/2,width - offset,
                        (height/2),
                        progress_line_bg_paint);

                canvas.drawLine(offset,
                        height/2,line_progress - offset,
                        (height/2),
                        progress_line_paint);

                break;
        }

    }


}

package com.example.analogstopwatch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Calendar;


public class ClockView extends View {

    private int height, width =0;
    private int padding = 0;
    private int fontsize=0;
    private int numeralSpacing =0;
    private int handTruncation;
    private int radius =0;
    private Paint paint;
    private boolean isInit;
    private int[] numbers = {5,10,15,20,25,30,35,40,45,50,55,60};
    private Rect rect = new Rect();
    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initClock(){
        height = getHeight();
        width = getWidth();
        padding = numeralSpacing+50;
        fontsize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,13,getResources().getDisplayMetrics());
        int min =  Math.min(height,width);
        radius = min/2 - padding;
        handTruncation = min/20;
        paint = new Paint();
        isInit = true;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!isInit){
            initClock();
        }

        canvas.drawColor(Color.BLACK);
        drawCircle(canvas,true);
        drawCircle(canvas,false);
        drawCenter(canvas,true);
        drawCenter(canvas,false);
        drawNumeral(canvas,true);
        drawNumeral(canvas,false);
        drawHand(canvas,splashscreen.sec);
        drawMinHand(canvas,splashscreen.min);


       // postInvalidateDelayed(500);
        invalidate();

    }

    private void drawMinHand(Canvas canvas, int min) {
        double angle = Math.PI * min/30 - Math.PI/2;
        int handRadius = (radius - handTruncation)/4;
        canvas.drawLine(width/2, height/(3),(float) (width/2 + Math.cos(angle)*handRadius),(float) (height/3 + Math.sin(angle)*handRadius),paint);
    }


    private void drawHand(Canvas canvas,double loc){


        double angle = Math.PI * loc/30 - Math.PI/2;
        int handRadius = radius - handTruncation;
        canvas.drawLine(width/2,height/2,(float) (width/2 + Math.cos(angle)*handRadius),(float) (height/2 + Math.sin(angle)*handRadius),paint);

    }
    private void drawNumeral(Canvas canvas,boolean isMin){

        for(int number : numbers){
            String tmp = String.valueOf(number);
            paint.getTextBounds(tmp,0,tmp.length(),rect);
            double angle = Math.PI /6 *((int)(number/5)-3);
            if(!isMin) {
                paint.setTextSize(fontsize);
                int x = (int) (width / 2 + Math.cos(angle) * radius - rect.width() / 2);
                int y = (int) (height / 2 + Math.sin(angle) * radius + rect.height() / 2);
                canvas.drawText(tmp, x, y, paint);
            }else{
                paint.setTextSize(fontsize/2);
                int x = (int) (width / 2 + Math.cos(angle) * radius/3.5 - rect.width() / 2);
                int y = (int) (height / 3 + Math.sin(angle) * radius/3.5 + rect.height() / 2);
                canvas.drawText(tmp, x, y, paint);
            }


        }
    }

    private void drawCenter(Canvas canvas, boolean isMin){
        paint.setStyle(Paint.Style.FILL);
        if(!isMin)
        canvas.drawCircle(width/2,height/2,12,paint);
        else
            canvas.drawCircle(width/2,height/3,8,paint);

    }

    private void drawCircle(Canvas canvas,boolean isMin){

        paint.reset();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        if(!isMin)
        canvas.drawCircle(width/2,height/2,radius+padding-10,paint);
        else
            canvas.drawCircle(width/2,height/3,(radius+padding-10)/3,paint);

    }
}

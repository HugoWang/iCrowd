package com.example.kai.icrowd;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;

import android.util.AttributeSet;

import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


public class GestureRelativeLayout extends RelativeLayout {
    
    private float startX, endX = 0;
    private float startY, endY = 0;

    private boolean isDown;//判断是否按下
    private boolean isMoving = false; //按下时的滑动
    private Context context;
    private Paint mPointPaint;
    private Paint mCirclePaint;

    
    
    public GestureRelativeLayout(Context context) {
        this(context, null);
    }

    public GestureRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        setWillNotDraw(false);
        isDown = false;

        mPointPaint = new Paint();
        mPointPaint.setColor(Color.YELLOW);
        mPointPaint.setAntiAlias(true);

        mCirclePaint = new Paint();
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setColor(Color.YELLOW);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStrokeWidth(10);
    }

    Handler m_handler = new Handler();

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                Log.i("TEST","X:"+startX+"");
                Log.i("TEST","Y:"+startY+"");
                break;
            case MotionEvent.ACTION_UP:
                m_handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isMoving = false;
                        if (isDown) {
                            isDown = false;
                            invalidate();
                        }
                    }
                }, 500);
                break;
            case MotionEvent.ACTION_MOVE:
                if(isDown){
                    isMoving = true;
                    
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i("TEST","Drawing");

        if(isDown){
            canvas.drawCircle(startX, startY, 30, mPointPaint);
            if(isMoving){
            		canvas.drawCircle(endX, endY,80,mCirclePaint);
            }else {
                canvas.drawCircle(startX, startY,80,mCirclePaint);
            }

        }else {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        }
    }
}

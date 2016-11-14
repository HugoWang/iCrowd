package com.example.kai.icrowd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Handler;

import android.text.Editable;
import android.util.AttributeSet;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


public class GestureRelativeLayout extends RelativeLayout {
    
    private float startX, endX = 0;
    private float startY, endY = 0;

    private boolean isDown;//判断是否按下
    private Context context;
    private Paint mPointPaint;

    private String objects[];
    private int isDraw = 0;

    private ArrayList<Rect> rectangles = new ArrayList<Rect>();
    
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
        mPointPaint.setStyle(Paint.Style.STROKE);
        mPointPaint.setStrokeWidth( 8 );
        mPointPaint.setColor( Color.RED );
        mPointPaint.setAntiAlias(true);
    }

    Handler m_handler = new Handler();

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                isDown = true;
                Log.i("TEST","X:"+startX+"");
                Log.i("TEST","Y:"+startY+"");
                break;
            case MotionEvent.ACTION_UP:
                endX = event.getX();
                endY = event.getY();

                if(isDraw == 1){
                    if((startX != endX) && (startY != endY)){
                        rectangles.add(new Rect((int)startX, (int)startY, (int)endX, (int)endY));
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        final EditText edittext = new EditText(context);
                        alert.setTitle("What is this?");
                        alert.setMessage( "Please lable the object." );
                        alert.setView(edittext);

                        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                String lableObj = edittext.getText().toString();
                                Log.i("TEST", "objects:"+lableObj);
                            }
                        });

                        alert.show();
                        isDraw = 0;
                    }

                }

                isDown = false;
                invalidate();

//                m_handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (isDown) {
//
//                            invalidate();
//                        }
//                    }
//                }, 500);
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i("TEST","Drawing");
        for (Rect rect : rectangles) {
            canvas.drawRect(rect, mPointPaint);
        }
        Log.i("TEST",rectangles.toString());

        isDraw = 1;
    }
}

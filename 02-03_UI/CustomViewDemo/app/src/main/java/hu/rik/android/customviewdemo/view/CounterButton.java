package hu.rik.android.customviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by Peter on 2015.01.13..
 */
public class CounterButton extends Button {

    private int counter = 0;
    private Paint paintText;

    public CounterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        paintText = new Paint();
        paintText.setColor(Color.RED);
        paintText.setTextSize(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawText(""+counter, 15, 15, paintText);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            counter++;
            invalidate();
        }

        return super.onTouchEvent(event);
    }
}

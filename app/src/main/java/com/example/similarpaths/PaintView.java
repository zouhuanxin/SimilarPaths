package com.example.similarpaths;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 只能画一笔
 */
public class PaintView extends androidx.appcompat.widget.AppCompatTextView {
    private Context context;
    private AttributeSet attributeSet;
    private Paint paint;
    private List<Coordinates> coordinates;

    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (this.context == null) {
            this.context = context;
        }
        if (this.attributeSet == null) {
            this.attributeSet = attrs;
        }
        init();
    }

    private void init() {
        coordinates = new ArrayList<>();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                coordinates = new ArrayList<>();
                coordinates.add(new Coordinates(x, y));
                break;
            case MotionEvent.ACTION_MOVE:
                coordinates.add(new Coordinates(x, y));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //invalidate();
                System.out.println(coordinates.toString());
                break;
        }
        return true;
    }

    /**
     * 返回坐标集合结果
     */
    public String getCoordinates(){
        return coordinates.toString();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setColor(0xff4678ff);
        paint.setStrokeWidth(5f);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < coordinates.size() - 1; i++) {
            canvas.drawLine(coordinates.get(i).getX(), coordinates.get(i).getY(), coordinates.get(i + 1).getX(), coordinates.get(i + 1).getY(), paint);
        }

    }

    class Coordinates {
        float x;
        float y;

        public Coordinates() {

        }

        public Coordinates(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "[" +
                    "" + x +
                    "," + y +
                    ']';
        }
    }
}

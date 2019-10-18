package com.starkinds.glassimmtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class MyCanvas extends View {
    private static final String TAG = "Canvas";
    private Context context;
    public int xTouch = 0;
    public int yTouch = 0;
    private int radius = 15;
    private Bitmap mBackground;
    private Bitmap mCursor;
    private int currentDegree = 0;
    public int oldX = 0;
    public int oldY = 0;

    // CONSTRUCTOR
    public MyCanvas(Context context, int x, int y) {
        super(context);
        this.context = context;
        setFocusable(true);
        mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.map_realistic);
        mCursor = BitmapFactory.decodeResource(getResources(), R.drawable.cursor);
        xTouch = x;
        yTouch = y;
        oldX = x;
        oldY=y;
    }

    public void setLocation(int x, int y) {
        //Uncomment is you use drawable for cursor
        //this.setLeft(x);//2*(x- Prefs.SCREEN_WIDTH/2));
        //this.setTop(y);//2*(y- Prefs.SCREEN_HEIGHT/2) + UtilFunctions.dp_to_pixels(context, 25));
        //this.setRight(x+UtilFunctions.dp_to_pixels(context, radius));
        //this.setBottom(y+UtilFunctions.dp_to_pixels(context, radius));
        oldX = xTouch;
        oldY = yTouch;
        xTouch = x;
        yTouch = y;
    }

    public void setDegree(int degree) {
        currentDegree = degree;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBackground, 0, 0, null);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawBitmap(mBackground, 0, 0, null);
/*
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.argb(50,255,255,0));
        p.setStyle(Paint.Style.FILL);
        canvas.drawCircle(xTouch, yTouch, radius*2, p);

        p.setColor(Color.argb(100,255,255,0));
        canvas.drawCircle(xTouch, yTouch, (int)(radius*1.5), p);

        p.setColor(Color.argb(150,255,255,0));
        canvas.drawCircle(xTouch, yTouch, (int)(radius*1), p);

        p.setColor(Color.argb(200,255,255,0));
        canvas.drawCircle(xTouch, yTouch, (int)(radius*0.5), p);
*/

        Matrix translate = new Matrix();
        //matrix.postTranslate(100, 100);
        //matrix.postRotate(currentDegree);
        int canvasX = xTouch - 40;
        int canvasY = yTouch - 40;
        translate.setTranslate(canvasX+100,canvasY+100);
        //matrix.postTranslate(xTouch - oldX, yTouch - oldY);
        //matrix.setRotate(currentDegree,mCursor.getWidth()/2,mCursor.getHeight()/2);
/*        Matrix rotate = new Matrix();
        if (currentDegree<=190){

        }
        else{
            rotate.setRotate(-(360-currentDegree),mCursor.getWidth()/2,mCursor.getHeight()/2);
        }*/
        Matrix rotate = new Matrix();
        float tmpX = mCursor.getWidth()/2;
        float tmpY = mCursor.getHeight() / 2;
        //Log.d(TAG, "tmpXY: " + tmpX + ", " + tmpY);
        rotate.postTranslate(-tmpX, -tmpY);
        rotate.postRotate(currentDegree);
        rotate.postTranslate(xTouch, yTouch);

        //Matrix combine = new Matrix();
        //combine.postConcat(rotate);
        //combine.postConcat(translate);


        //Log.d(TAG, "CurrentDegree: " + currentDegree);
        //canvas.drawBitmap(mCursor, matrix, null);
        //matrix.setRotate(currentDegree,mCursor.getWidth()/2,mCursor.getHeight()/2);
        canvas.drawBitmap(mCursor, rotate, null);
        //drawArrow(p,canvas,100,100,200,200);

    }

    private void drawArrow(Paint paint, Canvas canvas, float from_x, float from_y, float to_x, float to_y) {
        float angle, anglerad, radius, lineangle;

        //values to change for other appearance *CHANGE THESE FOR OTHER SIZE ARROWHEADS*
        radius = 10;
        angle = 15;

        //some angle calculations
        anglerad = (float) (PI * angle / 180.0f);
        lineangle = (float) (atan2(to_y - from_y, to_x - from_x));

        //tha line
        canvas.drawLine(from_x, from_y, to_x, to_y, paint);

        //tha triangle
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(to_x, to_y);
        path.lineTo((float) (to_x - radius * cos(lineangle - (anglerad / 2.0))),
                (float) (to_y - radius * sin(lineangle - (anglerad / 2.0))));
        path.lineTo((float) (to_x - radius * cos(lineangle + (anglerad / 2.0))),
                (float) (to_y - radius * sin(lineangle + (anglerad / 2.0))));
        path.close();

        canvas.drawPath(path, paint);
    }
}

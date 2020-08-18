package com.GHOSTmHBr.CustomStatusBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.animalsvoice.R;

public class CustomView extends View {


    enum MODE {PERCENT_MODE, NUMBER_MODE}
    MODE status = MODE.NUMBER_MODE;

    protected int backRadius, frontRadius, backColor, frontColor, textColor, number = 75, maxNumber;
    protected float textSize;
    protected int circleX, circleY;
    protected String sNumber;
    protected double percent;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDraw(canvas);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray mArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView, 0, 0);
        backColor = mArray.getColor(R.styleable.CustomView_setBackColor, getResources().getColor(R.color.colorPrimary));
        frontColor = mArray.getColor(R.styleable.CustomView_setFrontColor, getResources().getColor(R.color.textsColor));
        maxNumber = mArray.getInt(R.styleable.CustomView_setMaxNumber, 100);
        textColor = mArray.getColor(R.styleable.CustomView_setTextColor, getResources().getColor(R.color.colorAccent));
        mArray.recycle();
    }


    private void mDraw(Canvas canvas) {

        percent= number * 100.0 / maxNumber;

        circleX=canvas.getWidth()/2;
        circleY=canvas.getHeight()/2;

        backRadius=canvas.getWidth()/2;

        drawBackCircle(canvas);
        drawFrontCircle(canvas);
        drawText(canvas);
    }

    private void drawBackCircle(Canvas canvas){
        double degree=(-percent*3.6);
        RectF rectF=new RectF(canvas.getClipBounds().left,canvas.getClipBounds().top,canvas.getClipBounds().right,canvas.getClipBounds().bottom);

        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(backColor);

        if(degree==-360){
            canvas.drawCircle(circleX, circleY, backRadius, paint);
        }else{
            Path mPath = new Path();
            mPath.moveTo(circleX,circleY);
            mPath.arcTo(rectF,-90,(float)degree,false);
            mPath.lineTo(circleX,circleY);
            mPath.lineTo(circleX,0);
            mPath.close();

            paint.setColor(backColor);
            canvas.drawPath(mPath,paint);
        }

    }
    private void drawFrontCircle(Canvas canvas){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        circleX = canvas.getWidth() / 2;
        circleY = canvas.getHeight() / 2;

        frontRadius = backRadius * 3 / 4;

        paint.setColor(frontColor);
        canvas.drawCircle(circleX, circleY, frontRadius, paint);
    }
    private void drawText(Canvas canvas){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.CENTER);
        textSize = (float) frontRadius * 4 / 5;
        paint.setTextSize(textSize);

        switch (status) {
            case NUMBER_MODE:
                sNumber = String.valueOf(number);
                break;
            case PERCENT_MODE:
                sNumber = (String.valueOf((int)percent) + "%");
                break;
        }
        canvas.drawText(sNumber, circleX, circleY + ((float) textSize / 5 * 2), paint);
    }


    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public void setTextSize(float newSize) {
        textSize = newSize;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setMaxNumber(int newNumber) {
        maxNumber = newNumber;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMode(MODE mode) {
        status = mode;
    }

    public MODE getStatus() {
        return status;
    }

    ;
}

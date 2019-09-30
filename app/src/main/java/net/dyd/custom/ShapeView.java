package net.dyd.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2019, 订易点科技, All rights reserved.
 * -----------------------------------------------------------------
 * CreateDate: 2019/9/30
 * ClassName: ShapeView
 * Author: hk
 * Description: 58加载数据
 * Version:
 */
public class ShapeView extends View {
    private Paint mPaint;
    private Path mPath;

    public final static int Circle = 0x0001;
    public final static int Square = 0x0002;
    public final static int Triangle = 0x0003;

    public int mCurrentShape ;


    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mCurrentShape = Square;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mCurrentShape) {
            case Circle:
                int center = getWidth() / 2;
                mPaint.setColor(getResources().getColor(R.color.circle));
                canvas.drawCircle(center, center, center, mPaint);
                break;
            case Square:
                mPaint.setColor(getResources().getColor(R.color.rect));
                canvas.drawRect(0, 0, getWidth(), getWidth(), mPaint);
                break;
            case Triangle:
                mPaint.setColor(getResources().getColor(R.color.triangle));
                mPath = new Path();
                mPath.moveTo(getWidth() / 2, 0);
                mPath.lineTo(0, (float) (getWidth() / 2 * Math.sqrt(3)));
                mPath.lineTo(getWidth(), (float) (getWidth() / 2 * Math.sqrt(3)));
                mPath.close();
                canvas.drawPath(mPath, mPaint);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mCurrentShape);
        }
    }

    public void exChange() {
        switch (mCurrentShape) {
            case Circle:
                mCurrentShape = Square;
                break;
            case Square:
                mCurrentShape = Triangle;
                break;
            case Triangle:
                mCurrentShape = Circle;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mCurrentShape);
        }
        postInvalidate();

    }
}

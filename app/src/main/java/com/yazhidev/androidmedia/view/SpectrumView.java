package com.yazhidev.androidmedia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zengyazhi on 2018/10/22.
 */

public class SpectrumView extends View {

    private Context mContext;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private byte[] mData;
    private int mLiNeNum = 10;
    private int mMax = 100;

    public SpectrumView(Context context) {
        this(context, null);
    }

    public SpectrumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(mContext);
    }

    private void init(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
    }

    public void setData(byte[] data) {
        mData = data;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mData.length > 0 && mHeight != 0) {
            float xOffset = mWidth * 1F / (2 * mLiNeNum - 1);
            float x = 0;
            int indexOffset = mData.length / mLiNeNum;
            for (int i = 0; i < mData.length; i += indexOffset) {
                canvas.drawRect(x, (float) (mHeight * (1 - mData[i] * 1D / mMax)), x + xOffset, mHeight, mPaint);
                x += xOffset * 2;
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }
}

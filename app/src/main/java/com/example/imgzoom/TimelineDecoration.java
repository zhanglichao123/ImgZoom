package com.example.imgzoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class TimelineDecoration extends RecyclerView.ItemDecoration {


    private int width;//时间轴宽度
    private int top;//圆距离item顶部高度
    private Drawable goingDrawable;//绿色对勾圆
    private int goingDrawableSize;//绿色对勾圆的直径
    private int dividerHeight;//线条粗细

    private int lintColor = 0xff999999;//线条颜色
    private Paint mPaint;

    private int ovalRadius = 12;//灰色圆的半径
    private final Bitmap bitmap;


    public TimelineDecoration(int width, int top, Drawable goingDrawable, int goingDrawableSize, int dividerHeight, Context id) {
        this.width = width;
        this.top = top;
        this.goingDrawableSize = goingDrawableSize;
        this.goingDrawable = goingDrawable;
        this.dividerHeight = dividerHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = BitmapFactory.decodeResource(id.getResources(), R.drawable.ic_launcher);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(width, 0, 0, dividerHeight);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int height = child.getHeight();

            int top = child.getTop();
            int bottom = child.getBottom();
            int ss = bottom - top;
            int fs = (ss - goingDrawableSize) / 2;
            int dd = fs + goingDrawableSize;

            //竖直线
            int left = parent.getPaddingLeft() + width / 2;


            //小圆点
            int ovalCenterX = top + this.top + ovalRadius;

            goingDrawable.setBounds(left - goingDrawableSize / 2, top + fs, left + goingDrawableSize / 2, top + fs + goingDrawableSize);
            c.drawRect(left,
                    i == 0 ? dd : ss,//第一个item线有空一段
                    left + dividerHeight,
                    i == childCount - 1 ? bottom - dd : bottom + dividerHeight,
                    mPaint);

            goingDrawable.draw(c);

            //分割线
            //    mPaint.setColor(lintColor);
            //  c.drawRect(parent.getPaddingLeft() + width + ll, bottom, parent.getWidth() - parent.getPaddingRight(), bottom + dividerHeight, mPaint);


        }

    }
}

package com.example.imgzoom;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class TimeLineItemDecoration extends RecyclerView.ItemDecoration {

    private int mCircleSize = 14;//圆圈的半径
    private Paint mPaint;//画笔
    private int mPaintSize = 6;//画笔宽度
    private String mPaintColor = "#B8B8B8";//画笔默认颜色

    public TimeLineItemDecoration() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(mPaintSize);
        mPaint.setColor(Color.parseColor(mPaintColor));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        Rect rect = new Rect(200, 0, 0, 0);//使item从outRect中右移200px
        outRect.set(rect);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);


    }


    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        int childCount = parent.getChildCount();
        RecyclerView.Adapter adapter = parent.getAdapter();
        int itemCount = adapter.getItemCount();
        long itemId = adapter.getItemId(0);

        int itemDecorationCount = parent.getItemDecorationCount();
        View childAt = parent.getChildAt(itemCount - 1);

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int id = child.getId();
            float y = child.getY();
            int childAdapterPosition = parent.getChildAdapterPosition(child);
            if (id == itemId) {
                Log.e("zh尴尬", id + "==" + itemId + "----" + y + "-=-=-==" + childAdapterPosition);
            }


            int left = child.getLeft();
            int top = child.getTop();
            int bottom = child.getBottom();
            int right = child.getRight();

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int bottomMargin = params.bottomMargin;//防止在item根布局中设置marginTop和marginBottom
            int topMargin = params.topMargin;

            int leftX = left / 2;//轴线的x轴坐标
            int height = child.getHeight();//item的高度，不包含Margin
            mPaint.setColor(Color.parseColor(mPaintColor));
            if (childCount == 1) {
                canvas.drawLine(leftX, top, leftX, bottom - height / 2, mPaint);
            } else {
                if (i == 0 && childAdapterPosition == 0) {
                    canvas.drawLine(leftX, top + height / 2, leftX, bottom + bottomMargin, mPaint);
                } else if (i == childCount - 1 && childAdapterPosition == itemCount - 1) {
                    canvas.drawLine(leftX, top - topMargin, leftX, bottom - height / 2, mPaint);
                } else {
                    canvas.drawLine(leftX, top - topMargin, leftX, bottom - height / 2, mPaint);
                    canvas.drawLine(leftX, top + height / 2, leftX, bottom + bottomMargin, mPaint);
                }
            }
            mPaint.setColor(Color.RED);
            canvas.drawCircle(leftX, top + height / 2, 30, mPaint);


        }
    }


}

package com.example.imgzoom;

import android.app.Notification;
import android.content.Context;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.MediaSyncEvent;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class DewPP extends android.support.v7.widget.AppCompatImageView implements View.OnTouchListener {


    private Paint paint;
    private int dx;
    private int dy;
    private float x;
    private float y;
    private boolean isDwn;
    private float lefts;
    private float tops;
    Matrix mMatrix;

    public DewPP(Context context) {
        this(context, null);
    }

    public DewPP(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DewPP(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
        paint = new Paint();
    }


    private PointF startPoint = new PointF();//PointF(浮点对)
    private Matrix matrix = new Matrix();//矩阵对象
    private Matrix currentMatrix = new Matrix();//存放照片当前的矩阵
    private int mode = 0;//确定是放大还是缩小
    private static final int DRAG = 1;//拖拉模式
    private static final int ZOOM = 2;//缩放模式
    private float startDis;//开始距离
    private PointF midPoint;//中心点

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(4.0f);
        paint.setStyle(Paint.Style.STROKE);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//            int width = bitmap.getWidth() / 2;
//            int height = bitmap.getHeight() / 2;
        canvas.setMatrix(matrix);
        canvas.drawBitmap(bitmap, matrix, paint);
        if (isDwn) {

        }
    }

    //参数1:用户触摸的控件；参数2:用户触摸所产生的事件
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //判断事件的类型
        //得到低八位才能获取动作，所以要屏蔽高八位(通过与运算&255)
        //ACTION_MASK就是一个常量，代表255
        if (event.getPointerCount() == 1) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                float x1 = getX();
                float y1 = getY();
                float x = event.getRawX();
                float y = event.getRawY();
                int left = getLeft();
                int top = getTop();
                lefts = (x - left);
                tops = y - top;
                Matrix matrix = getMatrix();
                mMatrix = null;
                mMatrix = matrix;
                if (lefts > 0 && tops > 0)
                    isDwn = true;
            }
            invalidate();
        } else {
            isDwn = false;
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN://手指下压
                    mode = DRAG;
                    currentMatrix.set(getImageMatrix());//记录ImageView当前的移动位置
                    startPoint.set(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_MOVE://手指在屏幕移动，改事件会不断被调用
                    if (mode == DRAG) {//拖拉模式
                        float dx = event.getX() - startPoint.x;//得到在x轴的移动距离
                        float dy = event.getY() - startPoint.y;//得到在y轴的移动距离
                        matrix.set(currentMatrix);//在没有进行移动之前的位置基础上进行移动
                        //实现位置的移动
                        matrix.postTranslate(dx, dy);
                    } else if (mode == ZOOM) {//缩放模式
                        float endDis = distance(event);//结束距离
                        if (endDis > 10f) {//防止不规则手指触碰
                            //结束距离除以开始距离得到缩放倍数
                            float scale = endDis / startDis;
                            //通过矩阵实现缩放
                            //参数：1.2.指定在xy轴的放大倍数;3,4以哪个参考点进行缩放
                            //开始的参考点以两个触摸点的中心为准
                            matrix.set(currentMatrix);//在没有进行缩放之前的基础上进行缩放
                            matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                        }

                    }

                    break;
                case MotionEvent.ACTION_UP://手指离开屏幕
                case MotionEvent.ACTION_POINTER_UP://当屏幕上已经有手指离开屏幕，屏幕上还有一个手指，就会触发这个事件
                    mode = 0;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN://当屏幕上已经有触点(手指)，再有一个手指按下屏幕，就会触发这个事件
                    mode = ZOOM;
                    startDis = distance(event);
                    if (startDis > 10f) {//防止不规则手指触碰
                        midPoint = mid(event);
                        currentMatrix.set(getImageMatrix());//记录ImageView当前的缩放倍数
                    }
                    break;

                default:
                    break;
            }
        }

        //将imageView的矩阵位置改变
        setImageMatrix(matrix);
        return true;
    }


    //计算两点之间的距离(勾股定理)
    public float distance(MotionEvent event) {
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    //计算两个点的中心点
    public static PointF mid(MotionEvent event) {
        float midx = (event.getX(1) + event.getX(0)) / 2;
        float midy = (event.getY(1) + event.getY(0)) / 2;
        return new PointF(midx, midy);
    }
}

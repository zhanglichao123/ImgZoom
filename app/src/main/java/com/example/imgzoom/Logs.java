package com.example.imgzoom;


import android.content.Context;

import android.graphics.Matrix;
import android.support.v4.widget.ViewDragHelper;

import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import android.view.WindowManager;

import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;


public class Logs extends LinearLayout implements View.OnTouchListener {
    // 屏幕宽高
    private int screenHeight;
    private int screenWidth = getResources().getDisplayMetrics().widthPixels;
    private ViewDragHelper mDragHelper;
    private long lastMultiTouchTime;// 记录多点触控缩放后的时间
    private ScaleGestureDetector mScaleGestureDetector = null;
    // private View view;
    private int downX;//手指按下的x坐标值
    private int downY;//手指按下的y坐标值
    private boolean needToHandle = true;
    private int moveX;
    private int moveY;
    private float scale;//缩放比例
    private float preScale = 1f;// 默认前一次缩放比例为1
    private View inflate;
    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        /**
         * 用于判断是否捕获当前child的触摸事件
         *
         * @param child
         *            当前触摸的子view
         * @param pointerId
         * @return true就捕获并解析；false不捕获
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (preScale > 1)
                return true;
            return false;
        }

        /**
         * 控制水平方向上的位置
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int width = (int) child.getWidth();
            Log.e("zhang", left + "");
            //screenWidth1
            if (left < (screenWidth1 - width * preScale) / 2)
                left = (int) (screenWidth1 - width * preScale) / 2;// 限制mainView可向左移动到的位置

//
//            if (left > (width * preScale - screenWidth1) / 2)
//                left = (int) (width * preScale - screenWidth1) / 2;// 限制mainView可向右移动到的位置

            if (left > 0) left = (int) (1 * preScale);
            return left;
        }

        public int clampViewPositionVertical(View child, int top, int dy) {
            float y = child.getHeight();
            //screenHeight
            if (top < (screenHeight - y * preScale) / 2) {
                top = (int) (screenHeight - y * preScale) / 2;// 限制mainView可向上移动到的位置
            }
            if (top > (y * preScale - screenHeight1) / 2) {
                top = (int) (screenHeight * preScale - y) / 2;// 限制mainView可向上移动到的位置
            }

            return top;
        }


    };
    private int screenWidth1;
    private int screenHeight1;


    public Logs(Context context) {
        this(context, null);
    }

    public Logs(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Logs(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        screenWidth1 = getScreenWidth(context);
        screenHeight1 = getScreenHeight(context);
        inint(context);


    }

    private void inint(Context context) {
        inflate = View.inflate(context, R.layout.layout_to, this);
        mDragHelper = ViewDragHelper.create(this, callback);
        mScaleGestureDetector = new ScaleGestureDetector(context,
                new ScaleGestureListener());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = getMeasuredWidth();
        screenHeight = getMeasuredHeight();
        Log.e("screenWidth", screenWidth + "");
        Log.e("screenHeight", screenHeight + "");
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        boolean b = mDragHelper.shouldInterceptTouchEvent(ev);// 由mDragHelper决定是否拦截事件，并传递给onTouchEvent
        return b;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount(); // 获得多少点
        if (pointerCount > 1) {// 多点触控，
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    needToHandle = false;
                    break;
                case MotionEvent.ACTION_MOVE:

                    break;
                case MotionEvent.ACTION_POINTER_2_UP://第二个手指抬起的时候
                    lastMultiTouchTime = System.currentTimeMillis();
                    needToHandle = true;
                    break;
                default:
                    break;
            }
            return mScaleGestureDetector.onTouchEvent(event);//让mScaleGestureDetector处理触摸事件
        } else {
            //在500ms后解锁控件的左右滑动
            //防止缩放后控件乱动
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - lastMultiTouchTime > 500 && needToHandle) {
                try {
                    //   mDragHelper.processTouchEvent(event);
                } catch (Exception ee) {

                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) event.getX();
                        downY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveX = (int) event.getX();
                        moveY = (int) event.getY();
                        int measuredWidth = getMeasuredWidth();
                        Log.e("zahng", measuredWidth + "");

                        Log.e("fzq", "------move" + (downX - moveX));
                        int xx = moveX - downX;
                        int yy = moveY - downY;

                        // scrollBy(xx, yy);

                        // scrollBy(xx, yy);
                        layout(getLeft() + xx, getTop() + yy, getRight() + xx, getBottom() + yy);


                        downX = moveX;
                        downY = moveY;
                    default:
                        break;
                }
                return true;
            }
        }
        return false;
    }


    public class ScaleGestureListener implements
            ScaleGestureDetector.OnScaleGestureListener {

        private float previousSpan;
        private float currentSpan;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // 前一次双指间距
            previousSpan = detector.getPreviousSpan();
            // 本次双指间距
            currentSpan = detector.getCurrentSpan();
            if (currentSpan < previousSpan) {
                // 缩小
                scale = preScale - (previousSpan - currentSpan) / 1000;
            } else {
                // 放大
                scale = preScale + (currentSpan - previousSpan) / 1000;
            }
            Log.e("scale", scale + "");
            if (scale > 1 && scale < 3) {
                float currentSpanX = detector.getCurrentSpanX();
                Log.e("currentSpanX", currentSpanX + "");
//                setScaleX(scale);
//                setScaleY(scale);
                // 缩放view
                if (scale > 0.5) {
                    ViewHelper.setScaleX(Logs.this, scale);// x方向上缩放
                    ViewHelper.setScaleY(Logs.this, scale);// y方向上缩放
                }

                for (int i = 0; i < getChildCount(); i++) {

//                    getChildAt(i).setScaleX(scale);
//                    getChildAt(i).setScaleY(scale);
//                    int width = getChildAt(i).getWidth();
//                    int height = getChildAt(i).getHeight();
//                    int left = getChildAt(i).getLeft();
//                    getChildAt(i).layout(left + (int) (width * scale), getChildAt(i).getTop() + (int) (width * scale), (int) (width * scale) + getChildAt(i).getRight(), (int) (height * scale) + getChildAt(i).getBottom());


                    // ViewHelper.setScaleY(getChildAt(i), scale);
                    // ViewHelper.setScaleX(getChildAt(i), scale);

//                  lastMultiTouchTime = System.currentTimeMillis();
                }
            }

            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            // 一定要返回true才会进入onScale()这个函数
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            preScale = scale;// 记录本次缩放比例
            lastMultiTouchTime = System.currentTimeMillis();// 记录双指缩放后的时间
        }

    }

    /**
     * 获取屏幕的宽
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }
}

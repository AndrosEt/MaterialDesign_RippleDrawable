package et.materialdesigntest.widget;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/2/14.
 */

public class RippleDrawable extends Drawable {

    float cx = 20, cy = 20, radius = 0;
    private Paint mPaint;
    private int mRippleColor = Color.BLACK;
    private float mAlpha = 100;
    private Context mContext;
    private boolean flag = true;

    public RippleDrawable(Context context) {
        mContext = context;
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        //防止颜色过度抖动
        mPaint.setDither(true);

    }

    public void setRippleColor(int color) {
        mRippleColor = color;
        onColorOrAlphaChange();
    }

    @Override
    public void setAlpha(int i) {
        mAlpha = i;
        onColorOrAlphaChange();
    }

    private void onColorOrAlphaChange() {
        //先设置画笔颜色（包含透明度）
        mPaint.setColor(mRippleColor);
        if (mAlpha != 255) {
            int pAlpha = Color.alpha(mRippleColor);
            //根据drawable的透明度设置画笔颜色的透明度
            mPaint.setAlpha((int) (pAlpha * (mAlpha / 255f)));
        }
    }

    @Override
    public void draw(Canvas canvas) {
        //画一个圆
        canvas.drawCircle(cx, cy, radius, mPaint);
    }

    public void onTouchChange(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
    }

    /**
     * 用户按下处理
     *
     * @param x
     * @param y
     */
    private void touchDown(float x, float y) {
        //判断是否是第一次进来
        if(flag){
            cx = x;
            cy = y;
            radius = 0;
        }else{
            radius += 1;
            flag = false;
        }
        int maxX = getBounds().centerX() * 2;
        ValueAnimator animator = ValueAnimator.ofFloat(0,maxX);
        animator.setTarget(this);
        animator.setDuration(maxX / 1).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radius = (float) animation.getAnimatedValue();
                invalidateSelf();
            }
        });

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        if (mPaint.getColorFilter() != colorFilter) {
            mPaint.setColorFilter(colorFilter);
        }
    }

    @Override
    public int getOpacity() {
        int alpha = mPaint.getAlpha();
        if (alpha == 255) {
            return PixelFormat.OPAQUE;
        } else if (alpha == 0) {
            return PixelFormat.TRANSPARENT;
        } else {
            return PixelFormat.TRANSLUCENT;
        }
    }
}

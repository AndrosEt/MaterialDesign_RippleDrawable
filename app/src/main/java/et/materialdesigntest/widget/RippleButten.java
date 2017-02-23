package et.materialdesigntest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by Administrator on 2017/2/14.
 */

public class RippleButten extends Button {
    RippleDrawable mRippleDrawable;

    public RippleButten(Context context) {
        this(context,null);
    }

    public RippleButten(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RippleButten(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRippleDrawable = new RippleDrawable(context);
        //设置刷新接口，view中已经实现
        mRippleDrawable.setCallback(this);

        mRippleDrawable.setRippleColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //设置drawable绘制和刷新的区域
        mRippleDrawable.setBounds(0,0,getWidth(),getHeight());
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        //验证drawable
        return who == mRippleDrawable||super.verifyDrawable(who);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRippleDrawable.draw(canvas);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mRippleDrawable.onTouchChange(event);
        return super.onTouchEvent(event);
    }
}

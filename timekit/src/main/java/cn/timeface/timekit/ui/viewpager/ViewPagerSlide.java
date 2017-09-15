package cn.timeface.timekit.ui.viewpager;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ray on 2017/2/15.
 */

/**
 * 可以控制viewpager是否允许滑动
 */
public class ViewPagerSlide extends ViewPager {
    public static final int ALL = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int NONE = 3;

    @IntDef({ALL, LEFT, RIGHT, NONE})
    public @interface DIRECTION {
    }

    private int direction = ALL;
    private float initialXValue;

    public ViewPagerSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerSlide(Context context) {
        super(context);
    }

    public void canScroll(boolean canScroll) {
        direction = canScroll ? ALL : NONE;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.IsSwipeAllowed(event) && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.IsSwipeAllowed(event) && super.onInterceptTouchEvent(event);
    }

    private boolean IsSwipeAllowed(MotionEvent event) {
        if (this.direction == ALL) return true;

        if (direction == NONE)//disable any swipe
            return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            initialXValue = event.getX();
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            try {
                float diffX = event.getX() - initialXValue;
                if (diffX > 0 && direction == RIGHT) {
                    // swipe from left to right detected
                    return false;
                } else if (diffX < 0 && direction == LEFT) {
                    // swipe from right to left detected
                    return false;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return true;
    }

    public void setAllowedSwipeDirection(@DIRECTION int direction) {
        this.direction = direction;
    }

}

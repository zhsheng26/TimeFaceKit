package cn.timeface.timekit.ui.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 禁止左右滑动的viewPager
 *
 * @author yusen (QQ:1522289706)
 * @from 2014年9月28日上午9:47:52
 * 禁止左右滑动的viewPager
 */
public class ViewPagerNoScroll extends ViewPager {
    private boolean isCanScroll = false;

    public ViewPagerNoScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerNoScroll(Context context) {
        super(context);
    }

    public void canScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (isCanScroll || arg0.getAction() != MotionEvent.ACTION_MOVE) {
            return super.onTouchEvent(arg0);
        } else {
            return false;
        }

    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isCanScroll || arg0.getAction() != MotionEvent.ACTION_MOVE) {
            return super.onInterceptTouchEvent(arg0);
        } else {
            return false;
        }

    }

}

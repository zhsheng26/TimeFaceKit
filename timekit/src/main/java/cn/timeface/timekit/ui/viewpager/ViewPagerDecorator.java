package cn.timeface.timekit.ui.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * @author wxw
 * @from 2016/3/24
 * 用于解决滑动冲突 可以设置该ViewPager的touch事件是否可以被父View拦截
 */
public class ViewPagerDecorator extends ViewPager {

    private ViewGroup parent;
    private boolean intercepted; //该ViewPager的touch事件是否被父View拦截(不被拦截时该ViewPager可以自由滑动)

    public ViewPagerDecorator(Context context) {
        super(context);
    }

    public ViewPagerDecorator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNestedParent(ViewGroup parent) {
        this.parent = parent;
    }

    /**
     * 该ViewPager的touch事件是否被父View拦截
     *
     * @param intercepted 值为false时，该ViewPager可以自由滑动
     */
    public void setIntercepted(boolean intercepted) {
        this.intercepted = intercepted;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!intercepted && parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);//如果为true表示父view不拦截当前ViewPager的touch事件
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (!intercepted && parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (!intercepted && parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(arg0);
    }

}

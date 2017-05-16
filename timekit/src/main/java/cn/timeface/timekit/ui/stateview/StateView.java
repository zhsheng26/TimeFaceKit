package cn.timeface.timekit.ui.stateview;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ScrollingView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import cn.timeface.timekit.R;

public class StateView extends LinearLayout {

    public static StateView inject(@NonNull Activity activity) {
        return inject(activity, false);
    }

    public static StateView inject(@NonNull View view) {
        return inject(view, false);
    }

    public static StateView inject(@NonNull View view, boolean hasActionBar) {
        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            return inject(parent, hasActionBar);
        } else {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                return inject((ViewGroup) parent, hasActionBar);
            } else {
                throw new ClassCastException("view or view.getParent() must be ViewGroup");
            }
        }
    }

    public static StateView inject(@NonNull Activity activity, boolean hasActionBar) {
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        return inject(rootView, hasActionBar);
    }

    public static StateView inject(@NonNull ViewGroup parent) {
        return inject(parent, false);
    }

    public static StateView inject(@NonNull ViewGroup parent, boolean hasActionBar) {

        int screenHeight = 0;
        if (parent instanceof LinearLayout ||
                parent instanceof ScrollView ||
                parent instanceof AdapterView ||
                (parent instanceof NestedScrollingParent && parent instanceof NestedScrollingChild)) {
            ViewParent viewParent = parent.getParent();
            //整个思路就是在要注入的布局外面加入一层FrameLayout
            if (viewParent == null) {
                // create a new FrameLayout to wrap StateView and parent's childView
                FrameLayout wrapper = new FrameLayout(parent.getContext());
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                wrapper.setLayoutParams(layoutParams);

                if (parent instanceof LinearLayout) {
                    // create a new LinearLayout to wrap parent's childView
                    LinearLayout wrapLayout = new LinearLayout(parent.getContext());
                    wrapLayout.setLayoutParams(parent.getLayoutParams());
                    wrapLayout.setOrientation(((LinearLayout) parent).getOrientation());

                    for (int i = 0, childCount = parent.getChildCount(); i < childCount; i++) {
                        View childView = parent.getChildAt(i);
                        parent.removeView(childView);
                        wrapLayout.addView(childView);
                    }
                    wrapper.addView(wrapLayout);
                } else if (parent instanceof ScrollView || parent instanceof ScrollingView) {
                    // not recommended to inject Scrollview/NestedScrollView
                    if (parent.getChildCount() != 1) {
                        throw new IllegalStateException("the ScrollView does not have one direct child");
                    }
                    View directView = parent.getChildAt(0);
                    parent.removeView(directView);
                    wrapper.addView(directView);

                    WindowManager wm = (WindowManager) parent.getContext()
                            .getSystemService(Context.WINDOW_SERVICE);
                    DisplayMetrics metrics = new DisplayMetrics();
                    wm.getDefaultDisplay().getMetrics(metrics);
                    screenHeight = metrics.heightPixels;
                } else if (parent instanceof NestedScrollingParent &&
                        parent instanceof NestedScrollingChild) {
                    if (parent.getChildCount() == 2) {
                        View targetView = parent.getChildAt(1);
                        parent.removeView(targetView);
                        wrapper.addView(targetView);
                    } else if (parent.getChildCount() > 2) {
                        throw new IllegalStateException("the view is not refresh layout? view = "
                                + parent.toString());
                    }
                } else {
                    throw new IllegalStateException("the view does not have parent, view = "
                            + parent.toString());
                }
                // parent add wrapper
                parent.addView(wrapper);
                // StateView will be added to wrapper
                parent = wrapper;
            }
//            } else {
//                FrameLayout root = new FrameLayout(parent.getContext());
//                root.setLayoutParams(parent.getLayoutParams());
//                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                parent.setLayoutParams(layoutParams);
//
//                if (viewParent instanceof ViewGroup) {
//                    ViewGroup rootGroup = (ViewGroup) viewParent;
//                    rootGroup.removeView(parent);
//                    rootGroup.addView(root);
//                }
//                root.addView(parent);
//                parent = root;
//            }
        }
        StateView stateView = new StateView(parent.getContext());
        if (screenHeight > 0) {
            // let StateView be shown in the center
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    hasActionBar ? screenHeight - stateView.getActionBarHeight() : screenHeight);
            parent.addView(stateView, params);
        } else {
            parent.addView(stateView);
        }
        if (hasActionBar) {
            stateView.setTopMargin();
        }
        return stateView;
    }


    public StateView(Context context) {
        this(context, null);
    }

    public StateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    public void setTopMargin() {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        layoutParams.topMargin = getActionBarHeight();
    }

    public int getActionBarHeight() {
        int height = 0;
        TypedValue tv = new TypedValue();
        if (getContext().getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            height = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        return height;
    }

}

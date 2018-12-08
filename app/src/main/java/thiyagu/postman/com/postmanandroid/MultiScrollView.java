package thiyagu.postman.com.postmanandroid;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

public class MultiScrollView extends RecyclerView implements NestedScrollingParent {
    private View nestedScrollTarget;
    private boolean nestedScrollTargetIsBeingDragged;
    private boolean nestedScrollTargetWasUnableToScroll;
    private boolean skipsTouchInterception;

    public MultiScrollView(Context context) {
        super(context);
    }

    public MultiScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiScrollView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean temporarilySkipsInterception = this.nestedScrollTarget != null;
        if (temporarilySkipsInterception) {
            this.skipsTouchInterception = true;

        }

        boolean handled = super.dispatchTouchEvent(ev);
        if (temporarilySkipsInterception) {
            this.skipsTouchInterception = false;
            if (!handled || this.nestedScrollTargetWasUnableToScroll) {
                handled = super.dispatchTouchEvent(ev);
            }
        }

        return handled;
    }

    public boolean onInterceptTouchEvent(MotionEvent e) {
        return !this.skipsTouchInterception && super.onInterceptTouchEvent(e);
    }

    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (target == this.nestedScrollTarget && !this.nestedScrollTargetIsBeingDragged) {
            if (dyConsumed != 0) {
                this.nestedScrollTargetIsBeingDragged = true;
                this.nestedScrollTargetWasUnableToScroll = false;
            } else if (dyConsumed == 0 && dyUnconsumed != 0) {
                this.nestedScrollTargetWasUnableToScroll = true;
                ViewParent viewParent = target.getParent();
                if (viewParent != null) {
                    viewParent.requestDisallowInterceptTouchEvent(false);
                }
            }
        }

    }

    public void onNestedScrollAccepted(View child, View target, int axes) {
        if ((axes & 2) != 0) {
            this.nestedScrollTarget = target;
            this.nestedScrollTargetIsBeingDragged = false;
            this.nestedScrollTargetWasUnableToScroll = false;
        }

        super.onNestedScrollAccepted(child, target, axes);
    }

    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & 2) != 0;
    }

    public void onStopNestedScroll(View child) {
        this.nestedScrollTarget = (View)null;
        this.nestedScrollTargetIsBeingDragged = false;
        this.nestedScrollTargetWasUnableToScroll = false;
    }
}
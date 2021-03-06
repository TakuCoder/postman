package thiyagu.postman.com.postmanandroid.CustomViews;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;

import java.util.Calendar;

import thiyagu.postman.com.postmanandroid.R;


public class MaterialBetterSpinner extends MaterialAutoCompleteTextView implements AdapterView.OnItemClickListener {

    private static final int MAX_CLICK_DURATION = 300;
    private long startClickTime;
    private boolean isPopup;

    public MaterialBetterSpinner(Context context) {
        super(context);
        setOnItemClickListener(this);
    }

    public MaterialBetterSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        setOnItemClickListener(this);
    }

    public MaterialBetterSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
        setOnItemClickListener(this);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            performFiltering("", 0);
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
            setKeyListener(null);
            dismissDropDown();
        } else {
            isPopup = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled())
            return false;

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                {
                    Log.v("whereisclick","here");
                startClickTime = Calendar.getInstance().getTimeInMillis();
                break;
            }
            case MotionEvent.ACTION_UP:
                {
                    Log.v("whereisclick","here1");
                long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                if (clickDuration < MAX_CLICK_DURATION) {
                    if (isPopup) {
                        Log.v("whereisclick","here2");
                        dismissDropDown();
                        isPopup = false;
                        requestFocus();
                        showDropDown();
                        isPopup = true;
                    } else {
                        Log.v("whereisclick","here3");
                        requestFocus();
                        showDropDown();
                        isPopup = true;
                    }
                }
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        isPopup = false;
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        Drawable dropdownIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_expand_more_black_18dp);
        if (dropdownIcon != null) {
            right = dropdownIcon;
            right.mutate().setAlpha(66);
        }
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }
    @Override
    public boolean performClick() {
        super.performClick();
        //doSomething();
        return true;
    }

    private void doSomething() {
        Toast.makeText(getContext(), "did something", Toast.LENGTH_SHORT).show();
    }

}

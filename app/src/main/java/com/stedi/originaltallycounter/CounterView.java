package com.stedi.originaltallycounter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

/**
 * TextView which is responsible for drawing count
 */
public class CounterView extends TextView {
    private int count;

    public CounterView(Context context) {
        this(context, null);
    }

    public CounterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(getResources().getColor(R.color.counter_background));
        setTypeface(Typeface.DEFAULT_BOLD);
        setShadowLayer(10f, 15f, 15f, getResources().getColor(R.color.material_shadow));
        setTextColor(Color.WHITE);
        setGravity(Gravity.CENTER);
        drawCount();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getMeasuredWidth() / 3);
    }

    public void countUp() {
        setCount(++count);
    }

    public void reset() {
        setCount(0);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        if (this.count < 0 || this.count > 9999)
            this.count = 0;
        drawCount();
    }

    private void drawCount() {
        String s = "000" + count;
        if (s.length() > 4)
            s = s.substring(s.length() - 4);
        setText(s);
    }
}

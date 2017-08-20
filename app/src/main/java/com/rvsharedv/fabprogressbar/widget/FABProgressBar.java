package com.rvsharedv.fabprogressbar.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.rvsharedv.ringtonemanagerbyrvsharedv.R;


/*
 * Copyright (C) 2017 RVsharedv [ http://rvsharedv.com ]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@CoordinatorLayout.DefaultBehavior(Behavior.class)
public class FABProgressBar extends FrameLayout{

    private static final int FAB_COLOR = Color.parseColor("#c0c0c0");

    private static final int PROGRESS_COLOR = Color.parseColor("#4e387e");

    private int fabColor, progressColor;

    private FloatingActionButton mFloatingActionButton;

    private ProgressBar mProgressBar;

    private Context mContext;

    public FABProgressBar(Context context) {
        this(context, null);
    }

    public FABProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FABProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initStyle(attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FABProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initStyle(attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.RV_FloatingActionButton);
        mProgressBar = (ProgressBar) findViewById(R.id.RV_ProgressBar);

        if(mFloatingActionButton == null || mProgressBar == null){
            throw new IllegalStateException("FABProgressBar childs not specified.");
        }

        setFABColorFilter(fabColor, PorterDuff.Mode.SRC_ATOP);
        setProgressBarColorFilter(progressColor, PorterDuff.Mode.SRC_ATOP);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setGravity();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setGravity();
    }

    public void setFABImageResources(int resId){
        if(mFloatingActionButton != null){
            mFloatingActionButton.setImageResource(resId);
        }
    }

    /**
     * Show FABProgress Bar.
     */
    public void show(){
        try{
            setVisibility(VISIBLE);
        }finally{
            if(mFloatingActionButton != null){
                mFloatingActionButton.show();
            }
            if(mProgressBar != null){
                mProgressBar.setVisibility(VISIBLE);
            }
        }
    }

    /**
     * Hide FABProgress Bar.
     */
    public void hide(){
        if(mProgressBar != null){
            mProgressBar.setVisibility(GONE);
        }
        if(mFloatingActionButton != null){
            mFloatingActionButton.hide(mOnVisibilityChangedListener);
        }
    }

    private FloatingActionButton.OnVisibilityChangedListener mOnVisibilityChangedListener =
            new FloatingActionButton.OnVisibilityChangedListener() {

        @Override
        public void onShown(FloatingActionButton fab) {
            super.onShown(fab);
        }

        @Override
        public void onHidden(FloatingActionButton fab) {
            super.onHidden(fab);
            setVisibility(GONE);
        }
    };

    /**
     * set progress bar maximum
     * @param max
     */
    public void setProgressMax(int max){
        if(mProgressBar != null){
            mProgressBar.setMax(max);
        }
    }

    /**
     * set current progress
     * @param progress
     */
    public synchronized void setProgress(int progress){
        if(mProgressBar != null){
            mProgressBar.setProgress(progress);
        }
    }

    /**
     * set FAB Background color or ColorFilter
     * @param color
     * @param mode
     */
    public void setFABColorFilter(int color, PorterDuff.Mode mode){
        if(mFloatingActionButton != null){
            mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(color));
            mFloatingActionButton.setBackgroundTintMode(mode);

            Drawable mDrawable = mFloatingActionButton.getDrawable();
            if(mDrawable != null){
                mDrawable.setColorFilter(getColorByLuminance(color), mode);
            }
        }
    }

    /**
     * set ProgressBar background color or background tint
     * @param color
     * @param mode
     */
    public void setProgressBarColorFilter(int color, PorterDuff.Mode mode){

        if(mProgressBar != null){
            Drawable mDrawable = mProgressBar.getProgressDrawable();
            if(mDrawable != null)
                mDrawable.setColorFilter(color, mode);

            Drawable mDrawable2 = mProgressBar.getIndeterminateDrawable();
            if(mDrawable2 != null)
                mDrawable2.setColorFilter(color, mode);
        }
    }

    private void setGravity(){

        if(mFloatingActionButton != null){
            LayoutParams mLayoutParams = (LayoutParams) mFloatingActionButton.getLayoutParams();
            mLayoutParams.gravity = Gravity.CENTER;
        }

        if(mProgressBar != null){
            LayoutParams mLayoutParams2 = (LayoutParams) mProgressBar.getLayoutParams();
            mLayoutParams2.gravity = Gravity.CENTER;
        }

    }

    /**
     *
     * gets white/dark color using FAB background color.
     * @return
     */
    public int getColorByLuminance(int fabColor){

        int R = Color.red(fabColor);
        int G = Color.green(fabColor);
        int B = Color.blue(fabColor);

        //formula have copied from wikipedia
        //link ====== https://en.wikipedia.org/wiki/Luma_(video)
        //licensed under Creative Commons Attribution-ShareAlike License;
        //link ====== https://en.wikipedia.org/wiki/Wikipedia:Text_of_Creative_Commons_Attribution-ShareAlike_3.0_Unported_License
        int Y = (int)(0.2126 * R + 0.7152 * G + 0.0722 * B);

        if(Y > 128){
            return Color.BLACK;
        }

        return Color.WHITE;

    }

    private void initStyle(AttributeSet attrs, int defStyleAttr){

        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.RV_FABProgressBar, defStyleAttr, 0);

        fabColor = array.getColor(R.styleable.RV_FABProgressBar_rv_FabColor, FAB_COLOR);
        progressColor = array.getColor(R.styleable.RV_FABProgressBar_rv_progressColor, PROGRESS_COLOR);

        array.recycle();
    }

}

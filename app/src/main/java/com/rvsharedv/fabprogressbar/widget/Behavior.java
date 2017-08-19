package com.rvsharedv.fabprogressbar.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * Currently AppBarLayout and BottomSheetBehavior are not supported only SnackbarLayout is supported
 */
public class Behavior extends CoordinatorLayout.Behavior<FABProgressBar>{

    public Behavior() {
        super();
    }

    public Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FABProgressBar child, View dependency) {
        if(dependency instanceof Snackbar.SnackbarLayout){
            return true;
        }
        return false;
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, FABProgressBar child, View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
        child.setTranslationY(0);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FABProgressBar child, View dependency) {
        if(dependency instanceof Snackbar.SnackbarLayout){
            float translate = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
            if(child.getBottom() > dependency.getTop()){
                child.setTranslationY(translate);
            }
            return true;
        }
        return super.onDependentViewChanged(parent, child, dependency);
    }
}

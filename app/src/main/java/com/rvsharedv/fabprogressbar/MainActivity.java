package com.rvsharedv.fabprogressbar;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.rvsharedv.fabprogressbar.widget.FABProgressBar;

import java.util.Random;

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
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fab_progress_bar);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.RV_Toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("FABProgress Bar");

        setSupportActionBar(mToolbar);

        FABProgressBar mFabProgressBar = (FABProgressBar) findViewById(R.id.RV_FAB_ProgressBar);
        mFabProgressBar.setProgressMax(100);
        mFabProgressBar.setProgress(40);
        mFabProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(findViewById(R.id.RV_CoordinateLayout), "FAB Progress Bar", Snackbar.LENGTH_SHORT).show();
            }
        });

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.RV_RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerAdapter(this));

        ViewCompat.setNestedScrollingEnabled(mRecyclerView, false);

    }

    private static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

        private int i = 100;

        private Random mRandom;

        private Context mContext;

        RecyclerAdapter(Context mContext){
            this.mContext = mContext;
            mRandom = new Random();
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View mView = LayoutInflater.from(mContext).
                    inflate(R.layout.recycler_list_item_one, parent, false);

            return new RecyclerViewHolder(mView);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            holder.mAppCompatTextView.setText(String.valueOf(mRandom.nextInt(10000000)));
        }

        @Override
        public int getItemCount() {
            return i;
        }

    }

    private static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout mRelativeLayout;

        AppCompatTextView mAppCompatTextView;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.RV_RelativeLayout);
            mAppCompatTextView = (AppCompatTextView) itemView.findViewById(R.id.RV_TextView);
        }
    }

}

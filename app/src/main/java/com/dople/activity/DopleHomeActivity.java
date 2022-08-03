package com.dople.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dople.R;

public class DopleHomeActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mSearchText;

    private LinearLayout mTopSwimming, mTopRunning, mTopOutdoor, mRecommended, mGoodReview;
    private TextView mTabFavorite, mTabRecommended, mTabGooReview;
    private int currentIndex;

    private TextView mTopBtnSwimming, mTopBtnRunning, mTopBtnOutdoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dople_home);

        currentIndex = 0;
        initView();
    }

    private void initView() {
        ImageView mUserBtn = (ImageView) findViewById(R.id.dople_home_user_btn);
        mUserBtn.setColorFilter(Color.parseColor("#ffffff"));
        mUserBtn.setOnClickListener(this);

        mTopSwimming = (LinearLayout) findViewById(R.id.dople_home_list_for_top_swimming);
        mTopRunning = (LinearLayout) findViewById(R.id.dople_home_list_for_top_running);
        mTopOutdoor = (LinearLayout) findViewById(R.id.dople_home_list_for_top_outdoor);
        mRecommended = (LinearLayout) findViewById(R.id.dople_home_list_for_recommended);
        mGoodReview = (LinearLayout) findViewById(R.id.dople_home_list_for_good_review);

        mTabFavorite = (TextView) findViewById(R.id.dople_home_tab_favorite_selected);
        mTabRecommended = (TextView) findViewById(R.id.dople_home_tab_recommended_selected);
        mTabGooReview = (TextView) findViewById(R.id.dople_home_tab_good_review_selected);

        mTopBtnSwimming = (TextView)findViewById(R.id.dople_home_top_swimming);
        mTopBtnRunning = (TextView)findViewById(R.id.dople_home_top_running);
        mTopBtnOutdoor = (TextView)findViewById(R.id.dople_home_top_outdoor);
        mTopBtnSwimming.setOnClickListener(this);
        mTopBtnRunning.setOnClickListener(this);
        mTopBtnOutdoor.setOnClickListener(this);

        ((LinearLayout)findViewById(R.id.dople_home_tab_favorite)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.dople_home_tab_recommended)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.dople_home_tab_good_review)).setOnClickListener(this);


        ImageView mSearchBtn = (ImageView) findViewById(R.id.dople_home_search_btn);
        mSearchBtn.setOnClickListener(this);

        mSearchText = (EditText) findViewById(R.id.dople_home_search_text);


        mTopBtnSwimming.setSelected(true);

        mSearchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        Intent intent = new Intent(DopleHomeActivity.this, DopleSearchActivity.class);
                        intent.putExtra("key", mSearchText.getText().toString());
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.dople_home_tab_favorite:
                setDisplayLayout(0);
                break;
            case R.id.dople_home_tab_recommended:
                setDisplayLayout(1);
                break;
            case R.id.dople_home_tab_good_review:
                setDisplayLayout(2);
                break;
            case R.id.dople_home_top_swimming:
                currentIndex = 0;
                setDisplayLayout(0);
                break;
            case R.id.dople_home_top_running:
                currentIndex = 1;
                setDisplayLayout(0);
                break;
            case R.id.dople_home_top_outdoor:
                currentIndex = 2;
                setDisplayLayout(0);
                break;
            case R.id.dople_home_user_btn:
                intent = new Intent(DopleHomeActivity.this, DopleMyInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.dople_home_search_btn:
                intent = new Intent(DopleHomeActivity.this, DopleSearchActivity.class);
                intent.putExtra("key", mSearchText.getText().toString());
                startActivity(intent);
                break;
        }
    }

    private void setVisibleTab(int index) {
        mTabFavorite.setVisibility(View.GONE);
        mTabRecommended.setVisibility(View.GONE);
        mTabGooReview.setVisibility(View.GONE);

        if (index == 0) {
            mTabFavorite.setVisibility(View.VISIBLE);
            setVisibleFavorite(currentIndex);
        } else if (index == 1) {
            mTabRecommended.setVisibility(View.VISIBLE);
            setVisibleFavorite(-1);
        } else {
            mTabGooReview.setVisibility(View.VISIBLE);
            setVisibleFavorite(-1);
        }
    }

    private void setVisibleFavorite(int index) {
        mTopBtnOutdoor.setSelected(false);
        mTopBtnSwimming.setSelected(false);
        mTopBtnRunning.setSelected(false);

        if (index == 0) {
            mTopSwimming.setVisibility(View.VISIBLE);
            mTopBtnSwimming.setSelected(true);
        } else if (index == 1) {
            mTopRunning.setVisibility(View.VISIBLE);
            mTopBtnRunning.setSelected(true);
        } else if (index == 2){
            mTopOutdoor.setVisibility(View.VISIBLE);
            mTopBtnOutdoor.setSelected(true);
        }
    }

    private void setDisplayLayout(int index) {
        mTopSwimming.setVisibility(View.GONE);
        mTopRunning.setVisibility(View.GONE);
        mTopOutdoor.setVisibility(View.GONE);
        mRecommended.setVisibility(View.GONE);
        mGoodReview.setVisibility(View.GONE);

        if (index == 0) {
        } else if (index == 1) {
            mRecommended.setVisibility(View.VISIBLE);
        } else {
            mGoodReview.setVisibility(View.VISIBLE);
        }

        setVisibleTab(index);
    }
}
/*
 * Copyright (C) 2016 Lukoh Nam, goForer
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

package com.goforer.fyber.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.goforer.base.ui.activity.BaseActivity;
import com.goforer.base.ui.view.SwipeViewPager;
import com.goforer.fyber.R;
import com.goforer.fyber.model.data.Offers;
import com.goforer.fyber.ui.adapter.OfferInfoAdapter;
import com.goforer.fyber.utility.ActivityCaller;

import java.util.List;

import butterknife.BindView;

/**
 * Created by USER on 2016-09-06.
 */
public class OffersInfoActivity extends BaseActivity {
    private static final String TAG = "OffersInfoActivity";
    private static final String TRANSITION_IMAGE = "OffersInfoActivity:image";

    private static final int PAGE_MARGIN_VALUE = 40;

    private List<Offers> mOffersItems;
    private ActionBar mActionBar;

    private int mItemPosition;
    private int mFrom;

    @BindView(R.id.pager_flip)
    SwipeViewPager mSwipePager;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.backdrop)
    ImageView mBackdrop;
    @BindView(R.id.backdrop_new)
    ImageView mNewBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFrom = getIntent().getIntExtra(ActivityCaller.EXTRA_FROM, -1);
        mItemPosition = getIntent().getIntExtra(ActivityCaller.EXTRA_OFFERS_ITEM_POSITION, -1);
        mOffersItems = getIntent().getExtras().getParcelableArrayList(
                ActivityCaller.EXTRA_OFFERS_LIST);

        if (mOffersItems == null && mItemPosition == -1) {
            showToastMessage(getString(R.string.toast_no_offers));
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_offer_info);
    }

    @Override
    protected void setViews(Bundle savedInstanceState) {
        if (mOffersItems != null && mItemPosition != -1) {
            OfferInfoAdapter mAdapter = new OfferInfoAdapter(getSupportFragmentManager(), mOffersItems);
            mSwipePager.setAdapter(mAdapter);
            ViewCompat.setTransitionName(mSwipePager, TRANSITION_IMAGE);
            mSwipePager.setPageMargin(PAGE_MARGIN_VALUE);

            handleSwipePager();
            Glide.with(getApplicationContext()).load(mOffersItems.get(mItemPosition).getThumbnail()
                    .getHires()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    mBackdrop.setImageBitmap(resource);
                }
            });
        }
    }

    @Override
    protected void setActionBar() {
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mCollapsingToolbarLayout.setTitle(mOffersItems.get(mItemPosition).getTitle());
        if (mActionBar != null) {
            mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);
            mActionBar.setTitle(getResources().getString(R.string.app_name));
            mActionBar.setElevation(0);
            mActionBar.setDisplayShowTitleEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    protected void setEffectIn() {
        Log.i(TAG, "setEffectIn");

        overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.scale_down_exit);
    }

    @Override
    protected void setEffectOut() {
        Log.i(TAG, "setEffectOut");

        overridePendingTransition(R.anim.scale_up_enter, R.anim.slide_out_to_bottom);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(ActivityCaller.EXTRA_SELECTED_ITEM_POSITION, mItemPosition);
        this.setResult(RESULT_OK, intent);

        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void showNewBackDropImage(int position) {
        Glide.with(getApplicationContext()).load(mOffersItems.get(position).getThumbnail()
                .getHires()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mNewBackdrop.setImageBitmap(resource);
            }
        });
    }

    private void setImage(int position) {
        Glide.with(getApplicationContext()).load(mOffersItems.get(position).getThumbnail()
                .getHires()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mBackdrop.setImageBitmap(resource);
            }
        });

        if (position == mOffersItems.size() - 1) {
            showNewBackDropImage(position);
        } else {
            showNewBackDropImage(position + 1);
        }
    }

    private void setTitle(final int position, final float positionOffset) {
        if (positionOffset > 0.5) {
            if (position == mOffersItems.size() - 1) {
                mCollapsingToolbarLayout.setTitle(mOffersItems.get(position).getTitle());
            } else {
                mCollapsingToolbarLayout.setTitle(mOffersItems.get(position + 1).getTitle());
            }
        } else {
            mCollapsingToolbarLayout.setTitle(mOffersItems.get(position).getTitle());
        }
    }

    private void showToastMessage(String phrase) {
        Toast.makeText(getApplicationContext(), phrase, Toast.LENGTH_SHORT).show();
    }

    private void handleSwipePager() {
        mSwipePager.setCurrentItem(mItemPosition, false);
        mSwipePager.setOnSwipeOutListener(new SwipeViewPager.OnSwipeOutListener() {
            @Override
            public void onSwipeOutAtStart() {
            }

            @Override
            public void onSwipeOutAtEnd() {
            }
        });

        mActionBar.setTitle(mOffersItems.get(mItemPosition).getTitle());
        mSwipePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int displace = -positionOffsetPixels;
                int displaceNew = -positionOffsetPixels + mSwipePager.getWidth();

                if (positionOffset > 0.999) {
                    mBackdrop.setX(0);
                    mNewBackdrop.setX(0);
                } else {
                    setTitle(position, positionOffset);
                    mBackdrop.setX(displace);
                    mNewBackdrop.setX(displaceNew);
                }

                setImage(position);
            }

            @Override
            public void onPageSelected(int position) {
                mItemPosition = position;
                mCollapsingToolbarLayout.setTitle(mOffersItems.get(mItemPosition).getTitle());
                mCollapsingToolbarLayout.setExpandedTitleColor(getResources()
                        .getColor(R.color.white));
                mSwipePager.setCurrentItem(position, false);

                Log.d(TAG, "called onPageSelected");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}

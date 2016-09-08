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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import com.goforer.base.ui.activity.BaseActivity;
import com.goforer.fyber.R;
import com.goforer.fyber.model.action.FinishAction;
import com.goforer.fyber.model.action.MoveItemAction;
import com.goforer.fyber.model.action.SelectItemAction;
import com.goforer.fyber.ui.fragment.OfferListFragment;
import com.goforer.fyber.utility.ActivityCaller;
import com.goforer.fyber.utility.ConnectionUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_notice)
    TextView mNoticeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!ConnectionUtils.isNetworkAvailable(getApplicationContext())) {
            mNoticeText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void setViews(Bundle savedInstanceState) {
        transactFragment(OfferListFragment.class, R.id.content_holder, null);
    }

    @Override
    protected void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);
            actionBar.setTitle("  " + getResources().getString(R.string.app_name));
            actionBar.setLogo(R.drawable.ic_app_bar);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setElevation(0);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    protected void setEffectIn() {
        overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.scale_down_exit);
    }

    @Override
    protected void setEffectOut() {
        overridePendingTransition(R.anim.scale_up_enter, R.anim.slide_out_to_bottom);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case ActivityCaller.SELECTED_ITEM_POSITION:
                if (resultCode == RESULT_OK) {
                    int position = data.getIntExtra(
                            ActivityCaller.EXTRA_SELECTED_ITEM_POSITION, -1);
                    if (position != -1) {
                        MoveItemAction action = new MoveItemAction();
                        action.setPosition(position);
                        EventBus.getDefault().post(action);
                    }
                }
                break;
            default:
        }
    }

    public void showDialog(FinishAction action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(action.getCode());
        builder.setMessage(action.getMessage() + "\n"
                + getResources().getString(R.string.restart_phrase));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                finish();
            }
        });

        builder.show();
    }

    @SuppressWarnings("")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAction(SelectItemAction action) {
        ActivityCaller.INSTANCE.callInfo(this, action.getOffersList(), action.getPosition(),
                ActivityCaller.FROM_OFFERS_LIST, ActivityCaller.SELECTED_ITEM_POSITION);
    }
}

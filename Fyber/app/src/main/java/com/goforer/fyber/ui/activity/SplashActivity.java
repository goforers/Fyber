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

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.goforer.base.ui.activity.BaseActivity;
import com.goforer.fyber.R;
import com.goforer.fyber.utility.ActivityCaller;
import com.goforer.fyber.utility.CommonUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class SplashActivity  extends BaseActivity {
    private static final int MIN_SPLASH_TIME = 2000;

    private long mSplashStart;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_splash);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetGAIDTask(this).execute();
        mSplashStart = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();

        onWait();
    }

    private void onWait() {
        long elapsed = System.currentTimeMillis() - mSplashStart;

        long more_splash = MIN_SPLASH_TIME <= elapsed ? 0 : MIN_SPLASH_TIME - elapsed;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveToMain();
            }
        }, more_splash);
    }

    private void moveToMain() {
        ActivityCaller.INSTANCE.callOffersList(this);
        finish();
    }

    private static class GetGAIDTask extends AsyncTask<String, Integer, String> {
        private SplashActivity mActivity;
        private WeakReference<SplashActivity> mFragmentWeakRef;

        public GetGAIDTask(SplashActivity activity) {
            mActivity = activity;
            mFragmentWeakRef = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(String... strings) {
            AdvertisingIdClient.Info adInfo;
            adInfo = null;
            try {
                adInfo = AdvertisingIdClient.getAdvertisingIdInfo(
                        mActivity.getApplicationContext());
                if (adInfo.isLimitAdTrackingEnabled()) {
                    CommonUtils.setLimitAdTrackingEnabled(true);
                    return "Not found GAID";
                }

                CommonUtils.setLimitAdTrackingEnabled(false);
            } catch (IOException | GooglePlayServicesNotAvailableException
                    | GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            }

            return adInfo != null ? adInfo.getId() : null;
        }

        @Override
        protected void onPostExecute(String id) {
            SplashActivity activity = mFragmentWeakRef.get();
            if (activity != null) {
                CommonUtils.setGoogleAID(id.trim().replaceAll("-", ""));
            }
        }
    }
}

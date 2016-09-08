package com.goforer.fyber;

import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;

import com.goforer.fyber.ui.activity.SplashActivity;
import com.goforer.fyber.utility.CommonUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import org.jetbrains.annotations.TestOnly;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by USER on 2016-09-06.
 */
public class GAIDAndroidTest extends ActivityInstrumentationTestCase2<SplashActivity> {
    private static boolean mIsFound;

    public GAIDAndroidTest(){
        super(SplashActivity.class);
    }

    private class GetGAIDTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            AdvertisingIdClient.Info adInfo;
            adInfo = null;
            try {
                adInfo = AdvertisingIdClient.getAdvertisingIdInfo(
                        getActivity().getApplicationContext());
                if (adInfo.isLimitAdTrackingEnabled()) {
                    CommonUtils.setLimitAdTrackingEnabled(true);
                    return "Not found GAID";
                }
            } catch (IOException | GooglePlayServicesNotAvailableException
                    | GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            }

            return adInfo.getId();
        }

        @Override
        protected void onPostExecute(String id) {
            if ("Not found GAID".equals(id) || id.isEmpty()) {
                mIsFound = false;
            } else {
                mIsFound = true;
            }
        }
    }

    @TestOnly
    public void testGetGAIDTask() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                new GetGAIDTask().execute();
            }
        });

	    /* The testing thread will wait here until the UI thread releases it
	     * above with the countDown() or 30 seconds passes and it times out.
	     */
        signal.await(2, TimeUnit.SECONDS);
        assertTrue(mIsFound);
    }
}

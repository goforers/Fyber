package com.goforer.fyber;

import android.test.ActivityInstrumentationTestCase2;

import com.goforer.fyber.ui.activity.SplashActivity;
import com.goforer.fyber.web.communicator.RequestClient;

import org.jetbrains.annotations.TestOnly;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by USER on 2016-09-06.
 */
public class HTTPClientCreateAndroidTest extends ActivityInstrumentationTestCase2<SplashActivity> {

    private static boolean mIsCalled;

    public HTTPClientCreateAndroidTest() {
        super(SplashActivity.class);
    }

    @TestOnly
    public void testCreateRetrofit() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

        RequestClient.RequestMethod call =
                RequestClient.INSTANCE.getRequestMethod(
                        getActivity().getApplicationContext());

        if (call != null) {
            mIsCalled = true;
        } else {
            mIsCalled = false;
        }

        /* The testing thread will wait here until the UI thread releases it
	     * above with the countDown() or 30 seconds passes and it times out.
	     */
        signal.await(2, TimeUnit.SECONDS);
        assertTrue(mIsCalled);

    }
}

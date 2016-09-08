package com.goforer.fyber;

import android.test.ActivityInstrumentationTestCase2;

import com.goforer.fyber.model.data.Responses;
import com.goforer.fyber.model.event.OffersDataEvent;
import com.goforer.fyber.ui.activity.SplashActivity;
import com.goforer.fyber.utility.CommonUtils;
import com.goforer.fyber.web.communicator.RequestClient;

import org.jetbrains.annotations.TestOnly;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by USER on 2016-09-06.
 */
public class OfferAPIRequestAndroidTest extends ActivityInstrumentationTestCase2<SplashActivity> {
    private static final String IP = "109.235.143.113";
    private static final String LOCALE = "DE";
    private static final String UID = "spiderman";
    private static final String GAID = "c53305827e98418cb1131a19353ed211";

    private static final int APP_ID = 2070;
    private static final int OFFER_TYPES = 112;

    private static boolean mIsSucceeded;

    public OfferAPIRequestAndroidTest(){
        super(SplashActivity.class);
    }

    private String getHashKey(String advertisingId, long timestamp, int pageNum)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        StringBuilder tmp;
        tmp = new StringBuilder();
        tmp.append("appid=");
        tmp.append(APP_ID).append("&");
        tmp.append("device_id=");
        tmp.append(advertisingId).append("&");
        tmp.append("ip=");
        tmp.append(IP).append("&");
        tmp.append("locale=");
        tmp.append(LOCALE).append("&");
        tmp.append("offer_types=");
        tmp.append(OFFER_TYPES).append("&");
        tmp.append("page=");
        tmp.append(pageNum).append("&");
        tmp.append("timestamp=");
        tmp.append(timestamp).append("&");
        tmp.append("uid=");
        tmp.append(UID).append("&");
        tmp.append(CommonUtils.API_KEY);

        return CommonUtils.SHA1(tmp.toString());
    }

    @TestOnly
    public void testGetResponse() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

        long timestamp = System.currentTimeMillis() / 1000L;

        OffersDataEvent event = new OffersDataEvent(true);
        String hashKey = null;
        try {
            hashKey = getHashKey(GAID, timestamp, 1);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Call<Responses> call = RequestClient.INSTANCE.getRequestMethod(getActivity().getApplicationContext())
                .getOffers(APP_ID, GAID, IP, LOCALE, OFFER_TYPES, 1, timestamp, UID, hashKey);
        call.enqueue(new RequestClient.RequestCallback(event) {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                if (response.body().getCode().equals("OK") && !response.body().getOffers().isJsonNull()) {
                    mIsSucceeded = true;
                } else {
                    mIsSucceeded = false;
                }
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                super.onFailure(call, t);
                mIsSucceeded = false;
            }

        });

	    /* The testing thread will wait here until the UI thread releases it
	     * above with the countDown() or 30 seconds passes and it times out.
	     */
        signal.await(2, TimeUnit.SECONDS);
        assertTrue(mIsSucceeded);
    }
}

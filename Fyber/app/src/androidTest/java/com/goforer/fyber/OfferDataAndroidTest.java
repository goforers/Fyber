package com.goforer.fyber;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.goforer.fyber.model.data.Offers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by USER on 2016-09-06.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class OfferDataAndroidTest {
    public static final String mTitle = "Tap  Fish";
    public static final long mOfferId = 13554;
    public static final String mTeaser = "Download and START";
    public static final String mRequiredActions = "Download and START";
    public static final String mLink = "http://iframe.fyber.com/mbrowser?appid=157&lpid=11387&uid=player1";
    public static final int mOfferTypeId = 101;
    public static final String mReadable = "Download";
    public static final String mLowres = "http://cdn.fyber.com/assets/1808/icon175x175-2_square_60.png";
    public static final String mHires = "http://cdn.fyber.com/assets/1808/icon175x175-2_square_175.png";
    public static final int mPayout = 90;
    public static final int mAmount = 1800;
    public static final String mTimeToPayoutReadable = "30 minutes";

    private Offers mOffers;
    private Offers.OfferTypes mOfferTypes;
    private Offers.Thumbnail mThumbnail;
    private Offers.TimeToPayout mTimeToPayout;

    @Before
    public void createObject() {
        mOffers = new Offers();
        mOfferTypes = new Offers.OfferTypes();
        mThumbnail = new Offers.Thumbnail();
        mTimeToPayout = new Offers.TimeToPayout();
    }

    @Test
    public void offers_ParcelableWriteRead() {
        // Set up the Parcelable object to send and receive.
        mOffers.setTitle(mTitle);
        mOffers.setOfferId(mOfferId);
        mOffers.setTeaser(mTeaser);
        mOffers.setRequiredActions(mRequiredActions);
        mOffers.setLink(mLink);
        List<Offers.OfferTypes>  offerTypes = new ArrayList<>();
        mOfferTypes.setOfferTypeId(mOfferTypeId);
        mOfferTypes.setReadable(mReadable);
        offerTypes.add(mOfferTypes);
        mOfferTypes.setOfferTypeId(mOfferTypeId);
        mOfferTypes.setReadable(mReadable);
        offerTypes.add(mOfferTypes);
        mOffers.setOfferTypes(offerTypes);
        mThumbnail.setLowres(mLowres);
        mThumbnail.setHires(mHires);
        mOffers.setThumbnail(mThumbnail);
        mOffers.setPayout(mPayout);
        mTimeToPayout.setAmount(mAmount);
        mTimeToPayout.setReadable(mTimeToPayoutReadable);
        mOffers.setTimeToPayout(mTimeToPayout);

        // Write the data.
        Parcel parcel = Parcel.obtain();
        mOffers.writeToParcel(parcel, mOffers.describeContents());

        // After you're done with writing, you need to reset the parcel for reading.
        parcel.setDataPosition(0);

        // Read the data.
        Offers createdFromParcel = Offers.CREATOR.createFromParcel(parcel);
        String title = createdFromParcel.getTitle();
        long offerId = createdFromParcel.getOfferId();
        String teaser = createdFromParcel.getTeaser();
        String hires = createdFromParcel.getThumbnail().getHires();
        int payout = createdFromParcel.getPayout();

        // Verify that the received data is correct.
        assertThat(offerId, is(mOfferId));
        assertThat(title, is(mTitle));
        assertThat(teaser, is(mTeaser));
        assertThat(hires, is(mHires));
        assertThat(payout, is(mPayout));
    }
}

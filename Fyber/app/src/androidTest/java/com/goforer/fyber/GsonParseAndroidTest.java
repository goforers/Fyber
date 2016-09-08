package com.goforer.fyber;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.goforer.base.model.ListModel;
import com.goforer.fyber.model.data.Offers;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by USER on 2016-09-07.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class GsonParseAndroidTest {
    private Offers mOffers;
    private List<Offers> mOfferList;
    private String mOffersJson;
    private JsonElement mOffersElement;

    @Before
    public void convertToJsonElement() {
        Gson gson = new Gson();
        mOffersJson =
                "[{\"title\":\"Tap  Fish\",\"offer_id\":13554,\"teaser\":\"Download and START\",\"required_actions\":\"Download and START\",\"link\":\"http://iframe.fyber.com/mbrowser?appid=157&lpid=11387&uid=player11\",\"offer_types\":[{\"offer_type_id\":105,\"readable\":\"Registration\"},{\"offer_type_id\":112,\"readable\":\"Free\"}],\"payout\":18,\"time_to_payout\":{\"amount\":900,\"readable\":\"15 minutes\"},\"thumbnail\":{\"lowres\":\"http://cdn.fyber.com/assets/1808/icon175x175-2_square_60.png\",\"hires\":\"http://cdn.fyber.com/assets/1808/icon175x175-2_square_175.png\"}}," +
                 "{\"title\":\"Tap  Fish1\",\"offer_id\":13555,\"teaser\":\"Download and START\",\"required_actions\":\"Download and START\",\"link\":\"http://iframe.fyber.com/mbrowser?appid=157&lpid=11387&uid=player12\",\"offer_types\":[{\"offer_type_id\":105,\"readable\":\"Registration\"},{\"offer_type_id\":112,\"readable\":\"Free\"}],\"payout\":37,\"time_to_payout\":{\"amount\":300,\"readable\":\"5 minutes\"},\"thumbnail\":{\"lowres\":\"http://cdn.fyber.com/assets/1808/icon175x175-2_square_61.png\",\"hires\":\"http://cdn.fyber.com/assets/1808/icon175x175-2_square_176.png\"}}," +
                 "{\"title\":\"Tap  Fish2\",\"offer_id\":13556,\"teaser\":\"Download and START\",\"required_actions\":\"Download and START\",\"link\":\"http://iframe.fyber.com/mbrowser?appid=157&lpid=11387&uid=player13\",\"offer_types\":[{\"offer_type_id\":105,\"readable\":\"Registration\"},{\"offer_type_id\":112,\"readable\":\"Free\"}],\"payout\":18,\"time_to_payout\":{\"amount\":1800,\"readable\":\"30 minutes\"},\"thumbnail\":{\"lowres\":\"http://cdn.fyber.com/assets/1808/icon175x175-2_square_62.png\",\"hires\":\"http://cdn.fyber.com/assets/1808/icon175x175-2_square_177.png\"}}," +
                 "{\"title\":\"Tap  Fish3\",\"offer_id\":13557,\"teaser\":\"Download and START\",\"required_actions\":\"Download and START\",\"link\":\"http://iframe.fyber.com/mbrowser?appid=157&lpid=11387&uid=player14\",\"offer_types\":[{\"offer_type_id\":105,\"readable\":\"Registration\"},{\"offer_type_id\":112,\"readable\":\"Free\"}],\"payout\":157,\"time_to_payout\":{\"amount\":300,\"readable\":\"5 minutes\"},\"thumbnail\":{\"lowres\":\"http://cdn.fyber.com/assets/1808/icon175x175-2_square_63.png\",\"hires\":\"http://cdn.fyber.com/assets/1808/icon175x175-2_square_178.png\"}}," +
                 "{\"title\":\"Tap  Fish3\",\"offer_id\":13558,\"teaser\":\"Download and START\",\"required_actions\":\"Download and START\",\"link\":\"http://iframe.fyber.com/mbrowser?appid=157&lpid=11387&uid=player15\",\"offer_types\":[{\"offer_type_id\":101,\"readable\":\"Download\"},{\"offer_type_id\":112,\"readable\":\"Free\"}],\"payout\":7,\"time_to_payout\":{\"amount\":1800,\"readable\":\"30 minutes\"},\"thumbnail\":{\"lowres\":\"http://cdn.fyber.com/assets/1808/icon175x175-2_square_64.png\",\"hires\":\"http://cdn.fyber.com/assets/1808/icon175x175-2_square_177.png\"}}]";

        mOffersElement = gson.fromJson (mOffersJson, JsonElement.class);
    }

    public List<Offers> parseItems(JsonElement json) {
        return new ListModel<>(Offers.class).fromJson(json);
    }

    @Test
    public void do_OffersDataParse() throws JSONException {
        mOfferList = parseItems(mOffersElement);
        if (mOfferList != null && mOfferList.size() == 5) {
            assertTrue(true);
        } else {
            assertTrue(false);
        }
    }
}

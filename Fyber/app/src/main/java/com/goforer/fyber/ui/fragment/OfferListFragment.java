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

package com.goforer.fyber.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goforer.base.model.ListModel;
import com.goforer.base.ui.fragment.RecyclerFragment;
import com.goforer.fyber.R;
import com.goforer.fyber.model.action.FinishAction;
import com.goforer.fyber.model.action.MoveItemAction;
import com.goforer.fyber.model.data.Offers;
import com.goforer.fyber.model.data.Responses;
import com.goforer.fyber.model.event.OffersDataEvent;
import com.goforer.fyber.ui.activity.MainActivity;
import com.goforer.fyber.ui.adapter.OfferListAdapter;
import com.goforer.fyber.utility.CommonUtils;
import com.goforer.fyber.web.Intermediary;
import com.goforer.fyber.web.communicator.RequestClient;
import com.google.gson.JsonElement;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class OfferListFragment extends RecyclerFragment<Offers> {
    private static final String TAG = "OfferListFragment";

    private OfferListAdapter mAdapter;

    private int mTotalPageNum;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offer_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTotalPageNum = 1;
        setItemHasFixedSize(true);
        refresh();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        super.setOnProcessListener(new RecyclerFragment.OnProcessListener() {
            @Override
            public void onCompleted(int result) {
                if (result == OnProcessListener.RESULT_ERROR) {
                    showToastMessage(getString(R.string.toast_process_error));
                }
            }

            @Override
            public void onScrolledToLast(RecyclerView recyclerView, int dx, int dy) {
                Log.i(TAG, "onScrolledToLast");
            }

            @Override
            public void onScrolling() {
                Log.i(TAG, "onScrolling");
            }

            @Override
            public void onScrolled() {
                Log.i(TAG, "onScrolled");
            }
        });

        return new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return mAdapter = new OfferListAdapter(mContext, mItems, R.layout.list_offer_item, true);
    }

    @Override
    protected boolean isItemDecorationVisible() {
        return false;
    }

    @Override
    protected void requestData(boolean isNew) {
        try {
            requestOfferList(isNew);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "requestData");
    }

    @Override
    protected void updateData() {
        doneRefreshing();

        Log.i(TAG, "updateData");
    }

    @Override
    protected List<Offers> parseItems(JsonElement json) {
        return new ListModel<>(Offers.class).fromJson(json);
    }

    @Override
    protected boolean isLastPage(int pageNum) {
        return (mTotalPageNum == pageNum) && (mTotalPageNum > 1);

    }

    private void requestOfferList(boolean isNew)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        OffersDataEvent event = new OffersDataEvent(isNew);
        String advertisingId = CommonUtils.getGoogleAID();
        long timestamp = System.currentTimeMillis() / 1000L;

        String hashKey = getHashKey(advertisingId, timestamp, mCurrentPage);
        hashKey = hashKey.toLowerCase();

        Intermediary.INSTANCE.getOffers(mContext.getApplicationContext(), RequestClient.APP_ID,
                advertisingId, RequestClient.IP, RequestClient.LOCALE, RequestClient.OFFER_TYPES,
                mCurrentPage, timestamp, RequestClient.UID, hashKey, event);
    }

    @SuppressWarnings("")
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(OffersDataEvent event) {
        switch(event.getResponseClient().getStatus()) {
            case Responses.GENERAL_ERROR:
                showToastMessage(getString(R.string.toast_server_error_phrase));
                break;
            case Responses.NETWORK_ERROR:
                showToastMessage(getString(R.string.toast_disconnect_phrase));
                break;
            case Responses.RESPONSE_SIGNATURE_NOT_MATCH:
                showToastMessage(getString(R.string.toast_response_signature_mismatch_phrase));
                break;
            case Responses.SUCCESSFUL:
                if (event.getResponseClient().getCount() == 0) {
                    showToastMessage(getString(R.string.toast_no_offers));
                    return;
                }

                if (mCurrentPage == 1) {
                    mTotalPageNum = event.getResponseClient().getPages();
                }

                handleEvent(event);
                break;
        }
    }

    @SuppressWarnings("")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAction(MoveItemAction action) {
        mAdapter.moveSelectedPosition(getRecyclerView().getLayoutManager(), action.getPosition());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAction(FinishAction action){
        doneRefreshing();
        ((MainActivity)mActivity).showDialog(action);
    }

    private void showToastMessage(String phrase) {
        Toast.makeText(mContext.getApplicationContext(), phrase, Toast.LENGTH_SHORT).show();
    }

    private String getHashKey(String advertisingId, long timestamp, int pageNum)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        StringBuilder tmp = new StringBuilder();
        tmp.append("appid=");
        tmp.append(RequestClient.APP_ID).append("&");
        tmp.append("device_id=");
        tmp.append(advertisingId).append("&");
        tmp.append("ip=");
        tmp.append(RequestClient.IP).append("&");
        tmp.append("locale=");
        tmp.append(RequestClient.LOCALE).append("&");
        tmp.append("offer_types=");
        tmp.append(RequestClient.OFFER_TYPES).append("&");
        tmp.append("page=");
        tmp.append(pageNum).append("&");
        tmp.append("timestamp=");
        tmp.append(timestamp).append("&");
        tmp.append("uid=");
        tmp.append(RequestClient.UID).append("&");
        tmp.append(CommonUtils.API_KEY);

        return CommonUtils.SHA1(tmp.toString());
    }
}

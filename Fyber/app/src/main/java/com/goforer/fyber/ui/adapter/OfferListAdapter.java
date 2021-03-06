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

package com.goforer.fyber.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goforer.base.ui.adapter.BaseListAdapter;
import com.goforer.base.ui.adapter.BaseViewHolder;
import com.goforer.base.ui.adapter.DefaultViewHolder;
import com.goforer.base.ui.view.ThumbnailImageView;
import com.goforer.fyber.R;
import com.goforer.fyber.model.action.SelectItemAction;
import com.goforer.fyber.model.data.Offers;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

public class OfferListAdapter extends BaseListAdapter<Offers> {
    private static final String PAY_OUT = "PayOut : ";

    private Context mContext;

    public OfferListAdapter(Context context, final List<Offers> items, int layoutResId,
                            boolean usedLoadingImage) {
        super(items, layoutResId);

        setUsedLoadingImage(usedLoadingImage);
        mContext = context;
    }

    @Override
    public int getItemCount() {
        int count  = super.getItemCount();

        if (isReachedToLastPage()) {
            return count + 1;
        }

        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int itemCount = getItemCount() - 1;

        if (isReachedToLastPage() && position == itemCount) {
            return VIEW_TYPE_FOOTER;
        } else if (position == itemCount) {
            return VIEW_TYPE_LOADING;
        }

        return VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view;

        switch (type) {
            case VIEW_TYPE_FOOTER:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_last_item,
                        viewGroup, false);
                return new DefaultViewHolder(view);
            case VIEW_TYPE_LOADING:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.list_items_loading, viewGroup, false);
                return new DefaultViewHolder(view);
            default:
                return super.onCreateViewHolder(viewGroup, type);
        }
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(View view, int type) {
        return new OfferListViewHolder(view, mItems);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)){
            case VIEW_TYPE_FOOTER:
            case VIEW_TYPE_LOADING:
                return;
            default:
                super.onBindViewHolder(viewHolder, position);
        }
    }

    public static class OfferListViewHolder extends BaseViewHolder<Offers> {
        private List<Offers> mOffersItems;

        @BindView(R.id.iv_hires)
        ThumbnailImageView mHiresView;
        @BindView(R.id.tv_title)
        TextView mTitleView;
        @BindView(R.id.tv_teaser)
        TextView mTeaserView;
        @BindView(R.id.tv_payout)
        TextView mPayoutView;

        public OfferListViewHolder(View itemView, List<Offers> items) {
            super(itemView);

            mOffersItems = items;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void bindItemHolder(@NonNull final Offers offers, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectItemAction action = new SelectItemAction();
                    action.setOffers(offers);
                    action.setOffersList(mOffersItems);
                    action.setPosition(position);
                    EventBus.getDefault().post(action);
                }
            });

            mHiresView.setImage(offers.getThumbnail().getHires());
            mTitleView.setText(offers.getTitle());
            mTeaserView.setText(offers.getTeaser());
            mPayoutView.setText(PAY_OUT + String.valueOf(offers.getPayout()));
        }
    }
}

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

package com.goforer.fyber.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.goforer.base.ui.activity.BaseActivity;
import com.goforer.fyber.model.data.Offers;
import com.goforer.fyber.ui.activity.MainActivity;
import com.goforer.fyber.ui.activity.OffersInfoActivity;

import java.util.ArrayList;
import java.util.List;

public enum ActivityCaller {
    INSTANCE;

    public static final String EXTRA_SELECTED_ITEM_POSITION = "fyber:selected_item_position";
    public static final String EXTRA_FROM = "fyber:from";
    public static final String EXTRA_OFFERS_LIST = "fyber:offers_list";
    public static final String EXTRA_OFFERS_ITEM_POSITION = "fyber:offers_items_position";

    public static final int FROM_OFFERS_LIST = 0;
    public static final int SELECTED_ITEM_POSITION = 1000;

    public Intent createIntent(Context context, Class<?> cls, boolean isNewTask) {
        Intent intent = new Intent(context, cls);

        if (isNewTask && !(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        return intent;
    }

    private Intent createIntent(String action) {
        Intent intent = new Intent(action);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return intent;
    }

    public void callOffersList(Context context) {
        Intent intent = createIntent(context, MainActivity.class, true);
        context.startActivity(intent);
    }

    public void callInfo(Context context, List<Offers> items, int position, int from,
                         int requestCode) {
        Intent intent = createIntent(context, OffersInfoActivity.class, true);
        intent.putExtra(EXTRA_FROM, from);
        intent.putParcelableArrayListExtra(EXTRA_OFFERS_LIST, (ArrayList<Offers>)items);
        intent.putExtra(EXTRA_OFFERS_ITEM_POSITION, position);
        ((BaseActivity)context).startActivityForResult(intent, requestCode);
    }

    public void callLink(Context context,  String url) {
        Intent intent = createIntent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));

        context.startActivity(intent);
    }
}

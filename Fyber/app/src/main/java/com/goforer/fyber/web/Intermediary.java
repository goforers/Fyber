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

package com.goforer.fyber.web;

import android.content.Context;

import com.goforer.base.model.event.ResponseEvent;
import com.goforer.fyber.model.data.Responses;
import com.goforer.fyber.web.communicator.RequestClient;

import retrofit2.Call;
import retrofit2.Response;

public enum Intermediary {
    INSTANCE;

    public void getOffers(Context context, int appId, String device_id, String ip, String locale,
                          int offer_type, int pageNum, long timestamp, String uid, String hashKey,
                          ResponseEvent event)  {

        Call<Responses> call = RequestClient.INSTANCE.getRequestMethod(context)
                .getOffers(appId, device_id, ip, locale, offer_type, pageNum, timestamp, uid, hashKey);
        call.enqueue(new RequestClient.RequestCallback(event) {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                super.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
}

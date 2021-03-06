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

package com.goforer.base.model;

import com.google.gson.JsonElement;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ListModel <T extends BaseModel> implements ParameterizedType {
    private Class<T> mWrapped;

    public ListModel(Class<T> wrapped) {
        mWrapped = wrapped;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] { mWrapped};
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

    @Override
    public Type getRawType() {
        return List.class;
    }

    public List<T> fromJson(JsonElement json) {
        return T.gson().fromJson(json, this);
    }
}

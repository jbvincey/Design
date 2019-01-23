/*
 * Copyright 2018 Jean-Baptiste VINCEY.
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

package com.jbvincey.design.widget.helper;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by jbvincey on 22/11/2018.
 * Implement this listener with the behavior you want to set on cell swiped. Pass this listener to the
 * {@link SwipeCallbackModel}, that you will pass in the {@link SwipeCallback} constructor (either start
 * or end), this listener will be called on swipe (start or end according to the {@link SwipeCallbackModel}).
 */
public interface SwipeCallbackListener {

    void onViewSwiped(@NonNull View view);

}

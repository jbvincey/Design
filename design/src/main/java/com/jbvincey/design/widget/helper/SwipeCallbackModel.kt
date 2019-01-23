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

package com.jbvincey.design.widget.helper

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt

/**
 * Created by jbvincey on 22/11/2018.
 * Defines the view drawn behind viewholder on swipe, and the behavior on viewholder swiped.
 * @param backgroundColor color of the view drawn behind item on swipe
 * @param actionDrawable drawable to display in the view behind item on swipe
 * @param actionMargin margin from the side to apply to the actionDrawable
 * @param swipeControllerListener listener called on item swiped
 */
class SwipeCallbackModel(
        @ColorInt private val backgroundColor: Int,
        val actionDrawable: Drawable?,
        val actionMargin: Int,
        val swipeControllerListener: SwipeCallbackListener
) {

    val backgroundDrawable = ColorDrawable(backgroundColor)

}
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
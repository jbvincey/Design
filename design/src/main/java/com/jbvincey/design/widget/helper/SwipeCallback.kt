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

import android.content.Context
import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.END
import androidx.recyclerview.widget.ItemTouchHelper.START
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by jbvincey on 22/11/2018.
 * SwipeCallback is a {@link ItemTouchHelper.SimpleCallback} to be passed to {@link ItemTouchHelper}
 * (in constructor), that can be attached to a {@link RecyclerView} to enable item swipe.
 * @param swipeCallbackModelStart a {@link SwipeCallbackModel} defining view to draw behind item on swiping start
 * @param swipeCallbackModelEnd a {@link SwipeCallbackModel} defining view to draw behind item on swiping end
 * @param context any context (application or activity), used to access resources
 */
class SwipeCallback(
        private val swipeCallbackModelStart: SwipeCallbackModel? = null,
        private val swipeCallbackModelEnd: SwipeCallbackModel? = null,
        context: Context
) : ItemTouchHelper.SimpleCallback(0,
        (if (swipeCallbackModelStart != null) START else 0) or (if (swipeCallbackModelEnd != null) END else 0)
) {

    private val isRtL = context.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL

    override fun onMove(
            recyclerView: RecyclerView,
            viewHolder1: RecyclerView.ViewHolder,
            viewHolder2: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(
            viewHolder: RecyclerView.ViewHolder,
            direction: Int
    ) {
        if (direction == START) {
            swipeCallbackModelStart?.swipeControllerListener?.onViewSwiped(viewHolder.itemView)
        } else {
            swipeCallbackModelEnd?.swipeControllerListener?.onViewSwiped(viewHolder.itemView)
        }
    }

    override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            viewHolder.itemView.let { itemView ->
                if (itemView is SwipeableView) {
                    (if (itemView.isSwipeableStart()) START else 0) or (if (itemView.isSwipeableEnd()) END else 0)
                } else {
                    super.getSwipeDirs(recyclerView, viewHolder)
                }
            }

    override fun onChildDraw(
            canvas: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView

        val dXInt = dX.toInt()
        if (isSwipingStart(dXInt)) {
            if (isRtL) {
                drawBackgroundRight(itemView, canvas, dXInt, swipeCallbackModelStart!!)
                drawActionDrawableRight(itemView, canvas, dXInt, swipeCallbackModelStart)
            } else {
                drawBackgroundLeft(itemView, canvas, dXInt, swipeCallbackModelStart!!)
                drawActionDrawableLeft(itemView, canvas, dXInt, swipeCallbackModelStart)
            }
        } else {
            if (isRtL) {
                drawBackgroundLeft(itemView, canvas, dXInt, swipeCallbackModelEnd!!)
                drawActionDrawableLeft(itemView, canvas, dXInt, swipeCallbackModelEnd)
            } else {
                drawBackgroundRight(itemView, canvas, dXInt, swipeCallbackModelEnd!!)
                drawActionDrawableRight(itemView, canvas, dXInt, swipeCallbackModelEnd)
            }
        }

        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun isSwipingStart(dX: Int): Boolean = (dX > 0 && isRtL) || (dX < 0 && !isRtL)

    //region draw background

    private fun drawBackgroundLeft(
            itemView: View,
            canvas: Canvas,
            dX: Int,
            swipeControllerModel: SwipeCallbackModel
    ) {
        drawBackground(
                left = itemView.right + dX,
                top = itemView.top,
                right = itemView.right,
                bottom = itemView.bottom,
                canvas = canvas,
                swipeControllerModel = swipeControllerModel
        )
    }

    private fun drawBackgroundRight(
            itemView: View,
            canvas: Canvas,
            dX: Int,
            swipeControllerModel: SwipeCallbackModel
    ) {
        drawBackground(
                left = itemView.left,
                top = itemView.top,
                right = itemView.left + dX,
                bottom = itemView.bottom,
                canvas = canvas,
                swipeControllerModel = swipeControllerModel
        )
    }

    private fun drawBackground(
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            canvas: Canvas,
            swipeControllerModel: SwipeCallbackModel
    ) {
        val backgroundDrawable = swipeControllerModel.backgroundDrawable
        backgroundDrawable.setBounds(left, top, right, bottom)
        backgroundDrawable.draw(canvas)
    }

    //endregion

    //region draw action drawable

    private fun drawActionDrawableLeft(
            itemView: View,
            canvas: Canvas,
            dX: Int,
            swipeControllerModel: SwipeCallbackModel
    ) {
        drawActionDrawable(itemView, canvas, dX, swipeControllerModel, true)
    }

    private fun drawActionDrawableRight(
            itemView: View,
            canvas: Canvas,
            dX: Int,
            swipeControllerModel: SwipeCallbackModel
    ) {
        drawActionDrawable(itemView, canvas, dX, swipeControllerModel, false)
    }

    private fun drawActionDrawable(
            itemView: View,
            canvas: Canvas,
            dX: Int,
            swipeControllerModel: SwipeCallbackModel,
            drawLeft: Boolean
    ) {
        val actionDrawable = swipeControllerModel.actionDrawable

        if (actionDrawable != null) {
            val itemHeight = itemView.bottom - itemView.top

            val intrinsicWidth = actionDrawable.intrinsicWidth
            val intrinsicHeight = actionDrawable.intrinsicHeight

            val margin = swipeControllerModel.actionMargin

            val actionDrawableLeft = if (drawLeft) itemView.right - (margin + intrinsicWidth) else itemView.left + margin
            val actionDrawableRight = if (drawLeft) itemView.right - margin else itemView.left + margin + intrinsicWidth
            val actionDrawableTop = itemView.top + (itemHeight - intrinsicHeight) / 2
            val actionDrawableBottom = actionDrawableTop + intrinsicHeight
            actionDrawable.setBounds(actionDrawableLeft, actionDrawableTop, actionDrawableRight, actionDrawableBottom)

            val clipLeft = if (drawLeft) itemView.right + dX else itemView.left
            val clipTop = itemView.top
            val clipRight = if (drawLeft) itemView.right else itemView.left + dX
            val clipBottom = itemView.bottom

            canvas.clipRect(clipLeft, clipTop, clipRight, clipBottom)

            actionDrawable.draw(canvas)
        }
    }

    //endregion

}
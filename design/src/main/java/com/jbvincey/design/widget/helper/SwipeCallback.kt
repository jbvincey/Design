package com.jbvincey.design.widget.helper

import android.content.Context
import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.END
import android.support.v7.widget.helper.ItemTouchHelper.START
import android.view.View

/**
 * Created by jbvincey on 22/11/2018.
 * SwipeCallback is a {@link ItemTouchHelper.SimpleCallback} to be passed to {@link ItemTouchHelper}
 * (in constructor), that can be attached to a {@link RecyclerView} to enable item swipe.
 * @param swipeCallbackModelStart a {@link SwipeCallbackModel} defining view to draw behind item on swiping start
 * @param swipeCallbackModelEnd a {@link SwipeCallbackModel} defining view to draw behind item on swiping end
 * @param context any context (application or activity), used to access resources
 */
class SwipeCallback(private val swipeCallbackModelStart: SwipeCallbackModel? = null,
                    private val swipeCallbackModelEnd: SwipeCallbackModel? = null,
                    context: Context
) : ItemTouchHelper.SimpleCallback(0,
        (if (swipeCallbackModelStart != null) START else 0) or (if (swipeCallbackModelEnd != null) END else 0)
) {

    private val isRtL = context.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL

    override fun onMove(recyclerView: RecyclerView,
                        viewHolder1: RecyclerView.ViewHolder,
                        viewHolder2: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder,
                          direction: Int) {
        if (direction == ItemTouchHelper.START) {
            swipeCallbackModelStart?.swipeControllerListener?.onViewSwiped(viewHolder.itemView)
        } else {
            swipeCallbackModelEnd?.swipeControllerListener?.onViewSwiped(viewHolder.itemView)
        }
    }

    override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val itemView = viewHolder.itemView
        return if (itemView is SwipeableView) {
            (if (itemView.isSwipeableStart()) ItemTouchHelper.START else 0) or (if (itemView.isSwipeableEnd()) ItemTouchHelper.END else 0)
        } else {
            super.getSwipeDirs(recyclerView, viewHolder)
        }
    }

    override fun onChildDraw(c: Canvas,
                             recyclerView: RecyclerView,
                             viewHolder: RecyclerView.ViewHolder,
                             dX: Float,
                             dY: Float,
                             actionState: Int,
                             isCurrentlyActive: Boolean) {

        val itemView = viewHolder.itemView

        val dXInt = dX.toInt()
        if (isSwipingStart(dXInt)) {
            if (isRtL) {
                drawBackgroundRight(itemView, c, dXInt, swipeCallbackModelStart!!)
                drawActionDrawableRight(itemView, c, swipeCallbackModelStart)
            } else {
                drawBackgroundLeft(itemView, c, dXInt, swipeCallbackModelStart!!)
                drawActionDrawableLeft(itemView, c, swipeCallbackModelStart)
            }
        } else {
            if (isRtL) {
                drawBackgroundLeft(itemView, c, dXInt, swipeCallbackModelEnd!!)
                drawActionDrawableLeft(itemView, c, swipeCallbackModelEnd)
            } else {
                drawBackgroundRight(itemView, c, dXInt, swipeCallbackModelEnd!!)
                drawActionDrawableRight(itemView, c, swipeCallbackModelEnd)
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun isSwipingStart(dX: Int): Boolean {
        return (dX > 0 && isRtL) || (dX < 0 && !isRtL)
    }

    //region draw background

    private fun drawBackgroundLeft(itemView: View, canvas: Canvas, dX: Int, swipeControllerModel: SwipeCallbackModel) {
        drawBackground(itemView.right + dX, itemView.top, itemView.right, itemView.bottom, canvas, swipeControllerModel)
    }

    private fun drawBackgroundRight(itemView: View, canvas: Canvas, dX: Int, swipeControllerModel: SwipeCallbackModel) {
        drawBackground(itemView.left, itemView.top, itemView.left + dX, itemView.bottom, canvas, swipeControllerModel)
    }

    private fun drawBackground(left: Int, top: Int, right: Int, bottom: Int, canvas: Canvas, swipeControllerModel: SwipeCallbackModel) {
        val backgroundDrawable = swipeControllerModel.backgroundDrawable
        backgroundDrawable.setBounds(left, top, right, bottom)
        backgroundDrawable.draw(canvas)
    }

    //endregion

    //region draw action drawable

    private fun drawActionDrawableLeft(itemView: View, canvas: Canvas, swipeControllerModel: SwipeCallbackModel) {
        drawActionDrawable(itemView, canvas, swipeControllerModel, true)
    }

    private fun drawActionDrawableRight(itemView: View, canvas: Canvas, swipeControllerModel: SwipeCallbackModel) {
        drawActionDrawable(itemView, canvas, swipeControllerModel, false)
    }

    private fun drawActionDrawable(itemView: View, canvas: Canvas, swipeControllerModel: SwipeCallbackModel, drawLeft: Boolean) {
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

            actionDrawable.draw(canvas)
        }
    }

    //endregion

}
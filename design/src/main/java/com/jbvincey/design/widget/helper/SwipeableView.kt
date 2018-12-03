package com.jbvincey.design.widget.helper

/**
 * Created by jbvincey on 22/11/2018.
 * View hold by viewholder can implement this SwipeableView, in order to enable swipe on start / end
 * depending on logic specific to the view (e.g. only some type of viewholders can be swiped).
 */
interface SwipeableView {

    fun isSwipeableStart(): Boolean

    fun isSwipeableEnd(): Boolean

}

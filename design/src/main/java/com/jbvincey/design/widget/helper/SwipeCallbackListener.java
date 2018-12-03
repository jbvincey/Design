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

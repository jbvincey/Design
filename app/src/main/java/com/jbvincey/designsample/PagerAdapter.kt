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

package com.jbvincey.designsample

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.jbvincey.designsample.fragments.SwipeCallbackFragment
import com.jbvincey.designsample.fragments.ValidationInputEditTextFragment

/**
 * Created by jbvincey on 03/12/2018.
 */
class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {
        const val PAGER_FRAGMENT_COUNT = 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ValidationInputEditTextFragment()
            1 -> SwipeCallbackFragment()
            else -> throw RuntimeException("PagerAdapter should have only $PAGER_FRAGMENT_COUNT items")
        }
    }

    override fun getCount(): Int {
        return PAGER_FRAGMENT_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Validation"
            1 -> "Swipe"
            else -> throw RuntimeException("PagerAdapter should have only $PAGER_FRAGMENT_COUNT items")
        }
    }

}
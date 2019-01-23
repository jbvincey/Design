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

package com.jbvincey.designsample.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.jbvincey.design.widget.helper.SwipeCallback
import com.jbvincey.design.widget.helper.SwipeCallbackListener
import com.jbvincey.design.widget.helper.SwipeCallbackModel
import com.jbvincey.designsample.R

/**
 * Created by jbvincey on 03/12/2018.
 */
class SwipeCallbackFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_swipecallback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<RecyclerView>(R.id.swipecallbackRecyclerView).apply {
            setHasFixedSize(true)
            buildItemTouchHelper(context).attachToRecyclerView(this)
            layoutManager = LinearLayoutManager(view.context)
            adapter = SwipeAdapter(arrayOf("Item 1", "Item 2", "Item 3"))
        }
    }

    private fun buildItemTouchHelper(context: Context): ItemTouchHelper {
        val color = ContextCompat.getColor(context, R.color.colorAccent)
        val margin = context.resources.getDimensionPixelSize(R.dimen.margin_default)

        val swipeCallbackModelStart = SwipeCallbackModel(
                color,
                ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_white_24px),
                margin,
                SwipeCallbackListener { _ -> Toast.makeText(context, "on item swiped start", Toast.LENGTH_SHORT).show() }
        )

        val swipeControllerModelEnd = SwipeCallbackModel(
                color,
                ContextCompat.getDrawable(context, R.drawable.ic_baseline_archive_white_24px),
                margin,
                SwipeCallbackListener { _ -> Toast.makeText(context, "on item swiped end", Toast.LENGTH_SHORT).show() }
        )

        return ItemTouchHelper(SwipeCallback(swipeCallbackModelStart, swipeControllerModelEnd, context))
    }
}

class SwipeAdapter(private val myDataset: Array<String>) :
        RecyclerView.Adapter<SwipeAdapter.SwipeViewHolder>() {

    class SwipeViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): SwipeAdapter.SwipeViewHolder {
        return SwipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_swipe, parent, false))
    }

    override fun onBindViewHolder(holder: SwipeViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.itemSwipeText).text = myDataset[position]
    }

    override fun getItemCount() = myDataset.size
}
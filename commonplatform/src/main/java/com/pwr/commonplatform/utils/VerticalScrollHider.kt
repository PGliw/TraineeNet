package com.pwr.commonplatform.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.pwr.commonplatform.R

class VerticalScrollHider(private vararg val viewsToManage : View) : RecyclerView.OnScrollListener(){

    companion object {
        const val SCROLL_UP_THRESHOLD = -150
        const val SCROLL_DOWN_THRESHOLD = 150
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if(dy < SCROLL_UP_THRESHOLD){
            for(view in viewsToManage) view.visibility = View.VISIBLE
        }
        else if(dy > SCROLL_DOWN_THRESHOLD){
            for(view in viewsToManage) view.visibility = View.GONE
        }

    }
}


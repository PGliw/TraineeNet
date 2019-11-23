package com.pwr.trainwithme

import android.view.View
import androidx.recyclerview.widget.RecyclerView

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
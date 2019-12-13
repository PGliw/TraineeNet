package com.pwr.trainwithme.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.pwr.trainwithme.R
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(
    private val context: Context,
    val imagesUrls: Array<out String>
) : SliderViewAdapter<SliderAdapter.SlideViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup): SlideViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_slide, parent, false)
        return SlideViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: SlideViewHolder, position: Int) {
        Glide.with(context)
            .load(imagesUrls[position])
            .into(viewHolder.image)
    }

    override fun getCount(): Int = imagesUrls.size

    class SlideViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.slide_image)
    }
}
package com.pwr.trainwithme.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pwr.trainwithme.Detailable
import com.pwr.trainwithme.R
import com.pwr.trainwithme.Summarisable
import com.pwr.trainwithme.TrainerOfferFacade

class DetailableAdapter(
    private val context: Context,
    items: Array<out Detailable>,
    private val onItemSelectedListener: OnItemSelectedListener,
    private val imageType: ImageType = ImageType.SQUARE
) : RecyclerView.Adapter<DetailableAdapter.DetailedCardViewHolder>() {


    enum class ImageType{ CIRCLE, SQUARE }

    var items = items
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailedCardViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(
            myLayout,
            parent,
            false
        ) as CardView
        return DetailedCardViewHolder(cardView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DetailedCardViewHolder, position: Int) {
        holder.title.text = items[position].title
        holder.rating.text = items[position].rating.toString()
        holder.price?.text = items[position].price
        val glideOptions = when(imageType){
            ImageType.CIRCLE -> RequestOptions.circleCropTransform()
            else -> RequestOptions()
        }
        Glide.with(context).load(items[position].imageUrl).apply(glideOptions).into(holder.image)
        holder.cardView.setOnClickListener {
            onItemSelectedListener.onItemSelected(items[position])
        }
    }

    private val myLayout: Int
        get() = if (items.isNotEmpty()) {
            when (items[0]) {
                is TrainerOfferFacade -> R.layout.offer_card
                else -> R.layout.centre_card
            }
        } else R.layout.centre_card

    class DetailedCardViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        val image: ImageView = cardView.findViewById(R.id.image_left_side)
        val title: TextView = cardView.findViewById(R.id.text_title)
        val rating: TextView = cardView.findViewById(R.id.text_subtitle)
        val price: TextView? = cardView.findViewById(R.id.text_extra)
    }

    interface OnItemSelectedListener {
        fun onItemSelected(summary: Summarisable)
    }
}


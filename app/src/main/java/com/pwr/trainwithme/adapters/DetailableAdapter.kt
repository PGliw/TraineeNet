package com.pwr.trainwithme.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pwr.trainwithme.Detailable
import com.pwr.trainwithme.R
import com.pwr.trainwithme.Summarisable
import com.pwr.trainwithme.TrainerVM

class DetailableAdapter(
    private val context: Context,
    items: List<Detailable>,
    private val onItemSelectedListener: OnItemSelectedListener,
    private val onMoreInfoListener: OnMoreInfoListener? = null,
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
            R.layout.offer_card,
            parent,
            false
        ) as CardView
        return DetailedCardViewHolder(cardView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DetailedCardViewHolder, position: Int) {
        holder.bind(items[position], context, onItemSelectedListener, onMoreInfoListener, imageType)
    }

    class DetailedCardViewHolder(private val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        private val image: ImageView = cardView.findViewById(R.id.image_left_side)
        private val title: TextView = cardView.findViewById(R.id.text_title)
        private val rating: TextView = cardView.findViewById(R.id.text_subtitle)
        private val price: TextView = cardView.findViewById(R.id.text_extra)
        private val moreInfoButton: ImageButton = cardView.findViewById(R.id.button_info_offer_card)
        fun bind(item: Detailable,
                 context: Context,
                 onItemSelectedListener: OnItemSelectedListener,
                 onMoreInfoListener: OnMoreInfoListener?,
                 imageType: ImageType){
            val glideOptions = when(imageType){
                ImageType.CIRCLE -> RequestOptions.circleCropTransform()
                else -> RequestOptions.centerCropTransform()
            }
            title.text = item.title
            rating.text = item.rating.toString()
            Glide.with(context).load(item.imageUrl).apply(glideOptions).into(image)
            when(item.price){
                null -> price.visibility = View.GONE
                else -> {
                    price.text = item.price
                    price.visibility = View.VISIBLE
                }
            }
            when(onMoreInfoListener){
                null -> moreInfoButton.visibility = View.GONE
                else -> {
                    moreInfoButton.setOnClickListener {
                        onMoreInfoListener.onMoreInfo(item)
                    }
                    moreInfoButton.visibility = View.VISIBLE
                }
            }
            cardView.setOnClickListener {
                onItemSelectedListener.onItemSelected(item)
            }
        }
    }

    interface OnItemSelectedListener {
        fun onItemSelected(detailable: Detailable)
    }

    interface OnMoreInfoListener {
        fun onMoreInfo(detailable: Detailable)
    }
}


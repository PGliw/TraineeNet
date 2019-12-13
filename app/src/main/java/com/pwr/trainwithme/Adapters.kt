package com.pwr.trainwithme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.smarteist.autoimageslider.SliderViewAdapter

class SummaryAdapter(
    private val context: Context,
    summaries: Array<out Summarisable>,
    private val onSummarySelectedListener: OnSummarySelectedListener,
    private val cardType: Int = THIN
) : RecyclerView.Adapter<SummaryAdapter.SummaryCardViewHolder>() {

    companion object CardType {
        const val THIN = R.layout.summary_card_thin
        const val MEDIUM = R.layout.summary_card_medium
        const val WIDE = R.layout.summary_card_wide
    }

    var summaries = summaries
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryCardViewHolder {
        val cardView =
            LayoutInflater.from(parent.context).inflate(cardType, parent, false) as CardView
        return SummaryCardViewHolder(cardView)
    }

    override fun getItemCount(): Int = summaries.size

    override fun onBindViewHolder(holder: SummaryCardViewHolder, position: Int) {
        holder.title.text = summaries[position].title
        val glideOptions = RequestOptions().apply { centerCrop() }
        Glide.with(context).load(summaries[position].imageUrl).apply(glideOptions)
            .into(holder.image)
        holder.cardView.setOnClickListener {
            onSummarySelectedListener.onSummarySelected(summaries[position])
        }
    }

    class SummaryCardViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        val image: ImageView = cardView.findViewById(R.id.image_left_side)
        val title: TextView = cardView.findViewById(R.id.text_title)
    }
}

interface OnSummarySelectedListener {
    fun onSummarySelected(summary: Summarisable)
}

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
}

interface OnItemSelectedListener {
    fun onItemSelected(summary: Summarisable)
}

class SliderAdapter(
    private val context: Context,
    private val imagesUrls: Array<out String>
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

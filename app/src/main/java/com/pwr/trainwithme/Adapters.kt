package com.pwr.trainwithme

import android.content.Context
import android.media.Image
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
    summaries : Array<Summarisable>,
    val onSummarySelectedListener: OnSummarySelectedListener,
    val cardType : Int = THIN
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
        val cardView = LayoutInflater.from(parent.context).inflate(cardType, parent, false) as CardView
        return SummaryCardViewHolder(cardView)
    }

    override fun getItemCount(): Int = summaries.size

    override fun onBindViewHolder(holder: SummaryCardViewHolder, position: Int) {
        holder.title.text = summaries[position].title
        val glideOptions = RequestOptions().apply { centerCrop() }
        Glide.with(context).load(summaries[position].imageUrl).apply(glideOptions).into(holder.image)
        holder.cardView.setOnClickListener{
            onSummarySelectedListener.onSummarySelected(summaries[position])
        }
    }

    class SummaryCardViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView){
        val image: ImageView = cardView.findViewById(R.id.background_image)
        val title: TextView = cardView.findViewById(R.id.title)
    }
}

interface OnSummarySelectedListener{
    fun onSummarySelected(summary : Summarisable)
}

class OfferAdapter(
    private val context: Context,
    offers : Array<Offer>,
    val onOfferSelectedListener: OnOfferSelectedListener
) : RecyclerView.Adapter<OfferAdapter.OfferCardViewHolder>() {


    var offers = offers
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferCardViewHolder{
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.offer_card, parent, false) as CardView
        return OfferCardViewHolder(cardView)
    }

    override fun getItemCount(): Int = offers.size

    override fun onBindViewHolder(holder: OfferCardViewHolder, position: Int) {
        holder.title.text = offers[position].title
        holder.rating.text = offers[position].rating.toString()
        holder.price.text = offers[position].price
        val glideOptions = RequestOptions.circleCropTransform()
        Glide.with(context).load(offers[position].imageUrl).apply(glideOptions).into(holder.image)
        holder.cardView.setOnClickListener{
            onOfferSelectedListener.onOfferSelected(offers[position])
        }
    }

    class OfferCardViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView){
        val image: ImageView = cardView.findViewById(R.id.background_image)
        val title: TextView = cardView.findViewById(R.id.title)
        val rating: TextView = cardView.findViewById(R.id.rating)
        val price: TextView = cardView.findViewById(R.id.price)

    }
}

interface OnOfferSelectedListener{
    fun onOfferSelected(summary : Summarisable)
}

class SliderAdapter(
    private val context: Context,
    private val imagesUrls : Array<String>)
    : SliderViewAdapter<SliderAdapter.SlideViewHolder>(){
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

    class SlideViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView){
        val image : ImageView= itemView.findViewById(R.id.slide_image)
    }

}
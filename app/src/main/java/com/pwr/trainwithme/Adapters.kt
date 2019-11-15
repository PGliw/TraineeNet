package com.pwr.trainwithme

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CardStackAdapter(
    private val context: Context,
    var offers : Array<Offer>) : RecyclerView.Adapter<CardStackAdapter.CardViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.offer_card, parent, false) as CardView
        return CardViewHolder(cardView)
    }

    override fun getItemCount(): Int = offers.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val newOffer = offers[position]
        holder.title.text =  newOffer.title
        holder.description.text = newOffer.description
        Glide.with(context).load(newOffer.imageUrl).into(holder.image)
    }

    class CardViewHolder(cardView: CardView) : RecyclerView.ViewHolder(cardView){
        val image: ImageView = cardView.findViewById(R.id.offer_image)
        val title: TextView = cardView.findViewById(R.id.offer_title)
        val description: TextView = cardView.findViewById(R.id.offer_description)
    }
}

class SportsAdapter(
    private val context: Context,
    sports : Array<Sport>
) : RecyclerView.Adapter<SportsAdapter.SportCardViewHolder>() {

    var sports = sports
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportCardViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.sport_card, parent, false) as CardView
        return SportCardViewHolder(cardView)
    }

    override fun getItemCount(): Int = sports.size

    override fun onBindViewHolder(holder: SportCardViewHolder, position: Int) {
        holder.title.text = sports[position].name
        val glideOptions = RequestOptions().apply { centerCrop() }
        Glide.with(context).load(sports[position].imageUrl).apply(glideOptions).into(holder.image)
    }

    class SportCardViewHolder(cardView: CardView) : RecyclerView.ViewHolder(cardView){
        val image: ImageView = cardView.findViewById(R.id.background_image)
        val title: TextView = cardView.findViewById(R.id.title)
    }
}
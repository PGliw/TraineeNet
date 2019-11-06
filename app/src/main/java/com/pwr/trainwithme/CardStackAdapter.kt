package com.pwr.trainwithme

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CardStackAdapter(
    private val context: Context,
    val offers : Array<Offer>) : RecyclerView.Adapter<CardStackAdapter.CardViewHolder>(){
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
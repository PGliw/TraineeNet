package com.pwr.trainwithme

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

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
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
import com.pwr.trainwithme.R
import com.pwr.commonplatform.data.TrainerOverview

class TrainerOverviewAdapter(
    private val context: Context,
    items: List<TrainerOverview>,
    private val onItemSelected: (TrainerOverview) -> Unit
) : RecyclerView.Adapter<TrainerOverviewAdapter.TrainerOverviewViewHolder>() {


    var items = items
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainerOverviewViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(
            R.layout.trainer_overview_card,
            parent,
            false
        ) as CardView
        return TrainerOverviewViewHolder(cardView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TrainerOverviewViewHolder, position: Int) {
        holder.bind(items[position], context, onItemSelected)
    }

    class TrainerOverviewViewHolder(private val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        private val image: ImageView = cardView.findViewById(R.id.image_left_side)
        private val title: TextView = cardView.findViewById(R.id.text_title)
        private val subtitle: TextView = cardView.findViewById(R.id.text_subtitle)
        private val extraText: TextView = cardView.findViewById(R.id.text_extra)
        fun bind(item: TrainerOverview,
                 context: Context,
                 onItemSelected: (TrainerOverview) -> Unit
                 ){
            val glideOptions = RequestOptions.circleCropTransform()
            val names = "${item.firstName} ${item.lastName}"
            val opinions = when {
                item.meanGrade == null || item.opinionsCount == 0 -> context.getString(R.string.no_opinions_yet)
                else -> "${item.meanGrade} (${item.opinionsCount} ${context.getString(R.string.opinions_singular_plural)})"
            }
            title.text = names
            subtitle.text = opinions
            Glide.with(context).load(item.photoUrl).apply(glideOptions).into(image)
            val sports = when(item.sports.size) {
                0 -> context.getString(R.string.no_sports)
                in 1..3 -> item.sports.toString()
                else -> {
                    val firstThreeSports = item.sports.subList(0, 2)
                    val nMore = item.sports.size - firstThreeSports.size
                    "$firstThreeSports ${context.getString(R.string.and)} $nMore ${context.getString(R.string.more)}"
                }
            }
            extraText.text = sports
            cardView.setOnClickListener {
                onItemSelected(item)
            }
        }
    }
}


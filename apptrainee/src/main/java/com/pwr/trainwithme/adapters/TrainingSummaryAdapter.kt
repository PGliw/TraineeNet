package com.pwr.trainwithme.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pwr.trainwithme.R
import com.pwr.trainwithme.data.CentreOverview
import com.pwr.trainwithme.data.TrainingSummary

class TrainingSummaryAdapter(
    private val context: Context,
    items: List<TrainingSummary>,
    private val onItemSelected: (TrainingSummary) -> Unit
) : RecyclerView.Adapter<TrainingSummaryAdapter.TrainingSummaryViewHolder>() {


    var items = items
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingSummaryViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(
            R.layout.training_short_summary_card,
            parent,
            false
        ) as CardView
        return TrainingSummaryViewHolder(cardView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TrainingSummaryViewHolder, position: Int) {
        holder.bind(items[position], context, onItemSelected)
    }

    class TrainingSummaryViewHolder(private val cardView: CardView) :
        RecyclerView.ViewHolder(cardView) {
        private val status: TextView = cardView.findViewById(R.id.text_training_summary_status)
        private val sportImage: ImageView = cardView.findViewById(R.id.image_training_summary_sport)
        private val centre: TextView = cardView.findViewById(R.id.text_training_summary_centre)
        private val date: TextView = cardView.findViewById(R.id.text_training_summary_date)
        private val sportName: TextView = cardView.findViewById(R.id.text_training_summary_sport)
        fun bind(
            item: TrainingSummary,
            context: Context,
            onItemSelected: (TrainingSummary) -> Unit
        ) {

            status.text = item.trainingStatus
            centre.text = item.centreName
            // TODO convert to date and then format to locale string
            date.text = item.trainingStartDateTime
            sportName.text = item.sportName
            Glide.with(context)
                .load(item.sportPhotoUrl)
                .into(sportImage)

            cardView.setOnClickListener {
                onItemSelected(item)
            }
        }
    }
}


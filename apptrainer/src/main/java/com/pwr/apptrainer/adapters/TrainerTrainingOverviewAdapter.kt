package com.pwr.apptrainer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pwr.apptrainer.R
import com.pwr.commonplatform.data.model.trainer.TrainerTrainingOverview

class TrainerTrainingOverviewAdapter(
    private val context: Context,
    items: List<TrainerTrainingOverview>,
    private val onItemSelected: (TrainerTrainingOverview) -> Unit,
    private val onSwipeLeft: (TrainerTrainingOverview) -> Unit,
    private val onSwipeRight: (TrainerTrainingOverview) -> Unit
) : RecyclerView.Adapter<TrainerTrainingOverviewAdapter.TrainerTrainingSummaryViewHolder>() {


    var items = items
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainerTrainingSummaryViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(
            R.layout.training_overview_card,
            parent,
            false
        ) as CardView
        return TrainerTrainingSummaryViewHolder(cardView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TrainerTrainingSummaryViewHolder, position: Int) {
        holder.bind(items[position], context, onItemSelected, onSwipeLeft, onSwipeRight)
    }

    class TrainerTrainingSummaryViewHolder(private val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        private val image: ImageView = cardView.findViewById(R.id.image_left_side)
        private val title: TextView = cardView.findViewById(R.id.text_title)
        private val subtitle: TextView = cardView.findViewById(R.id.text_subtitle)
        private val extraText1: TextView = cardView.findViewById(R.id.text_extra_1)
        private val extraText2: TextView = cardView.findViewById(R.id.text_extra_2)
        fun bind(item: TrainerTrainingOverview,
                 context: Context,
                 onItemSelected: (TrainerTrainingOverview) -> Unit,
                 onSwipeLeft: (TrainerTrainingOverview) -> Unit,
                 onSwipeRight: (TrainerTrainingOverview) -> Unit
        ){
            val glideOptions = RequestOptions.circleCropTransform()
            val occupancy = "${item.numberOfTrainees} / ${item.traineesLimit}"
            val dates = "${item.startDateTime} - ${item.endDateTime}"
            Glide.with(context).load(item.photoUrl).apply(glideOptions).into(image)
            title.text = occupancy
            subtitle.text = dates
            extraText1.text = item.centreName
            extraText2.text = item.sportName
            cardView.setOnClickListener {
                onItemSelected(item)
            }
        }
    }
}


package com.pwr.trainwithme.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pwr.trainwithme.R
import com.pwr.trainwithme.data.CentreOverview

class CentreOverviewAdapter(
    private val context: Context,
    items: List<CentreOverview>,
    private val onItemSelected: (CentreOverview) -> Unit,
    private val onMoreInfoClicked: (CentreOverview) -> Unit
) : RecyclerView.Adapter<CentreOverviewAdapter.CentreOverviewViewHolder>() {


    var items = items
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CentreOverviewViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(
            R.layout.centre_overview_card,
            parent,
            false
        ) as CardView
        return CentreOverviewViewHolder(cardView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CentreOverviewViewHolder, position: Int) {
        holder.bind(items[position], context, onItemSelected, onMoreInfoClicked)
    }

    class CentreOverviewViewHolder(private val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        private val image: ImageView = cardView.findViewById(R.id.image_left_side)
        private val title: TextView = cardView.findViewById(R.id.text_title)
        private val subtitle: TextView = cardView.findViewById(R.id.text_subtitle)
        private val extraText: TextView = cardView.findViewById(R.id.text_extra)
        private val moreInfoButton: ImageButton = cardView.findViewById(R.id.button_info_centre_overview_card)
        fun bind(item: CentreOverview,
                 context: Context,
                 onItemSelected: (CentreOverview) -> Unit,
                 onMoreInfoClicked: (CentreOverview) -> Unit
                 ){
            title.text = item.name
            // TODO calc distance
            val dist = "(${item.latitude} ${item.longitude})"
            subtitle.text = dist
            Glide.with(context).load(item.photoUrl).into(image)
            cardView.setOnClickListener {
                onItemSelected(item)
            }
            moreInfoButton.setOnClickListener {
                onMoreInfoClicked(item)
            }
        }
    }
}


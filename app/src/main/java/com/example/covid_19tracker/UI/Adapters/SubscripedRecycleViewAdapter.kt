package com.example.covid_19tracker.UI.Adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.example.covid_19tracker.Database.CountyEntity
import com.example.covid_19tracker.R

class SubscripedRecycleViewAdapter(dataSet: MutableList<CountyEntity>)
    : DragDropSwipeAdapter<CountyEntity, SubscripedRecycleViewAdapter.ViewHolder>((dataSet)) {

    class ViewHolder(itemView: View) : DragDropSwipeAdapter.ViewHolder(itemView) {
        val countryText: TextView = itemView.findViewById(R.id.countryNameTextView)
        val totalCasesText: TextView = itemView.findViewById(R.id.countryTotalCasesTextView)
        val countryImage: ImageView = itemView.findViewById(R.id.countryImageView)
    }

    override fun getViewHolder(itemLayout: View) = SubscripedRecycleViewAdapter.ViewHolder(itemLayout)

    override fun onBindViewHolder(item: CountyEntity, viewHolder: SubscripedRecycleViewAdapter.ViewHolder, position: Int) {
        // Here we update the contents of the view holder's views to reflect the item's data
        viewHolder.countryText.text = item.country
        viewHolder.totalCasesText.text = "Total Cases: ${item.cases.toString()}"
        Glide.with(viewHolder.itemView)
            .load(item.countryInfo.flag)
            .placeholder(R.drawable.sick_person)
            .fitCenter()
            .into(viewHolder.countryImage);
    }

    override fun getViewToTouchToStartDraggingItem(item: CountyEntity, viewHolder: SubscripedRecycleViewAdapter.ViewHolder, position: Int): View? {
        // We return the view holder's view on which the user has to touch to drag the item
        return viewHolder.itemView
    }
}
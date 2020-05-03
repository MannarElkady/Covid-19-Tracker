package com.example.covid_19tracker.ui.adapters;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19tracker.domain.CountryModel
import com.example.covid_19tracker.databinding.CountryItemBinding

class CountryAdapter(val clickListener: CountryListener) : ListAdapter<CountryModel,
        CountryAdapter.ViewHolder>(CountryDiffUtil()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

       holder.bind(item,clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CountryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CountryModel,clickListener: CountryListener) {
            binding.country = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CountryItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class CountryDiffUtil : DiffUtil.ItemCallback<CountryModel>() {
    override fun areItemsTheSame(oldItem: CountryModel, newItem: CountryModel): Boolean {
        return oldItem.country==newItem.country
    }

    override fun areContentsTheSame(oldItem: CountryModel, newItem: CountryModel): Boolean {
        return oldItem == newItem
    }
}
class CountryListener(val clickListener: (countryName: String) -> Unit) {
    fun onClick(country: CountryModel) = clickListener(country.country)
}


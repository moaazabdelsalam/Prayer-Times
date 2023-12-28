package com.task.praytimes.times.presentation.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.task.praytimes.databinding.ItemTimeBinding
import com.task.praytimes.times.presentation.models.Prayer

class TimesAdapter : ListAdapter<Prayer, TimesAdapter.TimesViewHolder>(TimesDiffUtil()) {
    private lateinit var binding: ItemTimeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimesViewHolder {
        val inflater: LayoutInflater = parent
            .context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemTimeBinding.inflate(inflater, parent, false)
        return TimesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimesViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            prayerNameTxt.text = item.name
            prayerTimeTxt.text = item.time
        }
    }

    class TimesViewHolder(val binding: ItemTimeBinding) : RecyclerView.ViewHolder(binding.root)
}

class TimesDiffUtil : DiffUtil.ItemCallback<Prayer>() {
    override fun areItemsTheSame(oldItem: Prayer, newItem: Prayer) =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: Prayer, newItem: Prayer) =
        oldItem == newItem

}
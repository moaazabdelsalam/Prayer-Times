package com.task.praytimes.times.presentation.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.task.praytimes.databinding.ItemDateBinding
import com.task.praytimes.times.presentation.models.DayDate

class DatesAdapter(
    private val listener: (String) -> Unit
) :
    ListAdapter<DayDate, DatesAdapter.DatesViewHolder>(DatesDiffUtil()) {
    private lateinit var binding: ItemDateBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatesViewHolder {
        val inflater: LayoutInflater = parent
            .context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemDateBinding.inflate(inflater, parent, false)
        return DatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DatesViewHolder, position: Int) {
        val item = getItem(position)
        binding.apply {
            dayNameTxt.text = item.dayOfWeek
            dayOfMonthTxt.text = item.fullDate.split("-")[0]
            dateItem.setOnClickListener { listener(item.fullDate) }
        }
    }

    class DatesViewHolder(binding: ItemDateBinding) : RecyclerView.ViewHolder(binding.root)
}

class DatesDiffUtil : DiffUtil.ItemCallback<DayDate>() {
    override fun areItemsTheSame(oldItem: DayDate, newItem: DayDate) =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: DayDate, newItem: DayDate) =
        oldItem == newItem

}
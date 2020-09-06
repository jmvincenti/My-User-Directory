package com.jmvincenti.myuserdirectory.feature.userprofile.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.jmvincenti.myuserdirectory.databinding.GenericListItemBinding
import kotlin.properties.Delegates

class GenericAdapter(
    @DrawableRes private val iconRes: Int,
    @StringRes private val title: Int
) : RecyclerView.Adapter<GenericAdapter.GenericViewHolder>() {

    var value: String? by Delegates.observable(null as String?) { _, _, _ ->
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GenericViewHolder(
        GenericListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.bind(
            iconRes = iconRes,
            title = title,
            value = requireNotNull(value)
        )
    }

    override fun getItemCount(): Int = when (value.isNullOrBlank()) {
        true -> 0
        else -> 1
    }

    class GenericViewHolder(
        private val binding: GenericListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            @DrawableRes iconRes: Int,
            @StringRes title: Int,
            value: String
        ) {
            binding.genericHint.setText(title)
            binding.genericIcon.setImageResource(iconRes)
            binding.genericValue.text = value
        }
    }
}

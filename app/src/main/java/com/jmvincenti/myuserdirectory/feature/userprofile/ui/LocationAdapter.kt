package com.jmvincenti.myuserdirectory.feature.userprofile.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jmvincenti.myuserdirectory.R
import com.jmvincenti.myuserdirectory.databinding.UserLocationItemBinding
import com.jmvincenti.myuserdirectory.feature.userprofile.domain.CoordinateImageBuilder
import com.jmvincenti.myuserdirectory.model.Location
import kotlin.properties.Delegates

class LocationAdapter(
    private val coordinateImageBuilder: CoordinateImageBuilder
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    var location: Location? by Delegates.observable(null as Location?) { _, _, _ ->
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LocationViewHolder(
        UserLocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        coordinateImageBuilder
    )

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(requireNotNull(location))
    }

    override fun getItemCount(): Int = when (location) {
        null -> 0
        else -> 1
    }

    class LocationViewHolder(
        private val binding: UserLocationItemBinding,
        private val coordinateImageBuilder: CoordinateImageBuilder
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(location: Location) {
            binding.userLocationStreetValue.text = location.street.capitalize()
            binding.userLocationCityValue.text = location.city.capitalize()
            binding.userLocationStateValue.text = location.state.capitalize()
            binding.userLocationPostcodeValue.text = location.postcode.capitalize()

            when (location.coordinate) {
                null -> binding.userLocationImage.visibility = View.GONE
                else -> {
                    binding.userLocationImage.visibility = View.VISIBLE
                    Glide.with(binding.root.context)
                        .load(coordinateImageBuilder.build(coordinate = location.coordinate))
                        .placeholder(R.drawable.ic_baseline_map_24)
                        .centerCrop()
                        .into(binding.userLocationImage)
                }
            }
        }
    }
}

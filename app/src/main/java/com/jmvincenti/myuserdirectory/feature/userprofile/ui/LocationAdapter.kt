package com.jmvincenti.myuserdirectory.feature.userprofile.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmvincenti.myuserdirectory.databinding.UserLocationItemBinding
import com.jmvincenti.myuserdirectory.model.Location
import kotlin.properties.Delegates

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    var location: Location? by Delegates.observable(null as Location?) { _, _, _ ->
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LocationViewHolder(
        UserLocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(requireNotNull(location))
    }

    override fun getItemCount(): Int = when (location) {
        null -> 0
        else -> 1
    }

    class LocationViewHolder(private val biding: UserLocationItemBinding) :
        RecyclerView.ViewHolder(biding.root) {

        fun bind(location: Location) {
            biding.userLocationStreetValue.text = location.street
            biding.userLocationCityValue.text = location.city
            biding.userLocationStateValue.text = location.state
            biding.userLocationPostcodeValue.text = location.postcode

            biding.userLocationImage.visibility = View.GONE
        }
    }
}

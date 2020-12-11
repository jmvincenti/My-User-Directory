package com.jmvincenti.myuserdirectory.feature.userprofile.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jmvincenti.myuserdirectory.R
import com.jmvincenti.myuserdirectory.databinding.UserProfileCardBinding
import com.jmvincenti.myuserdirectory.model.User
import javax.inject.Inject
import kotlin.properties.Delegates

class UserProfileCardAdapter @Inject constructor() :
    RecyclerView.Adapter<UserProfileCardAdapter.CardViewHolder>() {

    var user: User? by Delegates.observable(null as User?) { _, _, _ ->
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CardViewHolder(
        UserProfileCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(requireNotNull(user))
    }

    override fun getItemCount(): Int = when (user) {
        null -> 0
        else -> 1
    }

    class CardViewHolder(
        private val binding: UserProfileCardBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.userName.text = user.fullName
            binding.userSubtitle.text = user.email

            Glide.with(binding.root.context)
                .load(user.pictures?.thumbnail)
                .circleCrop()
                .placeholder(R.drawable.ic_person)
                .into(binding.userThumbnail)
        }
    }
}

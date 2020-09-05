package com.jmvincenti.myuserdirectory.feature.userlist.ui.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jmvincenti.myuserdirectory.R
import com.jmvincenti.myuserdirectory.databinding.UserListItemBinding
import com.jmvincenti.myuserdirectory.model.User
import kotlin.properties.Delegates

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    var items: List<User> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class UserViewHolder(
        private val binding: UserListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

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

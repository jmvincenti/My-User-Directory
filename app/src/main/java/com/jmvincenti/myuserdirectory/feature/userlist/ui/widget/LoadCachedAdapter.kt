package com.jmvincenti.myuserdirectory.feature.userlist.ui.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmvincenti.myuserdirectory.databinding.LoadCachedViewBinding
import com.jmvincenti.statemachine.SimpleLoadingState
import kotlin.properties.Delegates

class LoadCachedAdapter : RecyclerView.Adapter<LoadCachedAdapter.LoadCachedHolder>() {

    var state: SimpleLoadingState
            by Delegates.observable(SimpleLoadingState.Idle as SimpleLoadingState) { _, _, _ ->
                notifyDataSetChanged()
            }

    var listener: (() -> Unit)? = null
    private val _listener: (() -> Unit) = {
        listener?.invoke()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LoadCachedHolder(
        LoadCachedViewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        _listener
    )

    override fun getItemCount(): Int = when (state) {
        SimpleLoadingState.Idle -> 0
        SimpleLoadingState.Loading -> 0
        is SimpleLoadingState.Error -> 1
    }

    override fun onBindViewHolder(holder: LoadCachedHolder, position: Int) {
        holder.bind(state)
    }

    class LoadCachedHolder(
        private val binding: LoadCachedViewBinding,
        private val listener: (() -> Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.errorButton.setOnClickListener { listener() }
        }

        fun bind(state: SimpleLoadingState) {
            when (state) {
                is SimpleLoadingState.Error -> {
                    binding.errorText.text = state.errorMessage.message
                }
            }
        }
    }
}

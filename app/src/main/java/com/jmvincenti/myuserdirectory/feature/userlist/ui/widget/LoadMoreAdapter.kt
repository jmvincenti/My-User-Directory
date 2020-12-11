package com.jmvincenti.myuserdirectory.feature.userlist.ui.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmvincenti.myuserdirectory.databinding.LoadMoreViewBinding
import com.jmvincenti.myuserdirectory.feature.userprofile.domain.CoordinateImageBuilder
import com.jmvincenti.statemachine.SimpleLoadingState
import javax.inject.Inject
import kotlin.properties.Delegates

class LoadMoreAdapter @Inject constructor(
    val builder : CoordinateImageBuilder
) :
    RecyclerView.Adapter<LoadMoreAdapter.LoadMoreHolder>() {

    var state: SimpleLoadingState
            by Delegates.observable(SimpleLoadingState.Idle as SimpleLoadingState) { _, _, _ ->
                notifyDataSetChanged()
            }

    var listener: (() -> Unit)? = null
    private val _listener: (() -> Unit) = {
        listener?.invoke()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LoadMoreHolder(
        LoadMoreViewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        _listener
    )

    override fun getItemCount(): Int = when (state) {
        SimpleLoadingState.Idle -> 0
        SimpleLoadingState.Loading -> 1
        is SimpleLoadingState.Error -> 1
    }

    override fun onBindViewHolder(holder: LoadMoreHolder, position: Int) {
        holder.bind(state)
    }

    class LoadMoreHolder(
        private val binding: LoadMoreViewBinding,
        private val listener: (() -> Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.errorButton.setOnClickListener { listener() }
        }

        fun bind(state: SimpleLoadingState) {
            when (state) {
                SimpleLoadingState.Loading ->
                    binding.viewAnimator.displayedChild =
                        binding.viewAnimator.indexOfChild(binding.progressBar)

                is SimpleLoadingState.Error -> {
                    binding.viewAnimator.displayedChild =
                        binding.viewAnimator.indexOfChild(binding.errorLayout)

                    binding.errorText.text = state.errorMessage.message
                }
            }
        }
    }
}

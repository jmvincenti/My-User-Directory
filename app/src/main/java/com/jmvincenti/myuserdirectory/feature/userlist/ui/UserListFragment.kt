package com.jmvincenti.myuserdirectory.feature.userlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmvincenti.myuserdirectory.databinding.UserListFragmentBinding
import com.jmvincenti.myuserdirectory.feature.userlist.model.UserListCommand
import com.jmvincenti.myuserdirectory.feature.userlist.model.UserListState
import com.jmvincenti.myuserdirectory.feature.userlist.ui.widget.UserAdapter
import com.jmvincenti.statemachine.SimpleLoadingState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserListFragment : Fragment() {

    companion object {
        fun newInstance() = UserListFragment()
    }

    @Inject
    lateinit var factory: UserListViewModel.Factory
    private lateinit var viewModel: UserListViewModel

    private var _binding: UserListFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter = UserAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = UserListFragmentBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.errorButton.setOnClickListener {
            viewModel.onCommand(UserListCommand.RequestRetry)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), factory).get(UserListViewModel::class.java)
        viewModel.state().observe(viewLifecycleOwner, Observer(this@UserListFragment::bind))
    }

    private var currentState: UserListState? = null
    private fun bind(state: UserListState) {

        // View animator
        val initialState = state.loadInitialState
        if (currentState?.loadInitialState != initialState) {
            binding.viewAnimator.displayedChild = when (initialState) {
                SimpleLoadingState.Idle ->
                    binding.viewAnimator.indexOfChild(binding.recyclerView)

                SimpleLoadingState.Loading ->
                    binding.viewAnimator.indexOfChild(binding.progressBar)

                is SimpleLoadingState.Error -> {
                    binding.errorText.text = initialState.errorMessage.message
                    if (state.userList.isEmpty()) {
                        binding.viewAnimator.indexOfChild(binding.errorLayout)
                    } else {
                        binding.viewAnimator.indexOfChild(binding.recyclerView)
                    }
                }
            }
        }

        // User list
        if (currentState?.userList != state.userList) {
            adapter.items = state.userList
        }

        currentState = state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

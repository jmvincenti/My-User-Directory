package com.jmvincenti.myuserdirectory.feature.userlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmvincenti.myuserdirectory.databinding.UserListFragmentBinding
import com.jmvincenti.myuserdirectory.feature.userlist.model.UserListCommand
import com.jmvincenti.myuserdirectory.feature.userlist.model.UserListState
import com.jmvincenti.myuserdirectory.feature.userlist.ui.widget.LoadCachedAdapter
import com.jmvincenti.myuserdirectory.feature.userlist.ui.widget.LoadMoreAdapter
import com.jmvincenti.myuserdirectory.feature.userlist.ui.widget.UserAdapter
import com.jmvincenti.myuserdirectory.ui.widget.EndlessRecyclerViewScrollListener
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

    private val userAdapter = UserAdapter()
    private val loadCachedAdapter = LoadCachedAdapter()
    private val loadMoreAdapter = LoadMoreAdapter()

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

        binding.recyclerView.apply {
            val layoutManager = LinearLayoutManager(context)

            this.layoutManager = layoutManager
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    viewModel.onCommand(UserListCommand.RequestLoadMore)
                }
            })

            adapter = ConcatAdapter(
                loadCachedAdapter,
                userAdapter,
                loadMoreAdapter
            )
        }

        userAdapter.listener = {
            // TODO open user details screen
        }

        loadCachedAdapter.listener = {
            viewModel.onCommand(UserListCommand.RequestRetry)
        }

        loadMoreAdapter.listener = {
            viewModel.onCommand(UserListCommand.RequestRetryLoadMore)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), factory).get(UserListViewModel::class.java)
        viewModel.state().observe(viewLifecycleOwner, Observer(this@UserListFragment::bind))
    }

    private var currentState: UserListState? = null
    private fun bind(state: UserListState) {

        // Cached adapter
        if (currentState?.loadInitialState != state.loadInitialState) {
            loadCachedAdapter.state = state.loadInitialState
        }

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
            userAdapter.items = state.userList
        }

        // Load more
        if (currentState?.loadMoreState != state.loadMoreState) {
            loadMoreAdapter.state = state.loadMoreState
        }

        currentState = state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.jmvincenti.myuserdirectory.feature.userlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jmvincenti.myuserdirectory.databinding.UserListFragmentBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = UserListFragmentBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(UserListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

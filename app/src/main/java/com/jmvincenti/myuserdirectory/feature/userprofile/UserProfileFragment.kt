package com.jmvincenti.myuserdirectory.feature.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jmvincenti.myuserdirectory.R
import com.jmvincenti.myuserdirectory.feature.userprofile.model.UserProfileState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    companion object {
        fun newInstance() = UserProfileFragment()
    }

    @Inject
    lateinit var factory: UserProfileViewModel.Factory
    private lateinit var viewModel: UserProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), factory)
            .get(UserProfileViewModel::class.java)

        viewModel.state().observe(viewLifecycleOwner, this@UserProfileFragment::bind)
    }

    private fun bind(state: UserProfileState) {
        TODO("Not yet implemented")
    }
}

package com.jmvincenti.myuserdirectory.feature.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jmvincenti.myuserdirectory.databinding.UserProfileFragmentBinding
import com.jmvincenti.myuserdirectory.feature.userprofile.model.UserProfileCommand
import com.jmvincenti.myuserdirectory.feature.userprofile.model.UserProfileState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val PARAM_USER_ID = "Param:userId"

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    companion object {
        fun newInstance(userId: String): UserProfileFragment {
            val fragment = UserProfileFragment()

            val args = Bundle()
            args.putString(PARAM_USER_ID, userId)
            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var userId: String

    @Inject
    lateinit var factory: UserProfileViewModel.Factory
    private lateinit var viewModel: UserProfileViewModel

    private var _binding: UserProfileFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userId = requireNotNull(arguments?.getString(PARAM_USER_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = UserProfileFragmentBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), factory)
            .get(UserProfileViewModel::class.java)

        viewModel.state().observe(viewLifecycleOwner, this@UserProfileFragment::bind)
        viewModel.onCommand(UserProfileCommand.Init(userId))
    }

    private fun bind(state: UserProfileState) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

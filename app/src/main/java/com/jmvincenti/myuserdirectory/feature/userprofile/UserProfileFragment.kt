package com.jmvincenti.myuserdirectory.feature.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.jmvincenti.myuserdirectory.R
import com.jmvincenti.myuserdirectory.databinding.UserProfileFragmentBinding
import com.jmvincenti.myuserdirectory.feature.userprofile.domain.CoordinateImageBuilder
import com.jmvincenti.myuserdirectory.feature.userprofile.model.UserProfileCommand
import com.jmvincenti.myuserdirectory.feature.userprofile.model.UserProfileState
import com.jmvincenti.myuserdirectory.feature.userprofile.ui.GenericAdapter
import com.jmvincenti.myuserdirectory.feature.userprofile.ui.LocationAdapter
import com.jmvincenti.myuserdirectory.feature.userprofile.ui.UserProfileCardAdapter
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import java.text.SimpleDateFormat
import java.util.*
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
    lateinit var coordinateImageBuilder: CoordinateImageBuilder

    @Inject
    lateinit var factory: UserProfileViewModel.Factory
    private lateinit var viewModel: UserProfileViewModel

    private var _binding: UserProfileFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var cardAdapter: UserProfileCardAdapter
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var phoneAdapter: GenericAdapter
    private lateinit var cellAdapter: GenericAdapter
    private lateinit var dobAdapter: GenericAdapter

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardAdapter = UserProfileCardAdapter()
        locationAdapter = LocationAdapter(coordinateImageBuilder)
        phoneAdapter = GenericAdapter(
            iconRes = R.drawable.ic_baseline_local_phone_24,
            title = R.string.user_phone
        )
        cellAdapter = GenericAdapter(
            iconRes = R.drawable.ic_baseline_phone_android_24,
            title = R.string.user_cell
        )
        dobAdapter = GenericAdapter(
            iconRes = R.drawable.ic_baseline_calendar_today_24,
            title = R.string.user_dob
        )

        binding.recyclerView.apply {
            val layoutManager = LinearLayoutManager(context)

            this.layoutManager = layoutManager
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )

            adapter = ConcatAdapter(
                cardAdapter,
                phoneAdapter,
                cellAdapter,
                dobAdapter,
                locationAdapter
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), factory)
            .get(UserProfileViewModel::class.java)

        viewModel.state().observe(viewLifecycleOwner, this@UserProfileFragment::bind)
        viewModel.onCommand(UserProfileCommand.Init(userId))
    }

    private fun bind(state: UserProfileState) {
        val user = state.user
            ?: return

        cardAdapter.user = user
        phoneAdapter.value = user.phone
        cellAdapter.value = user.cell
        dobAdapter.value = user.dob?.let { dateFormat.format(Date(it)) }
        locationAdapter.location = user.location

        Glide.with(requireContext())
            .load(user.pictures?.cover)
            .apply(bitmapTransform(BlurTransformation(25, 3)))
            .placeholder(R.drawable.ic_person)
            .into(binding.userCover)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

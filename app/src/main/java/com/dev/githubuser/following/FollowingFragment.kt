package com.dev.githubuser.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.githubuser.databinding.FragmentFollowingBinding
import com.dev.githubuser.detail.DetailViewModel
import com.dev.githubuser.domain.User
import com.dev.githubuser.main.UserAdapter
import com.dev.githubuser.settings.ViewModelFactory

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = obtainViewModel(activity as AppCompatActivity)
        val detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        detailViewModel.username.observe(requireActivity(), { username ->
            showLoading(true)
            viewModel.getFollowing(username).observe(requireActivity(), { user ->
                if (user != null) {
                    setFollowingData(user)
                    showLoading(false)
                }
            })
        })

        // Check If no following then show no data
        detailViewModel.following.observe(requireActivity(), {
            if (it < 1) {
                binding.tvNoData.visibility = View.VISIBLE
            }
        })


    }
    private fun obtainViewModel(activity: AppCompatActivity): FollowingViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, pref = null)
        return ViewModelProvider(activity, factory)[FollowingViewModel::class.java]
    }

    private fun setFollowingData(followingResponse: List<User>) {
        val listFollowing = ArrayList<User>()

        listFollowing.clear()
        for (i in followingResponse.indices) {
            val user = followingResponse[i]
            listFollowing.add(user)
        }

        val adapter = UserAdapter(listFollowing, 2)

        with(binding) {
            rvListUser.setHasFixedSize(true)
            rvListUser.layoutManager = LinearLayoutManager(requireContext())
            rvListUser.adapter = adapter
        }
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}
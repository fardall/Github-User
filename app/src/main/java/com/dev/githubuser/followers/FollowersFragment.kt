package com.dev.githubuser.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.githubuser.databinding.FragmentFollowersBinding
import com.dev.githubuser.detail.DetailViewModel
import com.dev.githubuser.domain.User
import com.dev.githubuser.main.UserAdapter
import com.dev.githubuser.settings.ViewModelFactory

class FollowersFragment : Fragment() {
    private lateinit var binding: FragmentFollowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = obtainViewModel(activity as AppCompatActivity)
        val detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        detailViewModel.username.observe(requireActivity(), { username ->
            showLoading(true)
            viewModel.getFollowers(username).observe(requireActivity(), { user ->
                if (user != null) {
                    setFollowersData(user)
                    showLoading(false)
                }
            })
        })

        detailViewModel.followers.observe(requireActivity(), {
            if (it < 1) {
                binding.tvNoDataFollower.visibility = View.VISIBLE
            }
        })

    }
    private fun obtainViewModel(activity: AppCompatActivity): FollowersViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, pref = null)
        return ViewModelProvider(activity, factory)[FollowersViewModel::class.java]
    }

    private fun setFollowersData(followersResponse: List<User>) {
        val listFollowers = ArrayList<User>()

        listFollowers.clear()
        for (i in followersResponse.indices) {
            val user = followersResponse[i]
            listFollowers.add(user)
        }

        val adapter = UserAdapter(listFollowers, 2)

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
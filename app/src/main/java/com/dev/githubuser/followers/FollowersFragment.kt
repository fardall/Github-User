package com.dev.githubuser.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.githubuser.responses.ItemsItem
import com.dev.githubuser.adapter.UserAdapter
import com.dev.githubuser.databinding.FragmentFollowersBinding
import com.dev.githubuser.detail.DetailActivity
import com.dev.githubuser.detail.DetailViewModel

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

        val mActivity = activity as DetailActivity
        val username = mActivity.user

        val viewModel = ViewModelProvider(requireActivity())[FollowersViewModel::class.java]
        val detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        viewModel.getFollowers(username)

        detailViewModel.followers.observe(requireActivity(), {
            if (it < 1) {
                binding.tvNoDataFollower.visibility = View.VISIBLE
            }
        })

        viewModel.listUser.observe(requireActivity(), { user ->
            setFollowersData(user)
        })

        viewModel.isLoading.observe(requireActivity(), {
            showLoading(it)
        })
    }


    private fun setFollowersData(followersResponse: List<ItemsItem>) {
        val listFollowers = ArrayList<ItemsItem>()

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
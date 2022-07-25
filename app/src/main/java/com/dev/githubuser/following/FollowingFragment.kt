package com.dev.githubuser.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.githubuser.responses.ItemsItem
import com.dev.githubuser.adapter.UserAdapter
import com.dev.githubuser.databinding.FragmentFollowingBinding
import com.dev.githubuser.detail.DetailActivity
import com.dev.githubuser.detail.DetailViewModel

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

        val mActivity = activity as DetailActivity
        val username = mActivity.user

        val viewModel = ViewModelProvider(requireActivity())[FollowingViewModel::class.java]
        val detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        viewModel.getFollowing(username)

        detailViewModel.following.observe(requireActivity(), {
            if (it < 1) {
                binding.tvNoData.visibility = View.VISIBLE
            }
        })

        viewModel.listUser.observe(requireActivity(), { user ->
            setFollowingData(user)
        })

        viewModel.isLoading.observe(requireActivity(), {
            showLoading(it)
        })

    }



    private fun setFollowingData(followingResponse: List<ItemsItem>) {
        val listFollowing = ArrayList<ItemsItem>()

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
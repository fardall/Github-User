package com.dev.githubuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.githubuser.databinding.FragmentFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        if (mActivity.following != null && mActivity.following!!.toInt() > 0) {
            Log.d("asd", "onViewCreated: ${mActivity.following}")
            binding.tvNoData.visibility = View.GONE
            getFollowing(username)
        }

    }

    private fun getFollowing(username: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setFollowingData(responseBody)
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e("asd", "onFailure: ${t.message}" )
            }


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
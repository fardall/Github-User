package com.dev.githubuser

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dev.githubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var user: String
    var followers: String? = null
    var following: String? = null

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getStringExtra(EXTRA_USERNAME)!!

        getUser(user)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }

    private fun getUser(username: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    showLoading(false)
                    val responseBody = response.body()
                    setUserData(responseBody)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("asd", "onFailure: ${t.message}")
            }
        })
    }

    private fun setUserData(user: UserResponse?) {
        if (user != null) {
            with(binding.includeUser) {
                tvUser.text = user.name
                tvUsername.text = user.login
                tvCompany.text = user.company
                tvLocation.text = user.location
                tvRepository.text = user.publicRepos.toString()
                tvFollowers.text = user.followers.toString()
                tvFollowing.text = user.following.toString()
            }
            Glide .with(this@DetailActivity)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.includeUser.ivUser)

            following = user.following.toString()
            followers = user.followers.toString()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.bringToFront()
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.bringToFront()
            binding.progressBar.visibility = View.GONE
        }
    }
}
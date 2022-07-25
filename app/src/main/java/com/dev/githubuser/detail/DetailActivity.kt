package com.dev.githubuser.detail

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dev.githubuser.R
import com.dev.githubuser.responses.UserResponse
import com.dev.githubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

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

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        viewModel.username.value = intent.getStringExtra(EXTRA_USERNAME)!!

        viewModel.username.observe(this, {
            viewModel.getUser(it)
        })

        viewModel.user.observe(this, { user ->
            setUserData(user)
        })

        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

        setTabPager()

    }

    private fun setTabPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
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
            Glide.with(this@DetailActivity)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.includeUser.ivUser)

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
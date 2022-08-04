package com.dev.githubuser.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dev.githubuser.R
import com.dev.githubuser.databinding.ActivityDetailBinding
import com.dev.githubuser.db.User
import com.dev.githubuser.responses.UserResponse
import com.dev.githubuser.settings.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var favUser: User

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        private const val TAG = "asd"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = obtainViewModel(this)

        viewModel.username.value = intent.getStringExtra(EXTRA_USERNAME)!!

        viewModel.username.observe(this, { username ->
            viewModel.getUser(username)

            viewModel.isExist(username).observe(this, { data ->
                Log.d(TAG, "onCreate: $data")
                if (data < 1) {
                    with(binding.fabFav) {
                        setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite, theme))
                        setOnClickListener {
                            isEnabled = false
                            setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_red, theme))
                            viewModel.insert(favUser)

                            Toast.makeText(
                                this@DetailActivity,
                                "Berhasil Menambah Data",
                                Toast.LENGTH_SHORT
                            ).show()
                            isEnabled = true
                        }
                    }
                } else {
                    with(binding.fabFav) {
                        setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_red, theme))
                        setOnClickListener {
                            isEnabled = false
                            setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite, theme))
                            viewModel.delete(favUser.username!!)

                            Toast.makeText(
                                this@DetailActivity,
                                "Berhasil Menghapus Data",
                                Toast.LENGTH_SHORT
                            ).show()
                            isEnabled = true
                        }
                    }
                }
            })
        })

        viewModel.user.observe(this, { user ->
            setUserData(user)
            favUser = User(name = user.name, username = user.login, avatar = user.avatarUrl)
        })


        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

        setTabPager()

    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, pref = null)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
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
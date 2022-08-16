package com.dev.githubuser.detail

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dev.githubuser.R
import com.dev.githubuser.databinding.ActivityDetailBinding
import com.dev.githubuser.data.local.db.UserEntity
import com.dev.githubuser.data.remote.responses.DetailUserResponse
import com.dev.githubuser.settings.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var favUserEntity: UserEntity

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        private const val TAG = "asd"
    }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = obtainViewModel(this)

        // Accept input username in order to fetch Github API
        viewModel.username.value = intent.getStringExtra(EXTRA_USERNAME)!!

        viewModel.username.observe(this, { username ->
            // Find user by username on Github API
            viewModel.getUser(username)

            // Check if the username is exist on RoomDB
            viewModel.isExist(username).observe(this, { data ->

                viewModel.detailUser.observe(this, { user ->
                    setUserData(user)
                    favUserEntity = UserEntity(name = user.name, username = user.login, avatar = user.avatarUrl)

                    // Set FAB Color based on isExist value
                    setFab(data, viewModel)
                })
            })
        })

        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

        setTabPager()

    }

    private fun makeToast(text: String) {
        Toast.makeText(
            this@DetailActivity,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setFab(data: Int, viewModel: DetailViewModel) {
        if (data < 1) { // if it's not exist
            Log.d(TAG, "onCreate: $data Masuk Data 0")
            with(binding.fabFav) {
                imageTintList = ColorStateList.valueOf(Color.WHITE)
                setOnClickListener {
                    isEnabled = false
                    imageTintList = ColorStateList.valueOf(Color.RED)
                    viewModel.insert(favUserEntity)

                    makeToast("Berhasil Menambah Data")
                    isEnabled = true
                }
            }
        } else {
            Log.d(TAG, "onCreate: $data Masuk Data 1")
            with(binding.fabFav) {
                imageTintList = ColorStateList.valueOf(Color.RED)
                setOnClickListener {
                    isEnabled = false
                    imageTintList = ColorStateList.valueOf(Color.WHITE)
                    viewModel.delete(favUserEntity.username!!)

                    makeToast("Berhasil Menghapus Data")
                    isEnabled = true
                }
            }
        }
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

    private fun setUserData(detailUser: DetailUserResponse?) {
        if (detailUser != null) {
            with(binding.includeUser) {
                tvUser.text = detailUser.name
                tvUsername.text = detailUser.login
                tvCompany.text = detailUser.company
                tvLocation.text = detailUser.location
                tvRepository.text = detailUser.publicRepos.toString()
                tvFollowers.text = detailUser.followers.toString()
                tvFollowing.text = detailUser.following.toString()
            }
            Glide.with(this@DetailActivity)
                .load(detailUser.avatarUrl)
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
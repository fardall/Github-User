package com.dev.githubuser.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.githubuser.main.UserAdapter
import com.dev.githubuser.databinding.ActivityFavoriteBinding
import com.dev.githubuser.settings.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = obtainViewModel(this)
        viewModel.getFavoriteUsers().observe(this, { listUser ->
            if (listUser.isNotEmpty()) {
                binding.tvNoData.visibility = View.GONE
                userAdapter = UserAdapter(listUser, 3)
                userAdapter.setListUser(listUser)
                with(binding.rvUser) {
                    layoutManager = GridLayoutManager(this@FavoriteActivity, 2)
                    adapter = userAdapter
                    setHasFixedSize(true)
                }
            } else {
                binding.tvNoData.visibility = View.VISIBLE
            }
        })

    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, pref = null)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }
}
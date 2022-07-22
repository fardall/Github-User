package com.dev.githubuser

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dev.githubuser.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getStringExtra(EXTRA_USERNAME)
        if (user != null) {
            getUser(user)
        }

    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    private fun getUser(username: String) {
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
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
            with(binding) {
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
                .into(binding.ivUser)
        }
    }
}
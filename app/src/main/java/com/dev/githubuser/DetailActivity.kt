package com.dev.githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dev.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER)

        if (user != null) {
            with(binding) {
                tvUser.text = user.name
                tvUsername.text = user.username
                tvCompany.text = user.company
                tvLocation.text = user.location
                tvRepository.text = user.repository
                tvFollowers.text = user.followers
                tvFollowing.text = user.following
            }

            Glide .with(this)
                .load(user.photo)
                .circleCrop()
                .into(binding.ivUser)
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}
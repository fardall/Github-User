package com.dev.githubuser.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.githubuser.databinding.ListUserBinding
import com.dev.githubuser.databinding.UserFollowsBinding
import com.dev.githubuser.data.local.db.UserEntity
import com.dev.githubuser.data.remote.responses.UserResponse
import com.dev.githubuser.detail.DetailActivity
import com.dev.githubuser.util.UserDiffCallback


class UserAdapter(private val listUser: List<Any>, private val viewType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val listFavoriteUsers = ArrayList<UserEntity>()

    fun setListUser(listFavorite: List<UserEntity>) {
        val diffCallback = UserDiffCallback(this.listFavoriteUsers, listFavorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavoriteUsers.clear()
        this.listFavoriteUsers.addAll(listFavorite)
        diffResult.dispatchUpdatesTo(this)
    }

    companion object {
        private const val ONE = 0
        private const val TWO = 1
        private const val THREE = 2
    }

    class GridVH(private var binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserResponse) {
            with(binding) {
                tvUsername.text = user.login
            }
            Glide .with(itemView.context)
                .load(user.avatarUrl)
                .into(binding.ivUser)
        }

        fun bindFavUsers(userEntity: UserEntity) {
            with(binding) {
                tvUser.text = userEntity.name
                tvUsername.text = userEntity.username
            }
            Glide .with(itemView.context)
                .load(userEntity.avatar)
                .into(binding.ivUser)
        }
    }

    class ListVH(private var binding: UserFollowsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserResponse) {
            with(binding) {
                tvUsername.text = user.login
            }
            Glide .with(itemView.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.ivUser)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (viewType) {
            1 -> ONE
            2 -> TWO
            3 -> THREE
            else -> throw IllegalArgumentException("Undefined ViewType")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ONE, THREE -> {
                val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridVH(binding)
            }
            TWO -> {
                val binding = UserFollowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ListVH(binding)
            }
            else -> throw IllegalArgumentException("Undefined ViewType")
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (holder.itemViewType) {
            ONE -> {
                val list = listUser[position] as UserResponse
                val gridHolder = holder as GridVH
                gridHolder.bind(list)
                gridHolder.itemView.setOnClickListener {
                    val toDetail =
                        Intent(gridHolder.itemView.context, DetailActivity::class.java)
                    toDetail.putExtra(DetailActivity.EXTRA_USERNAME, list.login)

                    gridHolder.itemView.context.startActivity(toDetail)
                }
            }
            TWO -> {
                val listHolder = holder as ListVH
                val list = listUser[position] as UserResponse
                listHolder.bind(list)
            }
            THREE -> {
                val gridHolder = holder as GridVH
                val listFav = listFavoriteUsers[position]
                gridHolder.bindFavUsers(listFav)
                gridHolder.itemView.setOnClickListener {
                    val toDetail =
                        Intent(gridHolder.itemView.context, DetailActivity::class.java)
                    toDetail.putExtra(DetailActivity.EXTRA_USERNAME, listFav.username)

                    gridHolder.itemView.context.startActivity(toDetail)
                }
            }
            else -> throw java.lang.IllegalArgumentException("Undefined view type")
        }
    }
}
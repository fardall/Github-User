package com.dev.githubuser

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.githubuser.databinding.ListUserBinding
import com.dev.githubuser.databinding.UserFollowsBinding


class UserAdapter(private val listUser: List<Any>, private val viewType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ONE = 0
        private const val TWO = 1
    }

    class GridVH(private var binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {

            with(binding) {
                tvUsername.text = user.login
            }
            Glide .with(itemView.context)
                .load(user.avatarUrl)
                .into(binding.ivUser)
        }
    }

    class ListVH(private var binding: UserFollowsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {

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
            else -> throw IllegalArgumentException("Undefined ViewType")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ONE -> {
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
                val list = listUser[position] as ItemsItem
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
                val list = listUser[position] as ItemsItem
                listHolder.bind(list)
            }
            else -> throw java.lang.IllegalArgumentException("Undefined view type")
        }
    }
}
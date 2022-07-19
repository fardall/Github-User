package com.dev.githubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.githubuser.databinding.ListUserBinding

class UserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(private var binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                tvUser.text = user.name
                tvUsername.text = user.username
            }
                Glide .with(itemView.context)
                    .load(user.photo)
                    .into(binding.ivUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])
        holder.itemView.setOnClickListener {
            val toDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            toDetail.putExtra(DetailActivity.EXTRA_USER, listUser[position])
            holder.itemView.context.startActivity(toDetail)
        }
    }
}
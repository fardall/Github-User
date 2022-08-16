package com.dev.githubuser.util

import androidx.recyclerview.widget.DiffUtil
import com.dev.githubuser.data.local.db.UserEntity

class UserDiffCallback(private val mOldUserEntityList: List<UserEntity>, private val mNewUserEntityList: List<UserEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldUserEntityList.size
    }
    override fun getNewListSize(): Int {
        return mNewUserEntityList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserEntityList[oldItemPosition].id == mNewUserEntityList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = mOldUserEntityList[oldItemPosition]
        val newUser = mNewUserEntityList[newItemPosition]
        return oldUser.name == newUser.name && oldUser.username == newUser.username
    }
}
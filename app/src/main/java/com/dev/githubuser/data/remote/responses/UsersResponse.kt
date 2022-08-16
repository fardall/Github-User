package com.dev.githubuser.data.remote.responses

import com.google.gson.annotations.SerializedName

data class UsersResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<UserResponse>
)

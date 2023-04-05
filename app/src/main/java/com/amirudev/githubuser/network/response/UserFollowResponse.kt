package com.amirudev.githubuser

import com.google.gson.annotations.SerializedName

data class UserFollowResponse(

	@field:SerializedName("UserFollowResponse")
	val userFollowResponse: List<UserFollowResponseItem?>? = null
)

data class UserFollowResponseItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("html_url")
	val url: String? = null
)

data class UserResponse(

	@field:SerializedName("hireable")
	val hireable: Any? = null,

	@field:SerializedName("twitter_username")
	val twitterUsername: Any? = null,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("blog")
	val blog: String? = null,

	@field:SerializedName("public_gists")
	val publicGists: Int? = null,

	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("html_url")
	val htmlUrl: String? = null,

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("company")
	val company: Any? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("public_repos")
	val publicRepos: Int? = null,

	@field:SerializedName("email")
	val email: Any? = null
)
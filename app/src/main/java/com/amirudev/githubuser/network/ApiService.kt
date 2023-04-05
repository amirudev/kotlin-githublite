package com.amirudev.githubuser.network

import com.amirudev.githubuser.UserFollowResponseItem
import com.amirudev.githubuser.UserResponse
import com.amirudev.githubuser.UserSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
//    Get list of user followers
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<UserFollowResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<UserFollowResponseItem>>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserResponse>

    @GET("search/users")
    fun getSearchResult(@Query("q") query: String): Call<UserSearchResponse>
}
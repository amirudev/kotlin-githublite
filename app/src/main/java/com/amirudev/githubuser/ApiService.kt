package com.amirudev.githubuser

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("users/amirudev/followers")
    fun getMyFollowers(): Call<List<UserFollowResponseItem>>

    @GET("users/amirudev/following")
    fun getMyFollowing(): Call<List<UserFollowResponseItem>>
}
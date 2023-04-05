package com.amirudev.githubuser.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amirudev.githubuser.UserFollowResponseItem
import com.amirudev.githubuser.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private var _followers = MutableLiveData<List<UserFollowResponseItem>>()
    val followers: LiveData<List<UserFollowResponseItem>> = _followers

    private var _following = MutableLiveData<List<UserFollowResponseItem>>()
    val following: LiveData<List<UserFollowResponseItem>> = _following

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private fun _fetchFollowInformation(apiService: Call<List<UserFollowResponseItem>>, followType: MutableLiveData<List<UserFollowResponseItem>>) {
        _isLoading.value = true

        apiService.enqueue(object : Callback<List<UserFollowResponseItem>> {
            override fun onResponse(
                call: Call<List<UserFollowResponseItem>>,
                response: Response<List<UserFollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    followType.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<UserFollowResponseItem>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.d("ERROR", "Error: ${t.message}")
            }
        })
    }

    fun getFollowing(username: String) {
        _fetchFollowInformation(ApiConfig.getApiService().getFollowing(username), _following)
    }

    fun getFollowers(username: String) {
        _fetchFollowInformation(ApiConfig.getApiService().getFollowers(username), _followers)
    }
}
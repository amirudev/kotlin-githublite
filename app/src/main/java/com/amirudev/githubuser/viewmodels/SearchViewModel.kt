package com.amirudev.githubuser.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amirudev.githubuser.UserFollowResponseItem
import com.amirudev.githubuser.UserSearchResponse
import com.amirudev.githubuser.UserSearchResponseItem
import com.amirudev.githubuser.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private var _users = MutableLiveData<List<UserSearchResponseItem>>()
    val users: MutableLiveData<List<UserSearchResponseItem>> = _users

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private var _isError = MutableLiveData<Boolean>()
    val isError: MutableLiveData<Boolean> = _isError

    private fun _fetchFollowInformation(apiService: Call<UserSearchResponse>, followType: MutableLiveData<List<UserSearchResponseItem>>) {
        _isLoading.value = true

        apiService.enqueue(object : Callback<UserSearchResponse> {
            override fun onResponse(
                call: Call<UserSearchResponse>,
                response: Response<UserSearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    followType.value = (response.body()?.items ?: emptyList()) as List<UserSearchResponseItem>?
                }
            }

            override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.d("ERROR", "Error: ${t.message}")
            }
        })
    }

    fun getUsersFromApi(username: String) {
        _fetchFollowInformation(ApiConfig.getApiService().getSearchResult(username), _users)
    }
}
package com.amirudev.githubuser.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amirudev.githubuser.UserResponse
import com.amirudev.githubuser.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private var _user = MutableLiveData<UserResponse>()
    val user: MutableLiveData<UserResponse> = _user

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private var _isError = MutableLiveData<Boolean>()
    val isError: MutableLiveData<Boolean> = _isError

    fun getUserFromApi(username: String) {
        _isLoading.value = true

        ApiConfig.getApiService().getUserDetail(username).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.d("ERROR", "Error: ${t.message}")
            }
        })
    }
}
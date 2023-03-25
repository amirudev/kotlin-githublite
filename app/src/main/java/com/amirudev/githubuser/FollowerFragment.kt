package com.amirudev.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FollowerFragment : Fragment() {
    private lateinit var rvUsersFollowers: RecyclerView
    private lateinit var list: List<UserFollowResponseItem>
    private var isLoading: Boolean = false
    private var pbUsersFollowers = view?.findViewById<ProgressBar>(R.id.pb_users_followers)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pbUsersFollowers = view?.findViewById<ProgressBar>(R.id.pb_users_followers)

//        showRecyclerList()
        list = getListUser().userFollowResponse as List<UserFollowResponseItem>

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_follower, container, false)

        rvUsersFollowers = view.findViewById(R.id.rv_users_followers)
        rvUsersFollowers.setHasFixedSize(true)

        return view;
    }

    private fun showRecyclerList() {
        rvUsersFollowers.layoutManager = LinearLayoutManager(requireContext())
        println(list)

        val listUserAdapter = ListUserAdapter(list)
        rvUsersFollowers.adapter = listUserAdapter
    }

    private fun getListUser(): UserFollowResponse {
        var userFollowResponseItemList: List<UserFollowResponseItem> = ArrayList();

        toggleLoading(true)
        GlobalScope.launch {
            try {
                val response = ApiConfig.getApiService().getMyFollowers().execute()
                if (response.isSuccessful) {
                    userFollowResponseItemList = response.body() ?: emptyList()
                    withContext(Dispatchers.Main) {
                        rvUsersFollowers.layoutManager = LinearLayoutManager(requireContext())
                        rvUsersFollowers.adapter = ListUserAdapter(userFollowResponseItemList)
                        toggleLoading(false)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    toggleLoading(false)
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return UserFollowResponse(userFollowResponse = userFollowResponseItemList)
    }

    private fun toggleLoading(isLoading: Boolean) {
        this.isLoading = isLoading
        if (pbUsersFollowers != null) pbUsersFollowers!!.visibility = if (isLoading) View.VISIBLE else View.GONE

        rvUsersFollowers.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}
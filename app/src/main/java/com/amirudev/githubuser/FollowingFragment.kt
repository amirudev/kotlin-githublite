package com.amirudev.githubuser

import android.content.Intent
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

class FollowingFragment : Fragment() {
    private lateinit var rvUsersFollowing: RecyclerView
    private lateinit var list: List<UserFollowResponseItem>
    private var isLoading: Boolean = false
    private var pbUsersFollowing = view?.findViewById<ProgressBar>(R.id.pb_users_following)
    var userFollowResponseItemList: List<UserFollowResponseItem> = ArrayList()
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            username = it?.getString("EXTRA_USERNAME").toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pbUsersFollowing = view.findViewById<ProgressBar>(R.id.pb_users_following)

        list = getListUser().userFollowResponse as List<UserFollowResponseItem>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_following, container, false)

        rvUsersFollowing = view.findViewById(R.id.rv_users_following)
        rvUsersFollowing.setHasFixedSize(true)

        return view
    }

    private fun showRecyclerList() {
        rvUsersFollowing.layoutManager = LinearLayoutManager(requireContext())
        println(list)

        val listUserAdapter = ListUserAdapter(userFollowResponseItemList, object : ListUserAdapter.onItemClickListener {
            override fun onItemClick(data: UserFollowResponseItem) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("EXTRA_USERNAME", data.login)
                startActivity(intent)
            }
        })

        rvUsersFollowing.adapter = listUserAdapter
    }

    private fun getListUser(): UserFollowResponse {
        toggleLoading(true)
        GlobalScope.launch {
            try {
                val response = ApiConfig.getApiService().getFollowing(username).execute()
                if (response.isSuccessful) {
                    userFollowResponseItemList = response.body() ?: emptyList()
                    withContext(Dispatchers.Main) {
                        showRecyclerList()
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
        if (pbUsersFollowing != null) pbUsersFollowing!!.visibility = if (isLoading) View.VISIBLE else View.GONE

        rvUsersFollowing.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}
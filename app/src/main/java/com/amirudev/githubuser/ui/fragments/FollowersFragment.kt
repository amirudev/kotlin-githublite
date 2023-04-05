package com.amirudev.githubuser.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amirudev.githubuser.adapters.ListUserAdapter
import com.amirudev.githubuser.R
import com.amirudev.githubuser.UserFollowResponseItem
import com.amirudev.githubuser.databinding.FragmentFollowerBinding
import com.amirudev.githubuser.models.User
import com.amirudev.githubuser.ui.activities.DetailActivity
import com.amirudev.githubuser.viewmodels.MainViewModel

class FollowersFragment : Fragment() {
    private var username: String = ""
    private var followersType: Int = TYPE_FOLLOWERS[0]

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
    }

    private var _binding: FragmentFollowerBinding? = null
    private val binding  get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            username = it?.getString(EXTRA_USERNAME).toString()
            followersType = it?.getInt(EXTRA_FOLLOWERS_TYPE)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFollowerBinding.bind(view)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                 binding.pbUsersFollowers.visibility = View.VISIBLE
            } else {
                binding.pbUsersFollowers.visibility = View.GONE
            }
        }

        viewModel.followers.observe(viewLifecycleOwner) { followers ->
            if (followers != null) {
                val users: List<User> = followers.map { follower ->
                    User(
                        id = follower.id ?: 0,
                        login = follower.login ?: "",
                        avatar_url = follower.avatarUrl ?: "",
                        url = follower.url ?: ""
                    )
                }

                showRecyclerList(users, binding.rvUsersFollowers)
            }
        }

        viewModel.following.observe(viewLifecycleOwner) { followers ->
            if (followers != null) {
                val users: List<User> = followers.map { follower ->
                    User(
                        id = follower.id ?: 0,
                        login = follower.login ?: "",
                        avatar_url = follower.avatarUrl ?: "",
                        url = follower.url ?: ""
                    )
                }

                showRecyclerList(users, binding.rvUsersFollowers)
            }
        }

        viewModel.following.observe(viewLifecycleOwner) { followers ->
            if (followers != null) {
                val users: List<User> = followers.map { follower ->
                    User(
                        id = follower.id ?: 0,
                        login = follower.login ?: "",
                        avatar_url = follower.avatarUrl ?: "",
                        url = follower.url ?: ""
                    )
                }

                showRecyclerList(users, binding.rvUsersFollowers)
            }
        }

        if (followersType == TYPE_FOLLOWERS[0]) {
            viewModel.getFollowers(username)
        } else {
            viewModel.getFollowing(username)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_follower, container, false)

        return view
    }

    private fun showRecyclerList(users: List<User>, view: RecyclerView) {
        view.layoutManager = LinearLayoutManager(requireContext())

        val listUserAdapter = ListUserAdapter(users, object : ListUserAdapter.onItemClickListener {
            override fun onItemClick(data: User) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(EXTRA_USERNAME, data.login)
                startActivity(intent)
            }
        })

        view.adapter = listUserAdapter
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_FOLLOWERS_TYPE = "extra_followers_type"

        private val TYPE_FOLLOWERS = intArrayOf(
            R.string.followers,
            R.string.following,
        )
    }
}
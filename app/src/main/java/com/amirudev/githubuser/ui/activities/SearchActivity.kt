package com.amirudev.githubuser.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amirudev.githubuser.R
import com.amirudev.githubuser.UserFollowResponseItem
import com.amirudev.githubuser.UserSearchResponse
import com.amirudev.githubuser.adapters.ListUserAdapter
import com.amirudev.githubuser.databinding.ActivitySearchBinding
import com.amirudev.githubuser.models.User
import com.amirudev.githubuser.network.ApiConfig
import com.amirudev.githubuser.ui.fragments.FollowersFragment
import com.amirudev.githubuser.viewmodels.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    var username: String? = null

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[SearchViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        actionBar?.title = "Search Result"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        username = intent.getStringExtra(SearchActivity.EXTRA_USERNAME) ?: "amirudev"

        viewModel.isLoading.observe(this) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.users.observe(this) { u ->
            if (u != null) {
                val userList = u.map { user ->
                    User(
                        id = user.id ?: 0,
                        login = user.login ?: "",
                        avatar_url = user.avatarUrl ?: "",
                        html_url = user.htmlUrl ?: ""
                    )
                }

                showRecyclerList(userList, binding.rvUsers)
            }
        }

        viewModel.getUsersFromApi(username!!)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Handle the back button click event here
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showRecyclerList(users: List<User>, view: RecyclerView) {
        view.layoutManager = LinearLayoutManager(this)
        view.setHasFixedSize(true)

        val listUserAdapter = ListUserAdapter(users, object : ListUserAdapter.onItemClickListener {
            override fun onItemClick(data: User) {
                val intent = Intent(this@SearchActivity, DetailActivity::class.java)
                intent.putExtra(EXTRA_USERNAME, data.login)
                startActivity(intent)
            }
        })

        view.adapter = listUserAdapter
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username";
    }
}
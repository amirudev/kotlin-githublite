package com.amirudev.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchActivity : AppCompatActivity() {
    private lateinit var rvUsers: RecyclerView
    private lateinit var list: List<UserFollowResponseItem>
    private var isLoading: Boolean = false
    private var pbUsers: ProgressBar? = null
    var userFollowResponseItemList: List<UserFollowResponseItem> = ArrayList()
    var username: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val actionBar = supportActionBar
        actionBar?.title = "Search Result"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        rvUsers = findViewById(R.id.rv_users)
        pbUsers = findViewById(R.id.progress_bar)

        rvUsers.setHasFixedSize(true)

        username = intent.getStringExtra(EXTRA_USERNAME)

        list = getListUser().items?.map { it ->
            UserFollowResponseItem(
                it?.avatarUrl ?: "",
                it?.id ?: 0,
                it?.login ?: "",
                it?.url ?: "")
        } ?: emptyList()

        showRecyclerList()
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

    private fun showRecyclerList() {

        rvUsers.layoutManager = LinearLayoutManager(this)
        println(list)

        val listUserAdapter = ListUserAdapter(userFollowResponseItemList, object : ListUserAdapter.onItemClickListener {
            override fun onItemClick(data: UserFollowResponseItem) {
                val intent = Intent(this@SearchActivity, DetailActivity::class.java)
                intent.putExtra("EXTRA_USERNAME", data.login)
                startActivity(intent)
            }
        })

        rvUsers.adapter = listUserAdapter
    }

    private fun getListUser(): UserSearchResponse {
        toggleLoading(true)
        GlobalScope.launch {
            try {
                val response = ApiConfig.getApiService().getSearchResult(username ?: "").execute()
                if (response.isSuccessful) {
                    userFollowResponseItemList = response.body()?.items?.map { it ->
                        UserFollowResponseItem(
                            it?.avatarUrl ?: "",
                            it?.id ?: 0,
                            it?.login ?: "",
                            it?.url ?: "")
                    } ?: emptyList()
                    withContext(Dispatchers.Main) {
                        showRecyclerList()
                        toggleLoading(false)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    toggleLoading(false)
                    Toast.makeText(this@SearchActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return UserSearchResponse()
    }

    private fun toggleLoading(isLoading: Boolean) {
        this.isLoading = isLoading
        if (pbUsers != null) pbUsers!!.visibility = if (isLoading) View.VISIBLE else View.GONE

        rvUsers.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    companion object {
        val EXTRA_USERNAME: String? = null
    }
}
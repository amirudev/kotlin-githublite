package com.amirudev.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {
    private lateinit var rvUsers: RecyclerView
    private lateinit var list: List<UserFollowResponseItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val actionBar = supportActionBar
        actionBar?.title = "Search Result"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        rvUsers = findViewById(R.id.rv_users)
        rvUsers.setHasFixedSize(true)

        list = getLstUser().userFollowResponse as List<UserFollowResponseItem>
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
        val listUserAdapter = ListUserAdapter(list)
        rvUsers.adapter = listUserAdapter
    }

    private fun getLstUser(): UserFollowResponse {
        var userFollowResponseItemList: ArrayList<UserFollowResponseItem> = ArrayList();

        userFollowResponseItemList.add(UserFollowResponseItem("https://avatars.githubusercontent.com/u/5664102?v=4", 5664102, "jeffersonsimaogoncalves", "https://github.com/jeffersonsimaogoncalves"),)
        userFollowResponseItemList.add(UserFollowResponseItem("https://avatars.githubusercontent.com/u/5664102?v=4", 5664102, "jeffersonsimaogoncalves", "https://github.com/jeffersonsimaogoncalves"),)
        userFollowResponseItemList.add(UserFollowResponseItem("https://avatars.githubusercontent.com/u/5664102?v=4", 5664102, "jeffersonsimaogoncalves", "https://github.com/jeffersonsimaogoncalves"),)

        val users = UserFollowResponse(userFollowResponse = userFollowResponseItemList)

        return users
    }

    companion object {
        val EXTRA_QUERY: String? = null
    }
}